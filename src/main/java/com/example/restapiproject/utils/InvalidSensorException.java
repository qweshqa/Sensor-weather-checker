package com.example.restapiproject.utils;

public class InvalidSensorException extends RuntimeException {
    public InvalidSensorException(String msg){
        super(msg);
    }
}
