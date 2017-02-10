package com.barbeque.exceptions;

/**
 * Created by System-2 on 2/10/2017.
 */
public class DeviceNotFoundException extends Exception
{
    private static final long serialVersionUID = 1L;
    public DeviceNotFoundException(String message){
        super(message);
    }
}