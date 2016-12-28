package com.barbeque.bo;

import com.barbeque.request.answer.AsnwerList;

import java.util.List;

/**
 * Created by System-2 on 12/15/2016.
 */
public class AnswerRequestBO {
    public List<AsnwerList> asnwerLists;

    public List<AsnwerList> getAsnwerLists() {
        return asnwerLists;
    }

    public void setAsnwerLists(List<AsnwerList> asnwerLists) {
        this.asnwerLists = asnwerLists;
    }

    @Override
    public String toString() {
        return "AnswerRequestBO{" +
                "asnwerLists=" + asnwerLists +
                '}';
    }
}
