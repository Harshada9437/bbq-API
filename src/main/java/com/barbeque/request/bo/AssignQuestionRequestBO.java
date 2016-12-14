package com.barbeque.request.bo;

/**
 * Created by System1 on 9/27/2016.
 */
public class AssignQuestionRequestBO {
    private int questionId;
    private int priority;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssignQuestionRequestBO that = (AssignQuestionRequestBO) o;

        if (questionId != that.questionId) return false;
        return priority == that.priority;

    }

    @Override
    public int hashCode() {
        int result = questionId;
        result = 31 * result + priority;
        return result;
    }

    @Override
    public String toString() {
        return "AssignQuestionRequestBO{" +
                "questionId=" + questionId +
                ", priority=" + priority +
                '}';
    }
}
