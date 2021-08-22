package com.codecool.dungeoncrawl.logic.utils;

public class Armour extends Item{

    public Armour(String name, int increaseValue) {
        super(name, increaseValue);
    }

    public String getTileName() {
        return "armour";
    }
}
