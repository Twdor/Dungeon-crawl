package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.utils.Cell;



public class Skeleton extends Enemy {

    public Skeleton(Cell cell) {
        super(cell);
        this.health = 100;
        this.strength = 10;
        this.enemyName = "skeleton";
    }


}
