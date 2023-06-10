package com.kamilahammatova.schedule.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonStudentCountDto {

    private String subject;

    private Integer count;

}
