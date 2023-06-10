package com.kamilahammatova.schedule.service.student;

import com.kamilahammatova.schedule.entities.mongodb.Student;
import com.kamilahammatova.schedule.exceptions.ObjectNotFoundException;
import com.kamilahammatova.schedule.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {

  @Autowired
  private StudentRepository studentRepository;

  @Override
  public Student update(Long primaryId, String fio, String email) {
    Student student = studentRepository.findById(primaryId)
        .orElseThrow(() -> new ObjectNotFoundException("student with id %s not found".formatted(primaryId)));
    student.setFio(fio);
    student.setEmail(email);
    studentRepository.save(student);
    return student;
  }

  @Override
  public Student addCourse(Long primaryId, Set<String> courseIds) {
    Student student = studentRepository.findById(primaryId)
        .orElseThrow(() -> new ObjectNotFoundException("student with id %s not found".formatted(primaryId)));
//    student.setLessonIds(courseIds);
    studentRepository.save(student);
    return student;
  }

  @Override
  public Student save(Student student) {
    return studentRepository.save(student);
  }

  @Override
  public List<Student> getAll() {
    return studentRepository.findAll();
  }

  @Override
  public Student get(Long primaryId) {
    return studentRepository.findById(primaryId).orElseThrow();
  }
}
