package com.kamilahammatova.schedule.entities.mongodb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"teachers"})
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String fio;

  @Column
  private String email;

  @JsonIgnore
  @ManyToMany(mappedBy = "students")
  private Set<Teacher> teachers = new HashSet<>();

}
