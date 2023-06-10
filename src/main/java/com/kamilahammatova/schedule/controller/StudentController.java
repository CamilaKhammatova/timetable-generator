package com.kamilahammatova.schedule.controller;

import com.kamilahammatova.schedule.dto.StudentDto;
import com.kamilahammatova.schedule.entities.mongodb.Student;
import com.kamilahammatova.schedule.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/students")
public class StudentController {

  @Autowired
  private StudentService studentService;

  @GetMapping("/{primaryId}")
  public Student getStudent(@PathVariable("primaryId") Long primaryId) {
    return studentService.get(primaryId);
  }

  @PatchMapping("/{primaryId}")
  public Student update(@PathVariable("primaryId") Long primaryId, @RequestBody StudentDto studentDto) {
    return studentService.update(primaryId, studentDto.getFio(), studentDto.getEmail());
  }

  @PutMapping("/{primaryId}")
  public Student addCourse(@PathVariable("primaryId") Long primaryId, @RequestBody StudentDto studentDto) {
    return studentService.addCourse(primaryId, studentDto.getCourseIds());
  }

  @PostMapping("/students")
  public Student save(@RequestBody Student student) {
    return studentService.save(student);
  }

  @GetMapping
  public List<Student> getAll() {
    return studentService.getAll();
  }
}
