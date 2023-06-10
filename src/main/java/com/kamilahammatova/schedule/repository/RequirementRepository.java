package com.kamilahammatova.schedule.repository;

import com.kamilahammatova.schedule.entities.mongodb.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequirementRepository extends JpaRepository<Requirement, Long> {

    Requirement findByType(String name);

}
