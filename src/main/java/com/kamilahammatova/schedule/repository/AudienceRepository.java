package com.kamilahammatova.schedule.repository;

import com.kamilahammatova.schedule.entities.mongodb.Audience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudienceRepository extends JpaRepository<Audience, Long> {

}
