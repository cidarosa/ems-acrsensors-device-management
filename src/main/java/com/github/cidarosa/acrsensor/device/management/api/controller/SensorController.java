package com.github.cidarosa.acrsensor.device.management.api.controller;

import com.github.cidarosa.acrsensor.device.management.api.model.SensorDetailOutputDTO;
import com.github.cidarosa.acrsensor.device.management.api.model.SensorInputDTO;
import com.github.cidarosa.acrsensor.device.management.api.model.SensorOutputDTO;
import com.github.cidarosa.acrsensor.device.management.domain.service.SensorService;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @GetMapping
    public ResponseEntity<Page<SensorOutputDTO>> search(@PageableDefault Pageable pageable) {

        Page<SensorOutputDTO> sensors = sensorService.findAllPageable(pageable);

        return ResponseEntity.ok(sensors);
    }

    @GetMapping("{sensorId}")
    public ResponseEntity<SensorOutputDTO> getOne(@PathVariable TSID sensorId) {

        SensorOutputDTO sensorDTO = sensorService.findById(sensorId);

        return ResponseEntity.ok(sensorDTO);
    }

    @GetMapping("{sensorId}/detail")
    public ResponseEntity<SensorDetailOutputDTO> getOneWithDetail(@PathVariable TSID sensorId) {

        SensorDetailOutputDTO sensorDetailDTO = sensorService.findByIdWithDetail(sensorId);

        return ResponseEntity.ok(sensorDetailDTO);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SensorOutputDTO> create(@RequestBody SensorInputDTO input) {

        SensorOutputDTO sensorDTO = sensorService.save(input);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("{id}")
                .buildAndExpand(sensorDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(sensorDTO);
    }

    @PutMapping("/{sensorId}/enable")
    public ResponseEntity<Void> enableSensorById(@PathVariable TSID sensorId) {

        sensorService.enableSensor(sensorId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{sensorId}/enable")
    public ResponseEntity<Void> disableSensorById(@PathVariable TSID sensorId) {

        sensorService.disableSensor(sensorId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{sensorId}")
    public ResponseEntity<SensorOutputDTO> updateSensor(@PathVariable TSID sensorId,
                                                        @RequestBody SensorInputDTO input) {

        SensorOutputDTO sensorDTO = sensorService.updateSensor(sensorId, input);

        return ResponseEntity.ok(sensorDTO);
    }

    @DeleteMapping("{sensorId}")
    public ResponseEntity<Void> deleteSensor(@PathVariable TSID sensorId) {

        sensorService.delete(sensorId);

        return ResponseEntity.noContent().build();
    }
}
