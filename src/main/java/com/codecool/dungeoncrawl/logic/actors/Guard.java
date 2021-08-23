package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.utils.Cell;

public class Guard extends Enemy {

    public Guard(Cell cell) {
        super(cell);
        health = 300;
        strength = 50;

    }


    @Override
    public String getTileName() {
        return "guard";
    }
}
