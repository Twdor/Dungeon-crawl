package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Guard extends Actor {

    public Guard(Cell cell) {
        super(cell);
        setHealth(20);
        setStrength(1);
    }


    @Override
    public String getTileName() {
        return "guard";
    }
}
