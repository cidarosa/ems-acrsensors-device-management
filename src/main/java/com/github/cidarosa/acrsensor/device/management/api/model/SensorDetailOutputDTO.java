package com.github.cidarosa.acrsensor.device.management.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorDetailOutputDTO {

    private SensorOutputDTO sensor;
    private SensorMonitoringOutputDTO monitoring;
}
