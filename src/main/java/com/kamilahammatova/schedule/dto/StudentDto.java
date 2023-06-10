package com.kamilahammatova.schedule.dto;

import java.util.Set;
import lombok.Data;

@Data
public class StudentDto {

  private String primaryId;
  private String fio;
  private String email;
  private Set<String> courseIds;
}
