package com.codecool.dungeoncrawl.logic.utils.items;

import com.codecool.dungeoncrawl.logic.utils.Cell;

public class Armour extends Item {

    public Armour(Cell cell) { super(cell,"armour"); }

    public String getTileName() {
        return "armour";
    }

}
