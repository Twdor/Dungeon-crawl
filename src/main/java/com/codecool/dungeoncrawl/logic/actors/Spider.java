package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.utils.Cell;

public class Spider extends Enemy {

    public Spider(Cell cell) {
        super(cell);
        health = 100;
        strength = 10;
        this.enemyName = "spider";
    }

}
