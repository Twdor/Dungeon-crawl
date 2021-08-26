package com.codecool.dungeoncrawl.logic.utils.items;

import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.utils.Cell;


public abstract class Item implements Drawable {
    private Cell cell;
    private String name;
    private int x;
    private int y;

    public Item(Cell cell, String name) {
        this.name = name;
        this.x = cell.getX();
        this.y = cell.getY();
        this.cell = cell;
        this.cell.setItem(this);
    }

    public String getName() { return name; }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
