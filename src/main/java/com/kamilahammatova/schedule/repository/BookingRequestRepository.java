package com.kamilahammatova.schedule.repository;

import com.kamilahammatova.schedule.entities.mongodb.BookingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookingRequestRepository extends JpaRepository<BookingRequest, Long> {

    List<BookingRequest> findAllByTeacherId(Long teacherId);

}
