package com.example.restapiproject.utils;

import com.example.restapiproject.models.Sensor;
import com.example.restapiproject.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Optional;

@Component
public class SensorValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        Optional<Sensor> optionalSensor = sensorService.findByName(sensor.getName());

        if(optionalSensor.isPresent()){
            errors.rejectValue("name", "", "This sensor already exists");
            throw new InvalidSensorException("This sensor already exists");
        }
    }
}
