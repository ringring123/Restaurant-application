package com.example.qlnhahang.Class;

public class DailyMenuItems {
    private int dailyMenuItemId;    // ID của DailyMenuItem
    private int dailyMenuId;        // ID của DailyMenu
    private int menuItemId;         // ID của MenuItem

    // Constructor không tham số
    public DailyMenuItems() {
    }

    // Constructor với tất cả các tham số
    public DailyMenuItems(int dailyMenuItemId, int dailyMenuId, int menuItemId) {
        this.dailyMenuItemId = dailyMenuItemId;
        this.dailyMenuId = dailyMenuId;
        this.menuItemId = menuItemId;
    }
    public DailyMenuItems(int dailyMenuId, int menuItemId) {
        this.dailyMenuId = dailyMenuId;
        this.menuItemId = menuItemId;
    }

    // Getter và Setter cho dailyMenuItemId
    public int getDailyMenuItemId() {
        return dailyMenuItemId;
    }

    public void setDailyMenuItemId(int dailyMenuItemId) {
        this.dailyMenuItemId = dailyMenuItemId;
    }

    // Getter và Setter cho dailyMenuId
    public int getDailyMenuId() {
        return dailyMenuId;
    }

    public void setDailyMenuId(int dailyMenuId) {
        this.dailyMenuId = dailyMenuId;
    }

    // Getter và Setter cho menuItemId
    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }
}
