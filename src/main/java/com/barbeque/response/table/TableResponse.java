package com.barbeque.response.table;

/**
 * Created by System-3 on 12/10/2016.
 */
public class TableResponse
{
    private int id;
    private String tableName;
    private String Status;

    public TableResponse(int id, String status,String tableName) {
        this.id = id;
        this.Status = status;
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

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
    public String toString() {
        return "TableResponse{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}
