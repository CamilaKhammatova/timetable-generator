package com.kamilahammatova.schedule.repository;

import com.kamilahammatova.schedule.entities.mongodb.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Long> {

}
