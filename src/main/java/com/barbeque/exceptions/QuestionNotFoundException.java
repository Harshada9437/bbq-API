package com.barbeque.exceptions;

/**
 * Created by System-2 on 12/20/2016.
 */
public class QuestionNotFoundException extends Exception
{
    private static final long serialVersionUID = 1L;
    public QuestionNotFoundException(String message){
        super(message);
    }

}
