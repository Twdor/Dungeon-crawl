package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.utils.Cell;


public abstract class Enemy extends Actor {

    public Enemy(Cell cell) {
        super(cell);
    }

    @Override
    public void move(int dx, int dy) {
    }

    @Override
    public String getTileName() {
        return null;
    }

}
