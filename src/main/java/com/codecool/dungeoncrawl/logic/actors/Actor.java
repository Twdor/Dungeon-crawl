package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.utils.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    protected Cell cell;
    protected int health;
    protected int strength;
    protected Cell nextCell;
    public boolean isInFightMode = false;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public abstract void move(int dx, int dy);

    public void setHealth(int value) { health += value; }

    public void setStrength(int value) { strength += value; }

    public int getHealth() { return health; }

    public int getStrength() { return  strength; }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    protected void setMovement() {
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    protected void fight() {
        if (nextCell.getActor().isDead()) {
            nextCell.getActor().die();
            isInFightMode = false;
            return;
        } else if (cell.getActor().isDead()) {
            cell.getActor().die();
            isInFightMode = false;
            return;
        }
        nextCell.getActor().setHealth(-getStrength());
        setHealth(-nextCell.getActor().getStrength());
    }

    protected boolean isEnemy() { return nextCell.getActor() != null; }

    public boolean isDead() { return this.health < 1; }

    protected void die() {
        if (cell.getActor() instanceof Enemy) cell.removeEnemy(this);
        cell.setActor(null);
    }
}
