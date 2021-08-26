package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.utils.Cell;


public abstract class Enemy extends Actor {
    protected String enemyName;

    public Enemy(Cell cell) {
        super(cell);
    }

    @Override
    public void move(int dx, int dy) {
    }

    public String getEnemyName() { return this.enemyName; }

    @Override
    public String getTileName() {
        return this.enemyName;
    }

}
