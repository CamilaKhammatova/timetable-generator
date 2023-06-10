package com.kamilahammatova.schedule.service.algorithm.service;

import com.kamilahammatova.schedule.entities.mongodb.Lesson;
import com.kamilahammatova.schedule.repository.AudienceRepository;
import com.kamilahammatova.schedule.repository.BookingRequestRepository;
import com.kamilahammatova.schedule.repository.LessonRepository;
import com.kamilahammatova.schedule.service.DistanceService;
import com.kamilahammatova.schedule.service.algorithm.Gen;
import com.kamilahammatova.schedule.service.algorithm.Individual;
import com.kamilahammatova.schedule.service.algorithm.Population;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class GeneticAlgorithmService {

    public final AudienceRepository audienceRepository;
    public final LessonRepository lessonRepository;
    public final BookingRequestRepository requestRepository;
    public final DistanceService distanceService;

    public static final Random rn = new Random();

    public Population generatePopulation(int popSize, List<Long> auditories, List<Long> requests) {
        var population = new Population(popSize);

        for (int i = 0; i < popSize; i++) {
            population.getIndividuals()[i] = new Individual(generateRandomGenes(auditories, requests));
        }

        return population;
    }

    private Gen[] generateRandomGenes(List<Long> auditories, List<Long> requests) {
        var gens = new Gen[requests.size()];
        var idx = 0;
        for (Long request : requests) {
            gens[idx] = new Gen(request, auditories.get(rn.nextInt(auditories.size())));
            idx++;
        }

        return gens;
    }

    public void calcFitness(Individual individual) {
        var bookings = toLessons(individual);

        var capacity = checkCapacity(bookings);
        var auditoryClashes = auditoryClashes(bookings);
        var requirementsFailed = requirementFailed(bookings);
        var distances = calculateDistances(bookings);
        var time = checkDateTime(bookings);
        System.out.println("calcFitness" + capacity + " " + auditoryClashes + " " + requirementsFailed + " " + distances);
        individual.setDistances(distances);

        var fitness = (int) (distances * 200 + (capacity * 100) + (auditoryClashes * 1000)
                + (requirementsFailed * 700) + (time * 1000));
        individual.setFitness(fitness);
    }

    public List<Lesson> toLessons(Individual individual) {
        return Arrays.stream(individual.getGenes())
                .map(this::genToLessons)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Lesson> genToLessons(Gen gen) {
        var request = requestRepository.findById(gen.getRequest()).orElseThrow(IllegalArgumentException::new);
        var audience = audienceRepository.findById(gen.getAuditory()).orElseThrow(IllegalArgumentException::new);

        switch (request.getPeriod()) {
            case ONCE: {
                return Collections.singletonList(Lesson.builder()
                        .audience(audience)
                        .bookingRequest(request)
                        .date(request.getStartDate())
                        .time(request.getTime())
                        .build());
            }
            case WEEKLY: {
                var bookings = new ArrayList<Lesson>();
                var date = request.getStartDate();
                while (date.isBefore(request.getFinishDate())) {
                    bookings.add(Lesson
                            .builder()
                            .audience(audience)
                            .bookingRequest(request)
                            .date(date)
                            .time(request.getTime())
                            .build());
                    date = date.plusWeeks(1);
                }
                return bookings;
            }
            case MONTHLY: {
                var bookings = new ArrayList<Lesson>();
                var date = request.getStartDate();
                while (date.isBefore(request.getFinishDate())) {
                    bookings.add(Lesson
                            .builder()
                            .audience(audience)
                            .bookingRequest(request)
                            .date(date)
                            .time(request.getTime())
                            .build());
                    date = date.plusMonths(1);
                }
                return bookings;
            }
            case ONE_IN_TWO_WEEKS: {
                var bookings = new ArrayList<Lesson>();
                var date = request.getStartDate();
                while (date.isBefore(request.getFinishDate())) {
                    bookings.add(Lesson
                            .builder()
                            .audience(audience)
                            .bookingRequest(request)
                            .date(date)
                            .time(request.getTime())
                            .build());
                    date = date.plusWeeks(2);
                }
                return bookings;
            }
            default:
                return Collections.emptyList();
        }
    }

    private long checkCapacity(List<Lesson> lessons) {
        return lessons
                .stream()
                .filter(b -> b.getBookingRequest().getCapacity() > b.getAudience().getCapacity())
                .count();
    }

    private long checkDateTime(List<Lesson> lessons) {
        return lessons
                .stream()
                .collect(groupingBy(l -> l.getDate().toString() + l.getTime().time))
                .values()
                .stream()
                .map(List::size)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private long auditoryClashes(List<Lesson> lessons) {

        return lessons
                .stream()
                .collect(groupingBy(Lesson::getDate))
                .values()
                .stream()
                .map(list -> list
                        .stream()
                        .collect(groupingBy(booking -> booking.getAudience().getId()))
                        .values()
                        .stream()
                        .map(bookingsInOneDayOfOneAuditory -> bookingsInOneDayOfOneAuditory
                                .stream()
                                .collect(groupingBy(Lesson::getTime))
                                .values()
                                .stream()
                                .map(Collection::size)
                                .filter(size -> size > 1)
                                .count()
                        ).reduce(Long::sum).orElse(0L)
                ).reduce(Long::sum).orElse(0L);
    }

    private long requirementFailed(List<Lesson> bookings) {
        return bookings
                .stream()
                .filter(b -> !b.getBookingRequest().getRequirements().containsAll(b.getAudience().getRequirements()))
                .count();
    }

    private long calculateDistances(List<Lesson> bookings) {
        var map = bookings.stream().collect(groupingBy(Lesson::getDate));

        var dist = new AtomicLong();
        for (Map.Entry<LocalDate, List<Lesson>> entry : map.entrySet()) {
            entry.getValue()
                    .stream()
                    .collect(groupingBy(b -> b.getBookingRequest().getGroup()))
                    .values()
                    .forEach(bookingList -> bookingList
                            .stream()
                            .sorted(Comparator.comparing(Lesson::getTime))
                            .map(Lesson::getAudience)
                            .reduce((a, a1) -> {
                                dist.addAndGet(Math.round(distanceService.getDistanceBetween(a, a1)));
                                return a1;
                            })
                    );

            entry.getValue()
                    .stream()
                    .collect(groupingBy(b -> b.getBookingRequest().getTeacher().getId()))
                    .values()
                    .forEach(bookingList -> bookingList
                            .stream()
                            .sorted(Comparator.comparing(Lesson::getTime))
                            .map(Lesson::getAudience)
                            .reduce((a, a1) -> {
                                dist.addAndGet(Math.round(distanceService.getDistanceBetween(a, a1)));
                                return a1;
                            })
                    );

        }
        return dist.get();
    }

}