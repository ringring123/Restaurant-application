package com.example.qlnhahang.Class;

public class MenuItems {
    private int menuItemId;
    private String name;
    private String description;
    private double price;
    private String image;
    private int quantity;

    public MenuItems(String name, double price, String description, String image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }
    public MenuItems(int id,String name, double price, String description, String image) {
        this.menuItemId = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }
    public MenuItems(int id,String name, double price, String description, String image, int quantity) {
        this.menuItemId = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
