package com.kamilahammatova.schedule.service.teacher;

import com.kamilahammatova.schedule.dto.TeacherDto;
import com.kamilahammatova.schedule.entities.mongodb.Teacher;

import java.util.List;

public interface TeacherService {

  Teacher update(Long primaryId, TeacherDto teacherDto);

  Teacher save(Teacher teacher);

  List<Teacher> getAll();

  Teacher getById(Long primaryId);

//  Teacher updatePrefferedDateRange(String primaryId, Set<DateRange> dateRanges);
//
//  Teacher updatePreferStudentsCount(String primaryId, int preferStudentsCount);
}
