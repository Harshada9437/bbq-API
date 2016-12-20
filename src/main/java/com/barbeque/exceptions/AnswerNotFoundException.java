package com.barbeque.exceptions;

/**
 * Created by System-2 on 12/19/2016.
 */
public class AnswerNotFoundException extends Exception
{
    private static final long serialVersionUID = 1L;
    public AnswerNotFoundException(String message){
        super(message);
    }
}
