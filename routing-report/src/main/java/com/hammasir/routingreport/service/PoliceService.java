package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.CreationDTO;
import com.hammasir.routingreport.model.dto.ReportDTO;
import com.hammasir.routingreport.model.entity.AccidentReport;
import com.hammasir.routingreport.model.entity.PoliceReport;
import com.hammasir.routingreport.model.entity.Report;
import com.hammasir.routingreport.model.enums.Police;
import com.hammasir.routingreport.repository.PoliceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PoliceService {

    private final PoliceRepository policeRepository;
    private final AuthenticationService authenticationService;
    private final GeometryFactory geometryFactory;

    public ReportDTO convertToReportDto(PoliceReport report) {
        return ReportDTO.builder()
                .type(report.getType())
                .category(report.getCategory().name())
                .location(geometryFactory.createWkt(report.getLocation()))
                .like(report.getLikeCounter())
                .username(report.getUser().getUsername())
                .build();
    }

    public ReportDTO createPoliceReport(ReportDTO report) {
        boolean isExisted = policeRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            PoliceReport newReport = new PoliceReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(true);
            newReport.setLikeCounter(0);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
            newReport.setLocation(geometryFactory.createGeometry(report.getLocation()));
            newReport.setCategory(Police.fromValue(report.getCategory()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));
            return convertToReportDto(policeRepository.save(newReport));
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    public List<ReportDTO> getActivePoliceReport(String location) {
        List<PoliceReport> activeReports = policeRepository.findByIsApprovedAndLocation(geometryFactory.createGeometry(location));
        return activeReports.stream()
                .map(this::convertToReportDto)
                .collect(Collectors.toList());
    }
}
