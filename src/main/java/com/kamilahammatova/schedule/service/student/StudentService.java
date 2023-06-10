package com.kamilahammatova.schedule.service.student;

import com.kamilahammatova.schedule.entities.mongodb.Student;

import java.util.List;
import java.util.Set;

public interface StudentService {

  Student update(Long primaryId, String name, String email);

  Student addCourse(Long primaryId, Set<String> courseIds);

  Student save(Student student);

  List<Student> getAll();

  Student get(Long primaryId);
}
