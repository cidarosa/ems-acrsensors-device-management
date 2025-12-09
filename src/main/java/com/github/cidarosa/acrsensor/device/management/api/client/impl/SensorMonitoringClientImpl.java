package com.github.cidarosa.acrsensor.device.management.api.client.impl;

import com.github.cidarosa.acrsensor.device.management.api.client.SensorMonitoringClient;
import com.github.cidarosa.acrsensor.device.management.api.client.SensorMonitoringClientBadGatewayException;
import io.hypersistence.tsid.TSID;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {

    //comunicação com RestClient - recebe URL do MS que está consumindo (monitoring)
    private final RestClient restClient;

//com Builder, chama bind do Builder que o Spring já carregou, com config que o Spring adiciona
    public SensorMonitoringClientImpl(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8082")
                //tratamento exceptions, qualquer tipo de erro
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    throw new SensorMonitoringClientBadGatewayException();
                })

                .build();
    }

    public void enableMonitoring(TSID sensorId) {

        restClient.put()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();

    }

    @Override
    public void disableMonitoring(TSID sensorId) {

        restClient.delete()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();

    }
}
