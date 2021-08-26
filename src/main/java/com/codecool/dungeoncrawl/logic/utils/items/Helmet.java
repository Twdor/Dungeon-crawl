package com.codecool.dungeoncrawl.logic.utils.items;

import com.codecool.dungeoncrawl.logic.utils.Cell;

public class Helmet extends Item{

    public Helmet(Cell cell) {
        super(cell,"helmet");
    }

    public String getTileName() {
        return "helmet";
    }

}
