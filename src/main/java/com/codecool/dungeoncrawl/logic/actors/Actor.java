package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    protected Cell cell;
    private int health;
    private int strength;
    protected Cell nextCell;
    protected int dx;
    protected int dy;


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
        nextCell = cell.getNeighbor(dx, dy);
        if (isValidMove()) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    protected boolean isValidMove() {
        return nextCell.getType() == CellType.FLOOR;
    }

    public void fight(int dx, int dy){
        Cell nextCell = cell.getNeighbor(dx, dy);
        // enemy attack
        if (nextCell.isPlayer() && cell.isEnemy()) {
            attack(nextCell.getActor(), nextCell);
        }
        // player attack
        if (nextCell.isEnemy() && cell.isPlayer()) {
            attack(nextCell.getActor(), nextCell);
        }
    }

    public void attack(Actor actor, Cell cell) {
        while (true) {
            actor.setHealth(actor.getHealth() - this.strength);
            if (actor.getHealth() < 0 || this.health < 0) {
                break;
            }
            this.health = this.health - actor.getStrength();
            if (actor.getHealth() < 0 || this.health < 0) {
                break;
            }
        }
        if (actor.getHealth() <= 0)  {
            cell.setActor(null);
        } else if (this.health <= 0 ){
            cell.setActor(null);
        }
    }

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
}
