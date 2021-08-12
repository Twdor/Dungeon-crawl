package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.utils.Inventory;


public class Player extends Actor {
    public Inventory inventory;


    public Player(Cell cell) {
        super(cell);
        setHealth(15);
        setStrength(50);
        inventory = new Inventory();
    }

    public boolean isPlayerOnItem(Cell cell) {
        return inventory.getInventory().containsKey(cell.getType());
    }

    @Override
    protected boolean isValidMove() {
        return nextCell.getType() == CellType.FLOOR
                || nextCell.getType() == CellType.OPEN_DOOR
                || isPlayerOnItem(nextCell)
                || nextCell.getType() == CellType.STAIRS;
    }

    public void tryToOpenDoor() {
        nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().equals(CellType.CLOSED_DOOR) && playerHasKey()) {
            nextCell.setType(CellType.OPEN_DOOR);
            inventory.updateInventory(CellType.KEY, -1);
        }
    }
    
    private boolean playerHasKey() {
        return inventory.getInventory().get(CellType.KEY) > 0;
    }

    public String getTileName() {
        return "player";
    }
}
