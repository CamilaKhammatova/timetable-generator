package com.kamilahammatova.schedule.entities.mongodb;

import com.kamilahammatova.schedule.dictionary.BookingPeriod;
import com.kamilahammatova.schedule.dictionary.LessonTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_request_id")
    private Long id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private LessonTime time;

    @Column
    private LocalDate finishDate;

    @Column
    private LocalDate startDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_booking_request_requirements",
            joinColumns = @JoinColumn(name = "booking_request_id"),
            inverseJoinColumns = @JoinColumn(name = "requirement_id"))
    private Set<Requirement> requirements;

    @Column
    private Integer capacity;

    @Column(name = "group_column")
    private String group;

    @Column
    @Enumerated(EnumType.STRING)
    private BookingPeriod period;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public void setTime(String time) {
        this.time = switch (time) {
            case "1" -> LessonTime.FIRST;
            case "2" -> LessonTime.SECOND;
            case "3" -> LessonTime.THIRD;
            case "4" -> LessonTime.FOURTH;
            case "5" -> LessonTime.FIFTH;
            case "6" -> LessonTime.SIXTH;
            default -> throw new IllegalArgumentException();
        };
    }

}