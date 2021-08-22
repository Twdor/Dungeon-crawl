package com.codecool.dungeoncrawl.logic.utils;

public class Sword extends Item{

    public Sword(String name, int increaseValue) {
        super(name, increaseValue);
    }

    public String getTileName() {
        return "sword";
    }
}
