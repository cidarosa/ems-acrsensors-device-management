package com.github.cidarosa.acrsensor.device.management.api.client;

import com.github.cidarosa.acrsensor.device.management.api.model.SensorMonitoringOutputDTO;
import io.hypersistence.tsid.TSID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

@HttpExchange("/api/sensors/{sensorId}/monitoring")
public interface SensorMonitoringClient {
    //representa camada REST do outro MS
    // ativar/desativar monitoramento Sensor

    @PutExchange("/enable")
    void enableMonitoring(@PathVariable TSID sensorId);

    @DeleteExchange("/enable")
    void disableMonitoring(@PathVariable TSID sensorId);

    @GetExchange
    SensorMonitoringOutputDTO getDetail(@PathVariable TSID sensorId);
}
