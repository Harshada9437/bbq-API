package com.barbeque.exceptions;

/**
 * Created by System-3 on 2/15/2017.
 */
public class CustomerNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    public CustomerNotFoundException(String message)
    {
        super(message);
    }
}
