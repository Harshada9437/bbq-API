package com.barbeque.exceptions;

/**
 * Created by System1 on 9/23/2016.
 */
public class TemplateNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public TemplateNotFoundException(String message) {
        super(message);
    }
}
