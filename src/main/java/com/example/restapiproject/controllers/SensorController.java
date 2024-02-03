package com.example.restapiproject.controllers;

import com.example.restapiproject.dto.SensorDTO;
import com.example.restapiproject.models.Sensor;
import com.example.restapiproject.services.SensorService;
import com.example.restapiproject.utils.InvalidSensorException;
import com.example.restapiproject.utils.InvalidSensorOrMeasurementException;
import com.example.restapiproject.utils.SensorErrorResponse;
import com.example.restapiproject.utils.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError fe: errors){
                errorMsg.append(fe.getField())
                        .append(" - ").append(fe.getDefaultMessage())
                        .append(";");
            }
            throw new InvalidSensorException(errorMsg.toString());
        }
        Sensor sensor = convertToSensor(sensorDTO);

        sensorValidator.validate(sensor, bindingResult);
        sensorService.save(sensor);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(InvalidSensorException e){
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sensorDTO, Sensor.class);
    }

}