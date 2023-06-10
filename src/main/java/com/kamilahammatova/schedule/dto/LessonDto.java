package com.kamilahammatova.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {

    private String subject;

    private String teacherFio;

    private LocalDate date;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private String time;

    private String address;

}
