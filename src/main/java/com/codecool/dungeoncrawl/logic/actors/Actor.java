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
            if (cell.getType() == CellType.ENEMY) {
                cell.setType(CellType.FLOOR);
                nextCell.setType(CellType.ENEMY);
            }
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    protected boolean isValidMove() {
        return nextCell.getType() == CellType.FLOOR && !nextCell.getType().getTileName().equals("player");
    }

    public void setHealth(int value) { health = value; }

    public void setStrength(int value) { strength = value; }

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
