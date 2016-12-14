package com.barbeque.response.question;
import com.barbeque.response.util.GenericResponse;
import java.util.List;

/**
 * Created by System1 on 9/21/2016.
 */
public class QuestionResponseList implements GenericResponse {
    private List<QuestionResponse> questions;
    private String messageType;
    private String message;

    public List<QuestionResponse> getQuestions() {return questions;}

    public void setQuestions(List<QuestionResponse> questions) {this.questions = questions;}

    public String getMessageType() {return messageType;}

    public void setMessageType(String messageType) {this.messageType = messageType;}

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    @Override
    public String toString() {
        return "QuestionResponseList{" +
                "questions=" + questions +
                ", messageType='" + messageType + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
