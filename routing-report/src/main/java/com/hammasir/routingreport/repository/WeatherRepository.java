package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.WeatherReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherReport, Long> {
}