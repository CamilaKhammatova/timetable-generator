package com.kamilahammatova.schedule.service.teacher;

import com.kamilahammatova.schedule.dto.TeacherDto;
import com.kamilahammatova.schedule.entities.mongodb.Teacher;
import com.kamilahammatova.schedule.exceptions.ObjectNotFoundException;
import com.kamilahammatova.schedule.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

  @Autowired
  private TeacherRepository teacherRepository;

  @Override
  public Teacher update(Long primaryId, TeacherDto teacherDto) {
    Teacher teacher = teacherRepository.findById(primaryId)
        .orElseThrow(() -> new ObjectNotFoundException("teacher with id %s not found".formatted(primaryId)));
//    teacher.setLessonIds(teacherDto.getCourseIds());
    teacher.setFio(teacherDto.getFio());
    teacher.setEmail(teacherDto.getEmail());
//    teacher.setPreferStudentsCount(teacherDto.getPreferStudentsCount());
//    teacher.setPreferDateRange(teacher.getPreferDateRange());
    return teacherRepository.save(teacher);
  }

  @Override
  public Teacher save(Teacher teacher) {
    return teacherRepository.save(teacher);
  }


  @Override
  public List<Teacher> getAll() {
    return teacherRepository.findAll();
  }

  @Override
  public Teacher getById(Long primaryId) {
    return teacherRepository.findById(primaryId).orElseThrow();
  }
}
