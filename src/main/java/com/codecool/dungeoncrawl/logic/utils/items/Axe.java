package com.codecool.dungeoncrawl.logic.utils.items;

import com.codecool.dungeoncrawl.logic.utils.Cell;

public class Axe extends Item{

    public Axe(Cell cell) {super(cell,"axe");}

    public String getTileName() {
        return "axe";
    }
}
