package com.github.cidarosa.acrsensor.device.management.domain.repository;

import com.github.cidarosa.acrsensor.device.management.domain.model.Sensor;
import com.github.cidarosa.acrsensor.device.management.domain.model.SensorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, SensorId> {
}
