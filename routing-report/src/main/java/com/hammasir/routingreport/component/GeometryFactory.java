package com.hammasir.routingreport.component;

import com.hammasir.routingreport.model.dto.ReportDto;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Component;

@Component
public class GeometryFactory {

    public Geometry createGeometry(ReportDto report) {
        String location = report.getLocation();
        WKTReader reader = new WKTReader();
        try {
            return reader.read(location);
        } catch (Exception e) {
            throw new IllegalArgumentException("Location is NOT valid!");
        }
    }
}