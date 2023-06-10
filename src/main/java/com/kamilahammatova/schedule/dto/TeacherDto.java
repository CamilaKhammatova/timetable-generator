package com.kamilahammatova.schedule.dto;

import com.kamilahammatova.schedule.entities.sbeans.DateRange;
import lombok.Data;

import java.util.Set;

@Data
public class TeacherDto {

  private String primaryId;
  private String fio;
  private String email;
  private Set<String> courseIds;
  private Set<DateRange> dateRanges;
//  private int preferStudentsCount;
}
