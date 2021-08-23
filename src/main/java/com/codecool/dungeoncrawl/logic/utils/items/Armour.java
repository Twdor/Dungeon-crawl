package com.codecool.dungeoncrawl.logic.utils.items;

import com.codecool.dungeoncrawl.logic.utils.items.Item;

public class Armour extends Item {

    public Armour(String name, int increaseValue) {
        super(name, increaseValue);
    }

    public String getTileName() {
        return "armour";
    }
}
