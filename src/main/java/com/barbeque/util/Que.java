package com.barbeque.util;

import com.barbeque.dto.request.AnswerDTO;

import java.util.List;

/**
 * Created by System-2 on 3/3/2017.
 */
public class Que {
    private String id;
    private char type;
    private List<AnswerDTO> ans;

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AnswerDTO> getAns() {
        return ans;
    }

    public void setAns(List<AnswerDTO> ans) {
        this.ans = ans;
    }
}
