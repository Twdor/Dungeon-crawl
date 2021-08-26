package com.codecool.dungeoncrawl.logic.utils.items;

import com.codecool.dungeoncrawl.logic.utils.Cell;

public class Key extends Item{

    public Key(Cell cell) { super(cell, "key"); }

    public String getTileName() {
        return "key";
    }

}
