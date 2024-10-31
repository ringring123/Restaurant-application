package com.example.qlnhahang.Class;

public class TableMenuItems {
    private int tableMenuItemId;
    private int tableId;
    private int menuItemId;
    private int quantity;
    private String orderDate;

    public TableMenuItems(int tableMenuItemId, int tableId, int menuItemId, int quantity, String orderDate) {
        this.tableMenuItemId = tableMenuItemId;
        this.tableId = tableId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public int getTableMenuItemId() {
        return tableMenuItemId;
    }

    public void setTableMenuItemId(int tableMenuItemId) {
        this.tableMenuItemId = tableMenuItemId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
