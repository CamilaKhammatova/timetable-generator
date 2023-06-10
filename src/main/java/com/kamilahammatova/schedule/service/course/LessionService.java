package com.kamilahammatova.schedule.service.course;

import com.kamilahammatova.schedule.dto.LessonDto;
import com.kamilahammatova.schedule.dto.LessonStudentCountDto;
import com.kamilahammatova.schedule.entities.mongodb.Lesson;

import java.util.List;

public interface LessionService {

    Lesson save(Lesson lesson);

    List<Lesson> getAll();

    List<LessonDto> getAllByStudentId(Long studentId);

    List<LessonStudentCountDto> getLessonStudentCount(Long teacherId);

    List<LessonDto> getTeacherLessons(Long teacherId);

}
