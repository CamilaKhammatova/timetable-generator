package com.kamilahammatova.schedule.controller;

import com.kamilahammatova.schedule.dto.TeacherDto;
import com.kamilahammatova.schedule.entities.mongodb.Teacher;
import com.kamilahammatova.schedule.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/teachers")
public class TeacherController {

  @Autowired
  private TeacherService teacherService;

  @GetMapping("/{primaryId}")
  public Teacher getById(@PathVariable("primaryId") Long primaryId) {
    return teacherService.getById(primaryId);
  }

  @PatchMapping("/{primaryId}")
  public Teacher update(@PathVariable("primaryId") Long primaryId, @RequestBody TeacherDto teacherDto) {
    return teacherService.update(primaryId, teacherDto);
  }

  @PostMapping
  public Teacher save(@RequestBody Teacher teacher) {
    return teacherService.save(teacher);
  }

  @GetMapping
  public List<Teacher> getAll() {
    return teacherService.getAll();
  }
}
