package com.github.cidarosa.acrsensor.device.management.api.model;

import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class SensorMonitoringOutputDTO {

    private TSID id;
    private Double lastTemperature;
    private OffsetDateTime updateAt;
    private Boolean enbled;

}
