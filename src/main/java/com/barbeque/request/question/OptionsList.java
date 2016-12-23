package com.barbeque.request.question;

/**
 * Created by System-3 on 12/23/2016.
 */
public class OptionsList
{
    private String label;
    private int rating;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "OptionsList{" +
                "label='" + label + '\'' +
                ", rating=" + rating +
                '}';
    }
}
