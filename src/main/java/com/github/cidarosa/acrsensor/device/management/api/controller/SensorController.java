package com.github.cidarosa.acrsensor.device.management.api.controller;

import com.github.cidarosa.acrsensor.device.management.api.model.SensorInputDTO;
import com.github.cidarosa.acrsensor.device.management.api.model.SensorOutputDTO;
import com.github.cidarosa.acrsensor.device.management.common.IdGenerator;
import com.github.cidarosa.acrsensor.device.management.domain.model.Sensor;
import com.github.cidarosa.acrsensor.device.management.domain.model.SensorId;
import com.github.cidarosa.acrsensor.device.management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorRepository sensorRepository;

    @GetMapping
    public Page<SensorOutputDTO> search(@PageableDefault Pageable pageable) {

        Page<Sensor> sensors = sensorRepository.findAll(pageable);

        return sensors.map(this::convertToModel);

    }

    @GetMapping("{sensorId}")
    public SensorOutputDTO getOne(@PathVariable TSID sensorId) {
        //criar um conversor TSID para sensoId - usando Spring
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return convertToModel(sensor);
    }


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

        return convertToModel(sensor);
    }

    @PutMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableSensorById(@PathVariable TSID sensorId){

        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensor.setEnabled(true);

        sensorRepository.save(sensor);
    }

    @DeleteMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableSensorById(@PathVariable TSID sensorId){

        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensor.setEnabled(false);

        sensorRepository.save(sensor);
    }

    @PutMapping("/{sensorId}")
    @ResponseStatus(HttpStatus.OK)
    public SensorOutputDTO updateSensor(@PathVariable TSID sensorId,
                                        @RequestBody SensorInputDTO input) {

        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensor.setName(input.getName());
        sensor.setIp(input.getIp());
        sensor.setLocation(input.getLocation());
        sensor.setProtocol(input.getProtocol());
        sensor.setModel(input.getModel());

        sensor = sensorRepository.save(sensor);

        return convertToModel(sensor);
    }

    @DeleteMapping("{sensorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSensor(@PathVariable TSID sensorId) {

        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        sensorRepository.deleteById(sensor.getId());
    }

    private SensorOutputDTO convertToModel(Sensor sensor) {
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
