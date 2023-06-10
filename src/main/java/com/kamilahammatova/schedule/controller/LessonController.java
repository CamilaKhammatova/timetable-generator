package com.kamilahammatova.schedule.controller;

import com.kamilahammatova.schedule.dto.LessonDto;
import com.kamilahammatova.schedule.dto.LessonStudentCountDto;
import com.kamilahammatova.schedule.entities.mongodb.Lesson;
import com.kamilahammatova.schedule.service.course.LessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/lessons")
public class LessonController {

  @Autowired
  private LessionService lessionService;

  @PostMapping
  public Lesson save(@RequestBody Lesson lesson) {
    return lessionService.save(lesson);
  }

  @GetMapping("/student/{studentId}")
  public List<LessonDto> getAllStudentLessons(@PathVariable("studentId") Long studentId) {
    return lessionService.getAllByStudentId(studentId);
  }

  @GetMapping("/teacher/{teacherId}/count")
  public List<LessonStudentCountDto> getLessonStudentCount(@PathVariable("teacherId") Long teacherId) {
    return lessionService.getLessonStudentCount(teacherId);
  }


  @GetMapping("/teacher/{teacherId}")
  public List<LessonDto> getTeacherLessons(@PathVariable("teacherId") Long teacherId) {
    return lessionService.getTeacherLessons(teacherId);
  }

  @GetMapping
  public List<Lesson> getAll() {
    return lessionService.getAll();
  }
}
