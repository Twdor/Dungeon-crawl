package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Enemy;


public class EnemyModel extends BaseModel{
    private String enemyName;
    private int hp;
    private int x;
    private int y;

    public EnemyModel(String enemyName, int x, int y) {
        this.enemyName = enemyName;
        this.x = x;
        this.y = y;
    }

    public EnemyModel(Enemy enemy) {
        this.enemyName = enemy.getEnemyName();
        this.x = enemy.getX();
        this.y = enemy.getY();
        this.hp = enemy.getHealth();
    }


    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
