package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.report.TrafficReport;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrafficRepository extends JpaRepository<TrafficReport, Long> {

    @Query("SELECT CASE WHEN COUNT(tr) > 0 THEN true ELSE false END " +
            "FROM TrafficReport tr WHERE ST_Equals(tr.location, ST_GeomFromText(:location, 4326)) = true " +
            "AND tr.expirationTime > CURRENT_TIMESTAMP")
    boolean existsByLocationAndExpirationTime(@Param("location") String location);

    @Query("SELECT tr FROM TrafficReport tr " +
            "WHERE ST_DWithin(ST_Transform(tr.location, 3857), ST_Transform(:location, 3857), 10) = true " +
            "AND tr.expirationTime > CURRENT_TIMESTAMP " +
            "AND tr.isApproved = true")
    List<TrafficReport> findByLocationAndExpirationTimeAndIsApproved(@Param("location") Geometry location);
}
