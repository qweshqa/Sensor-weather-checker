package com.example.restapiproject.services;

import com.example.restapiproject.models.Measurement;
import com.example.restapiproject.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }
    public List<Measurement> findAll(){
        return measurementRepository.findAll();
    }

    public List<Measurement> findAllByRainingTrue(){
        return measurementRepository.findAllByRainingTrue();
    }
    @Transactional
    public void save(Measurement measurement){
        measurementRepository.save(measurement);
    }
}
