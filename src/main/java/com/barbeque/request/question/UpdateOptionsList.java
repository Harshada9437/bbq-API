package com.barbeque.request.question;

/**
 * Created by System-2 on 12/24/2016.
 */
public class UpdateOptionsList {
    private int id;
    private String label;
    private int rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        return "UpdateOptionsList{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", rating=" + rating +
                '}';
    }
}
