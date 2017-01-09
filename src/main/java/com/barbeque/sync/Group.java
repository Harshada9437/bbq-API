package com.barbeque.sync;

/**
 * Created by System-2 on 1/9/2017.
 */
public class Group {
    private int id;
    private String desc;
    private String shortDesc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    @Override
    public String toString() {
        return "Sync{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                '}';
    }
}
