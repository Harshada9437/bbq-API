package com.barbeque.exceptions;

/**
 * Created by System-2 on 12/26/2016.
 */
public class OutletNotFoundException extends Exception
{
    private static final long serialVersionUID = 1L;
    public OutletNotFoundException(String message){
        super(message);
    }
}
