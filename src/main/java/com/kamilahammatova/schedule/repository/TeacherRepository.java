package com.kamilahammatova.schedule.repository;

import com.kamilahammatova.schedule.entities.mongodb.Student;
import com.kamilahammatova.schedule.entities.mongodb.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

  List<Teacher> findAllByStudentsContaining(Student student);

}
