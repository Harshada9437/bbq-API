package com.barbeque.dto.request;

/**
 * Created by System-3 on 2/8/2017.
 */
public class RollRequestDTO {
    private int roll_id;
    private String name;
    private String menu_access;
    private String outlet_access;

    public int getRoll_id() {
        return roll_id;
    }

    public void setRoll_id(int roll_id) {
        this.roll_id = roll_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenu_access() {
        return menu_access;
    }

    public void setMenu_access(String menu_access) {
        this.menu_access = menu_access;
    }

    public String getOutlet_access() {
        return outlet_access;
    }

    public void setOutlet_access(String outlet_access) {
        this.outlet_access = outlet_access;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RollRequestDTO that = (RollRequestDTO) o;

        if (roll_id != that.roll_id) return false;
        if (!name.equals(that.name)) return false;
        if (!menu_access.equals(that.menu_access)) return false;
        return outlet_access.equals(that.outlet_access);
    }

    @Override
    public int hashCode() {
        int result = roll_id;
        result = 31 * result + name.hashCode();
        result = 31 * result + menu_access.hashCode();
        result = 31 * result + outlet_access.hashCode();
        return result;
    }


}
