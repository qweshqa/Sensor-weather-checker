package com.example.restapiproject.controllers;

import com.example.restapiproject.dto.MeasurementDTO;
import com.example.restapiproject.dto.SensorDTO;
import com.example.restapiproject.models.Measurement;
import com.example.restapiproject.models.Sensor;
import com.example.restapiproject.services.MeasurementService;
import com.example.restapiproject.services.SensorService;
import com.example.restapiproject.utils.InvalidSensorOrMeasurementException;
import com.example.restapiproject.utils.SensorNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    @Autowired
    public MeasurementController(MeasurementService measurementService,
                                 SensorService sensorService,
                                 ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }
    @GetMapping()
    public List<MeasurementDTO> getAllMeasurements(){
        List<MeasurementDTO> list = new ArrayList<>();

        for(Measurement m: measurementService.findAll()){
            list.add(convertMeasurementToDTO(m));
        }
        return list;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError fe: errors){
                errorMsg.append(fe.getField())
                        .append(" - ").append(fe.getDefaultMessage())
                        .append(";");
            }
            throw new InvalidSensorOrMeasurementException(errorMsg.toString());
        }

        Optional<Sensor> sensor = sensorService.findByName(measurementDTO.getSensor().getName());

        if(sensor.isEmpty()){
            throw new SensorNotFoundException();
        }

        Measurement measurement = convertDTOtoMeasurement(measurementDTO);

        measurement.setSensor(sensor.get());
        sensor.get().add(measurement);

        measurementService.save(measurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public int rainyDaysCount(){
        return measurementService.findAllByRainingTrue().size();
    }

    public MeasurementDTO convertMeasurementToDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
    public Measurement convertDTOtoMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }
}
