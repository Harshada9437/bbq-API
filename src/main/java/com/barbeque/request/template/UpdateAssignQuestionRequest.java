package com.barbeque.request.template;

/**
 * Created by System-3 on 1/4/2017.
 */
public class UpdateAssignQuestionRequest
{
    private int questionId;
    private int priority;
    private int templateId;




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

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateAssignQuestionRequest that = (UpdateAssignQuestionRequest) o;

        if (questionId != that.questionId) return false;
        if (priority != that.priority) return false;
        return templateId == that.templateId;
    }

    @Override
    public int hashCode() {
        int result = questionId;
        result = 31 * result + priority;
        result = 31 * result + templateId;
        return result;
    }

    @Override
    public String toString() {
        return "UpdateAssignQuestionRequest{" +
                "questionId=" + questionId +
                ", priority=" + priority +
                ", templateId=" + templateId +
                '}';
    }
}
