package com.kamilahammatova.schedule.repository;

import com.kamilahammatova.schedule.entities.mongodb.Lesson;
import com.kamilahammatova.schedule.entities.mongodb.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllByBookingRequestTeacher(Teacher teacher);

}
