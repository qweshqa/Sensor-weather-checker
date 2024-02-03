package com.example.restapiproject.services;

import com.example.restapiproject.models.Sensor;
import com.example.restapiproject.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Optional<Sensor> findByName(String s){
        return sensorRepository.findByName(s);
    }
    @Transactional
    public void save(Sensor sensor){
        sensorRepository.save(sensor);
    }
}
