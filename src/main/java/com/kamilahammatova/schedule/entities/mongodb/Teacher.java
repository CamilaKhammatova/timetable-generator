package com.kamilahammatova.schedule.entities.mongodb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"students", "teacherBookingRequest"})
public class Teacher {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String fio;

  @Column
  private String email;

  @Column
  private String subjectName;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "teacher_user",
          joinColumns = @JoinColumn(name = "teacher_id"),
          inverseJoinColumns = @JoinColumn(name = "student_id"))
  private Set<Student> students = new HashSet<>();

  @JsonIgnore
  @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
  private Set<BookingRequest> teacherBookingRequest;

  @Column
  private int preferStudentsCount;

}
