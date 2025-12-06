package com.github.cidarosa.acrsensor.device.management.domain.service;

import com.github.cidarosa.acrsensor.device.management.api.model.SensorInputDTO;
import com.github.cidarosa.acrsensor.device.management.api.model.SensorOutputDTO;
import com.github.cidarosa.acrsensor.device.management.common.IdGenerator;
import com.github.cidarosa.acrsensor.device.management.domain.model.Sensor;
import com.github.cidarosa.acrsensor.device.management.domain.model.SensorId;
import com.github.cidarosa.acrsensor.device.management.domain.repository.SensorRepository;
import com.github.cidarosa.acrsensor.device.management.domain.service.exception.ResourceNotFoundException;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;

    @Transactional(readOnly = true)
    public Page<SensorOutputDTO> findAllPageable(Pageable pageable) {

        Page<Sensor> sensors = sensorRepository.findAll(pageable);

        return sensors.map(this::convertToModel);
    }

    @Transactional(readOnly = true)
    public SensorOutputDTO findById(TSID sensorId) {

        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado. ID: " + sensorId));

        return convertToModel(sensor);
    }

    @Transactional
    public SensorOutputDTO save(SensorInputDTO inputDTO) {

        Sensor sensor = new Sensor();
        convertToEntity(inputDTO, sensor);
        sensor.setId(new SensorId(IdGenerator.generateTSID()));

        sensor = sensorRepository.saveAndFlush(sensor);

        return convertToModel(sensor);
    }

    @Transactional
    public SensorOutputDTO updateSensor(TSID sensorId, SensorInputDTO inputDTO) {

        try {
            Sensor sensor = sensorRepository.getReferenceById(new SensorId(sensorId));
            convertToEntity(inputDTO, sensor);
            sensor = sensorRepository.save(sensor);
            return convertToModel(sensor);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + sensorId);
        }
    }

    @Transactional
    public void enableSensor(TSID sensorId) {
        try {
            Sensor sensor = sensorRepository.getReferenceById(new SensorId(sensorId));
            sensor.setEnabled(true);
            sensorRepository.save(sensor);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + sensorId);
        }
    }

    @Transactional
    public void disableSensor(TSID sensorId) {

        try {
            Sensor sensor = sensorRepository.getReferenceById(new SensorId(sensorId));
            sensor.setEnabled(false);
            sensorRepository.save(sensor);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + sensorId);
        }
    }

    @Transactional
    public void delete(TSID sensorId){

        if(!sensorRepository.existsById(new SensorId(sensorId))){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + sensorId);
        }
        sensorRepository.deleteById(new SensorId(sensorId));

    }

    private void convertToEntity(SensorInputDTO inputDTO, Sensor sensor) {

        sensor.setName(inputDTO.getName());
        sensor.setIp(inputDTO.getIp());
        sensor.setLocation(inputDTO.getLocation());
        sensor.setProtocol(inputDTO.getProtocol());
        sensor.setModel(inputDTO.getModel());
        sensor.setEnabled(false);
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
