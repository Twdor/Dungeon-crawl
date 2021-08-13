package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.utils.Inventory;


public class Player extends Actor {
    public Inventory inventory;
    String[] developersNames = {"cipi", "adi", "cozmin"};
    public String playerName;


    public Player(Cell cell) {
        super(cell);
        setHealth(1000);
        setStrength(100);
        inventory = new Inventory();
    }

    public boolean isPlayerOnItem(Cell cell) {
        return inventory.getInventory().containsKey(cell.getType());
    }

    private boolean isDevName() {
        for (String name : developersNames) {
            if (name.equalsIgnoreCase(playerName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isValidMove() {
        return nextCell.getType() == CellType.FLOOR
                || nextCell.getType() == CellType.OPEN_DOOR
                || isPlayerOnItem(nextCell)
                || nextCell.getType() == CellType.STAIRS
                || isDevName();
    }

    public void fight(int dx, int dy){
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.isEnemy()) {
            nextCell.getActor().setHealth(nextCell.getActor().getHealth()-getStrength());
            setHealth(getHealth()-nextCell.getActor().getStrength());
            if (nextCell.getActor().getHealth() <= 0) {
                nextCell.setType(CellType.FLOOR);
            } else if (getHealth() <= 0) {
                cell.setType(CellType.FLOOR);
                System.out.println("You Lose! Game over!");
                System.exit(0);
            }
        }
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
