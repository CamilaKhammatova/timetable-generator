package com.kamilahammatova.schedule.service.admin;

import com.kamilahammatova.schedule.entities.mongodb.Audience;
import com.kamilahammatova.schedule.entities.mongodb.Lesson;
import com.kamilahammatova.schedule.entities.mongodb.Teacher;

public interface AdminService {

  Teacher saveTeacher(Teacher teacher);

  Lesson saveCourse(Lesson lesson);

  Audience saveAudience(Audience audience);
}
