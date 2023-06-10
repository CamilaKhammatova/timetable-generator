package com.kamilahammatova.schedule.entities.mongodb;

import com.kamilahammatova.schedule.dictionary.LessonTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lesson {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @Column
  @Enumerated(EnumType.STRING)
  private LessonTime time;

  @Column
  private LocalDate date;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "audience_id")
  private Audience audience;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "request_id")
  private BookingRequest bookingRequest;

}
