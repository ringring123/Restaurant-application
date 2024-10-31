package com.example.qlnhahang.Class;

public class Tables {
    private int tableId;
    private int tableNumber;
    private int capacity;
    private String status;
    private String tabledate;

    public Tables(int tableId, int tableNumber, int capacity, String status, String tabledate) {
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = status;
        this.tabledate = tabledate;
    }
    public Tables( int tableNumber, int capacity, String status, String tabledate) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = status;
        this.tabledate = tabledate;
    }

    // Getters and Setters
    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getTabledate() {
        return tabledate;
    }
    public void setTabledate(String tabledate) {
        this.tabledate = tabledate;
    }
}
