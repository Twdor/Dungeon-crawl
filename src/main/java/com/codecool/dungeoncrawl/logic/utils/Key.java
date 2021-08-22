package com.codecool.dungeoncrawl.logic.utils;

public class Key extends Item{

    public Key(String name, int increaseValue) {
        super(name, increaseValue);
    }


    public String getTileName() {
        return "key";
    }
}
