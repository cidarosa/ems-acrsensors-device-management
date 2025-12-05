package com.github.cidarosa.acrsensor.device.management.api.controller;

import com.github.cidarosa.acrsensor.device.management.api.model.SensorInputDTO;
import com.github.cidarosa.acrsensor.device.management.api.model.SensorOutputDTO;
import com.github.cidarosa.acrsensor.device.management.common.IdGenerator;
import com.github.cidarosa.acrsensor.device.management.domain.model.Sensor;
import com.github.cidarosa.acrsensor.device.management.domain.model.SensorId;
import com.github.cidarosa.acrsensor.device.management.domain.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorRepository sensorRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorOutputDTO create(@RequestBody SensorInputDTO input) {

        Sensor sensor = Sensor.builder()
                .id(new SensorId(IdGenerator.generateTSID()))
                .name(input.getName())
                .ip(input.getIp())
                .location(input.getLocation())
                .protocol(input.getProtocol())
                .model(input.getModel())
                .enabled(false)
                .build();

        sensor = sensorRepository.saveAndFlush(sensor);

        return SensorOutputDTO.builder()
                .id(sensor.getId().getValue())
                .name(sensor.getName())
                .ip(sensor.getIp())
                .location(sensor.getLocation())
                .protocol(sensor.getProtocol())
                .model(sensor.getModel())
                .enabled(sensor.getEnabled())
                .build();
    }
}
