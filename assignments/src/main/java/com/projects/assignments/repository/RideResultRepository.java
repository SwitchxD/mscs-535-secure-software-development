package com.projects.assignments.repository;

import com.projects.assignments.entity.RideResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideResultRepository extends JpaRepository<RideResult, Long> {
}
