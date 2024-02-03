package com.example.restapiproject.utils;

public class InvalidSensorOrMeasurementException extends RuntimeException{
    public InvalidSensorOrMeasurementException(String msg){
        super(msg);
    }
}
