package com.codecool.dungeoncrawl.logic.utils;

import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Item implements Drawable {
    String name;
    int increaseValue;

    public Item(String name, int increaseValue) {
        this.name = name;
        this.increaseValue = increaseValue;
    }

    public String getName() { return name; }

    public int getIncreaseValue() { return increaseValue; }

}
