package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Enemy {
    public Skeleton(Cell cell) {
        super(cell);
        setHealth(200);
        setStrength(15);
    }

    @Override
    public String getTileName() { return "enemy skeleton"; }
}
