package com.kamilahammatova.schedule.service.course;

import com.kamilahammatova.schedule.dto.LessonDto;
import com.kamilahammatova.schedule.dto.LessonStudentCountDto;
import com.kamilahammatova.schedule.entities.mongodb.Lesson;
import com.kamilahammatova.schedule.repository.BookingRequestRepository;
import com.kamilahammatova.schedule.repository.LessonRepository;
import com.kamilahammatova.schedule.repository.StudentRepository;
import com.kamilahammatova.schedule.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessionServiceImpl implements LessionService {

    @Autowired
    private LessonRepository lessonRepostory;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BookingRequestRepository bookingRequestRepository;

    @Override
    public Lesson save(Lesson lesson) {
        return lessonRepostory.save(lesson);
    }

    @Override
    public List<Lesson> getAll() {
        return lessonRepostory.findAll();
    }

    @Override
    public List<LessonDto> getAllByStudentId(Long studentId) {
        var student = studentRepository.findById(studentId).orElseThrow();
        var teachers = teacherRepository.findAllByStudentsContaining(student);
        List<LessonDto> result = new ArrayList<>();

        teachers.forEach(teacher -> {
            var lessons = lessonRepostory.findAllByBookingRequestTeacher(teacher);
            lessons.forEach(lesson -> {
                result.add(LessonDto.builder()
                                .subject(lesson.getBookingRequest().getTeacher().getSubjectName())
                                .teacherFio(lesson.getBookingRequest().getTeacher().getFio())
                                .time(lesson.getBookingRequest().getTime().time)
                                .address(lesson.getAudience().getAddress())
                                .date(lesson.getDate())
                        .build());
            });
        });

        return result;
    }

    @Override
    public List<LessonStudentCountDto> getLessonStudentCount(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .map(teacher -> new LessonStudentCountDto(teacher.getSubjectName(), teacher.getStudents().size() + 27))
                .map(List::of)
                .orElseThrow();
    }

    @Override
    public List<LessonDto> getTeacherLessons(Long teacherId) {
        var teacher = teacherRepository.findById(teacherId).orElseThrow();
        var lessons = lessonRepostory.findAllByBookingRequestTeacher(teacher);
        List<LessonDto> result = new ArrayList<>();

        lessons.forEach(lesson -> {
            result.add(LessonDto.builder()
                    .subject(lesson.getBookingRequest().getTeacher().getSubjectName())
                    .teacherFio(lesson.getBookingRequest().getTeacher().getFio())
                    .time(lesson.getBookingRequest().getTime().time)
                    .address(lesson.getAudience().getAddress())
                    .date(lesson.getDate())
                    .build());
        });

        return result;
    }


}
