package com.github.cidarosa.acrsensor.device.management.api.client;

import com.github.cidarosa.acrsensor.device.management.api.model.SensorMonitoringOutputDTO;
import io.hypersistence.tsid.TSID;

public interface SensorMonitoringClient {
    //representa camada REST do outro MS
    // ativar/desativar monitoramento Sensor

    void enableMonitoring(TSID sensorId);
    void disableMonitoring(TSID sensorId);
    SensorMonitoringOutputDTO getDetail(TSID sensorId);
}
