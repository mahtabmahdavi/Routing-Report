package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.TrafficReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficRepository extends JpaRepository<TrafficReport, Long> {
}