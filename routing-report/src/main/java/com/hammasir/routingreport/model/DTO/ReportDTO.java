package com.hammasir.routingreport.model.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private String type;
    private String category;
    private String location;
    private String username;
}
