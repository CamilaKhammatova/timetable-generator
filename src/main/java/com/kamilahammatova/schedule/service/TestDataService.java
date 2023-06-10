package com.kamilahammatova.schedule.service;

import com.kamilahammatova.schedule.dictionary.BookingPeriod;
import com.kamilahammatova.schedule.entities.mongodb.*;
import com.kamilahammatova.schedule.repository.*;
import com.kamilahammatova.schedule.service.algorithm.GeneticAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TestDataService {

    private final BookingRequestRepository requestRepository;
    private final RequirementRepository requirementRepository;
    private final AudienceRepository audienceRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final GeneticAlgorithm geneticAlgorithm;

    @PostConstruct
    public void postConstruct() {
        if (requirementRepository.findAll().isEmpty()) {
            loadTeachers();
            loadStudents();
            loadRequirement();
            loadAuditories();
            loadRequests();
            geneticAlgorithm.train();
        }
    }

    @SneakyThrows
    public void loadRequirement() {
        List<String> types = List.of("Проектор", "Трибуна", "Лаборатория");
        types.forEach(type -> requirementRepository.saveAndFlush(
                Requirement.builder()
                        .type(type)
                        .build()));
    }

    @SneakyThrows
    public void loadRequests() {
        var file = new File("src/main/resources/data/requests.csv");

        var sc = new Scanner(file);
        sc.nextLine();

        for (int i = 0; i < 28; i++) {
            var str = sc.nextLine().split(",");

            var request = new BookingRequest();
            request.setName(str[0]);
            request.setPeriod(BookingPeriod.WEEKLY);
            request.setStartDate(LocalDate.parse("2022-02-07"));
            request.setFinishDate(LocalDate.parse("2022-06-30"));
            request.setTime(str[3]);
            request.setGroup(str[4]);
            request.setCapacity(Integer.parseInt(str[6]));

            if (str.length > 7) {
                var requirement = str[7];
                request.setRequirements(Set.of(
                        requirementRepository.findByType(requirement)));
            }
            var teacher = teacherRepository.findById(Long.valueOf(str[5])).orElseThrow();
            request.setTeacher(teacher);
            teacher.getTeacherBookingRequest().add(request);

            teacherRepository.saveAndFlush(teacher);
            requestRepository.saveAndFlush(request);
        }
    }

    @SneakyThrows
    public void loadTeachers() {
        var file = new File("src/main/resources/data/teachers.csv");

        var sc = new Scanner(file);
        sc.nextLine();

        for (int i = 0; i < 5; i++) {
            var str = sc.nextLine().split(",");

            var teacher = new Teacher();
            teacher.setId(Long.valueOf(str[0]));
            teacher.setFio(str[1]);
            teacher.setEmail(str[2]);
            teacher.setPreferStudentsCount(Integer.parseInt(str[3]));
            teacher.setSubjectName(str[4]);

            teacherRepository.saveAndFlush(teacher);
        }
    }

    @SneakyThrows
    public void loadStudents() {
        var file = new File("src/main/resources/data/students.csv");

        var sc = new Scanner(file);
        sc.nextLine();

        for (int i = 0; i < 5; i++) {
            var str = sc.nextLine().split(",");

            var student = new Student();
            student.setId(Long.valueOf(str[0]));
            student.setFio(str[1]);
            student.setEmail(str[2]);
            for (var teacherId : str[3].split(";")) {
                var teacher = teacherRepository.findById(Long.valueOf(teacherId)).orElseThrow();
                student.getTeachers().add(teacher);
                teacher.getStudents().add(student);
                studentRepository.saveAndFlush(student);
                teacherRepository.save(teacher);
            }

            studentRepository.saveAndFlush(student);
        }
    }

    @SneakyThrows
    public void loadAuditories() {

        var file = new File("src/main/resources/data/auditory.csv");

        var sc = new Scanner(file);
        sc.nextLine();

        for (int i = 0; i < 87; i++) {
            var str = sc.nextLine().split(",");

            var auditory = new Audience();
            auditory.setId(Long.valueOf(str[0]));
            auditory.setAddress(str[1] + " " + str[2] + " " + str[3]);

            auditory.setNumber(str[4]);

            if (str.length < 6) {
                continue;
            }
            var capacity = str[5];
            auditory.setCapacity(Integer.parseInt(capacity));

            if (str.length > 6) {
                var type = str[6];
                var requirement = requirementRepository.findByType(type);
                auditory.setRequirements(List.of(requirement));
            }
            audienceRepository.saveAndFlush(auditory);
        }
    }

}
