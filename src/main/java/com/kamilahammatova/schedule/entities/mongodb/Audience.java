package com.kamilahammatova.schedule.entities.mongodb;

import com.kamilahammatova.schedule.dictionary.AudienceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Audience {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "audience_id")
  private Long id;

  @Column
  private String number;

  @Column
  private String address;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "tb_auditory_requirements",
          joinColumns = @JoinColumn(name = "audience_id"),
          inverseJoinColumns = @JoinColumn(name = "requirement_id"))
  private List<Requirement> requirements;

  @Column
  private Integer capacity;

  @Column
  @Enumerated(EnumType.STRING)
  private AudienceStatus status = AudienceStatus.ALLOW;

  //key - lessonid
//  private Map<String, Integer> lessonToCapacity = new HashMap<>();

}
