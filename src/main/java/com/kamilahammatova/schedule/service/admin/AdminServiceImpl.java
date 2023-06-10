package com.kamilahammatova.schedule.service.admin;

import com.kamilahammatova.schedule.entities.mongodb.Audience;
import com.kamilahammatova.schedule.entities.mongodb.Lesson;
import com.kamilahammatova.schedule.entities.mongodb.Teacher;
import com.kamilahammatova.schedule.repository.AudienceRepository;
import com.kamilahammatova.schedule.repository.LessonRepository;
import com.kamilahammatova.schedule.repository.StudentRepository;
import com.kamilahammatova.schedule.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

  @Autowired
  private TeacherRepository teacherRepository;
  @Autowired
  private LessonRepository lessonRepostory;
  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  private AudienceRepository audienceRepository;

  @Override
  public Teacher saveTeacher(Teacher teacher) {
    return teacherRepository.save(teacher);
  }

  @Override
  public Lesson saveCourse(Lesson lesson) {
    return lessonRepostory.save(lesson);
  }

  @Override
  public Audience saveAudience(Audience audience) {
    return audienceRepository.save(audience);
  }
}
