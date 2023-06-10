package com.kamilahammatova.schedule.service.algorithm;

import com.kamilahammatova.schedule.dictionary.AudienceStatus;
import com.kamilahammatova.schedule.entities.mongodb.Audience;
import com.kamilahammatova.schedule.entities.mongodb.BookingRequest;
import com.kamilahammatova.schedule.entities.mongodb.Lesson;
import com.kamilahammatova.schedule.repository.AudienceRepository;
import com.kamilahammatova.schedule.repository.BookingRequestRepository;
import com.kamilahammatova.schedule.repository.LessonRepository;
import com.kamilahammatova.schedule.service.algorithm.service.GeneticAlgorithmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneticAlgorithm {
    private final GeneticAlgorithmService service;
    private final BookingRequestRepository requestRepository;
    private final AudienceRepository auditoryRepository;
    private final LessonRepository lessonRepository;


    private static final boolean verbose = true;
    public static final Random rn = new Random();

    public void train() {
        var maxGeneration = 5;
        var popSize = 100;

        var auditories = auditoryRepository.findAll()
                .stream()
                .filter(a -> a.getStatus().equals(AudienceStatus.ALLOW))
                .map(Audience::getId)
                .collect(Collectors.toList());
        var requests = requestRepository.findAll()
                .stream()
                .map(BookingRequest::getId)
                .collect(Collectors.toList());

        Individual fittest = null;
        Individual secondFittest;
        int generationCount = 0;
        var numberOfGenes = requests.size();

        //Initialize population

        var population = service.generatePopulation(popSize, auditories, requests);

        log.info("Population of " + population.getIndividuals().length + " individual(s).");

        //Calculate fitness of each individual
        Arrays.stream(population.getIndividuals()).forEach(service::calcFitness);

        log.info("\nGeneration: " + generationCount + " Fittest: " + population.getFittestScore());
        //show genetic pool
//        showGeneticPool(population.getIndividuals());

        //While population gets an individual with maximum fitness
        while (population.getFittestScore() < numberOfGenes || generationCount < maxGeneration) {
            ++generationCount;

            //Do selection
            fittest = population.selectFittest();

            secondFittest = population.selectSecondFittest();

            //Do crossover
            int crossOverPoint = rn.nextInt(numberOfGenes);

            //Swap values among parents
            for (int i = 0; i < crossOverPoint; i++) {
                Gen temp = fittest.getGenes()[i].clone();
                fittest.getGenes()[i] = secondFittest.getGenes()[i];
                secondFittest.getGenes()[i] = temp;

            }

            //С шансом 10% производим мутацию в двух наиболее приспособленных
            if (rn.nextInt() % 10 < 2) {
                int mutationPoint = rn.nextInt(numberOfGenes);

                fittest.getGenes()[mutationPoint].auditory = auditories.get(rn.nextInt(auditories.size()));

                mutationPoint = rn.nextInt(numberOfGenes);

                secondFittest.getGenes()[mutationPoint].auditory = auditories.get(rn.nextInt(auditories.size()));
            }

            //Add fittest offspring to population
            service.calcFitness(fittest);
            service.calcFitness(secondFittest);

            //Get index of least fit individual
            int leastFittestIndex = population.getLeastFittestIndex();

            //Replace least fittest individual from most fittest offspring
            population.getIndividuals()[leastFittestIndex] =
                    fittest.getFitness() < secondFittest.getFitness()
                            ? fittest
                            : secondFittest;

            //Calculate new fitness value
            Arrays.stream(population.getIndividuals()).forEach(service::calcFitness);


            log.info("\nGeneration: " + generationCount + " Mean distances: " + population.getMeanDistances());
            log.info("\nGeneration: " + generationCount + " Fittest score: " + population.getFittestScore());

            //show genetic pool
//            showGeneticPool(population.getIndividuals());
        }

        log.info("\nSolution found in generation " + generationCount);
        log.info("Index of winner Individual: " + population.getFittestIndex());
        log.info("Fitness: " + population.getFittestScore());
        System.out.print("Genes: ");
        for (int i = 0; i < numberOfGenes; i++) {
            System.out.print(population.selectFittest().getGenes()[i]);
        }

        log.info("");

        lessonRepository.saveAll(service.toLessons(fittest));
    }

}
