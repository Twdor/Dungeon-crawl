package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.utils.items.Item;


public class ItemModel extends BaseModel{
    private String itemName;
    private int x;
    private int y;

    public ItemModel(String itemName, int x, int y) {
        this.itemName = itemName;
        this.x = x;
        this.y = y;
    }

    public ItemModel(Item item) {
        this.itemName = item.getName();
        this.x = item.getX();
        this.y = item.getY();
    }


    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
