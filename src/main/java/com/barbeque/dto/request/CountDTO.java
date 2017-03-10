package com.barbeque.dto.request;

/**
 * Created by System-3 on 2/13/2017.
 */
public class CountDTO {
    private String questionDesc;
    private String answerDesc;
    private String  questionType;
    private int count;
    private int rating;


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public String getAnswerDesc() {
        return answerDesc;
    }

    public void setAnswerDesc(String answerDesc) {
        this.answerDesc = answerDesc;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountDTO countDTO = (CountDTO) o;

        if (count != countDTO.count) return false;
        if (rating != countDTO.rating) return false;
        if (!questionDesc.equals(countDTO.questionDesc)) return false;
        if (!answerDesc.equals(countDTO.answerDesc)) return false;
        return questionType.equals(countDTO.questionType);
    }

    @Override
    public int hashCode() {
        int result = questionDesc.hashCode();
        result = 31 * result + answerDesc.hashCode();
        result = 31 * result + questionType.hashCode();
        result = 31 * result + count;
        return result;
    }

    @Override
    public String toString() {
        return "CountDTO{" +
                "questionDesc='" + questionDesc + '\'' +
                ", answerDesc='" + answerDesc + '\'' +
                ", questionType='" + questionType + '\'' +
                ", count=" + count +
                ", rating=" + count +
                '}';
    }
}



