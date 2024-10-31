package com.example.qlnhahang.Class;

import java.util.ArrayList;

public class DailyMenu {
    private int dailyMenuId;
    private String menuDate;


    public DailyMenu(String menuDate) {
        this.menuDate = menuDate;
    }

    public DailyMenu(int dailyMenuId, String menuDate) {
        this.dailyMenuId = dailyMenuId;
        this.menuDate = menuDate;
    }

    public String getDate() {
        return menuDate;
    }

    public void setDate(String date) {
        this.menuDate = date;
    }
}
