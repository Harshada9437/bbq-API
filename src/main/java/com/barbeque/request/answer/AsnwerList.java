package com.barbeque.request.answer;

import java.util.List;

/**
 * Created by System-3 on 12/21/2016.
 */
public class AsnwerList
{
    private int id;
private List<String>answerDesc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getAnswerDesc() {
        return answerDesc;
    }

    public void setAnswerDesc(List<String> answerDesc) {
        this.answerDesc = answerDesc;
    }

    @Override
    public String toString() {
        return "AsnwerList{" +
                "id=" + id +
                ", answerDesc=" + answerDesc +
                '}';
    }
}
