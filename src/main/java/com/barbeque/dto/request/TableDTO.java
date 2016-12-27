package com.barbeque.dto.request;

/**
 * Created by System-2 on 12/26/2016.
 */
public class TableDTO {
    private int id;
    private String tableName;
    private String Status;

    public String getTableName() {return tableName;}

    public void setTableName(String tableName) {this.tableName = tableName;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableDTO tableDTO = (TableDTO) o;

        if (id != tableDTO.id) return false;
        if (tableName != null ? !tableName.equals(tableDTO.tableName) : tableDTO.tableName != null) return false;
        return Status != null ? Status.equals(tableDTO.Status) : tableDTO.Status == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
        result = 31 * result + (Status != null ? Status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TableDTO{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}
