package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.utils.Cell;
import com.codecool.dungeoncrawl.logic.utils.CellType;
import com.codecool.dungeoncrawl.logic.utils.DevNames;
import com.codecool.dungeoncrawl.logic.utils.items.Inventory;


public class Player extends Actor {
    private String playerName;


    public Player(Cell cell) {
        super(cell);
        health = 1000;
        strength = 100;
    }

    public Player(Cell cell, int health, int strength, String name) {
        super(cell);
        this.health = health;
        this.strength = strength;
        playerName = name;
    }

    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public String getPlayerName() { return playerName; }

    @Override
    public void move(int dx, int dy) {
        nextCell = cell.getNeighbor(dx, dy);
        tryToOpenDoor();

        if (!isEnemy() && (isDevName() || isValidMove()))  setMovement();
        else if (isEnemy()) fight();
    }

    public boolean isPlayerOnItem() {
        return cell.getItem() != null;
    }

    private boolean isDevName() {
        for (DevNames value : DevNames.values()) {
            if (value.toString().equals(playerName.toUpperCase())) return true;
        }
        return false;
    }

    private boolean isValidMove() {
        return nextCell.getType() == CellType.FLOOR
                || nextCell.getType() == CellType.OPEN_DOOR
                || isPlayerOnItem()
                || nextCell.getType() == CellType.STAIRS;
    }


    public void tryToOpenDoor() {
        if (nextCell.getType().equals(CellType.CLOSED_DOOR) && playerHasKey()) {
            nextCell.setType(CellType.OPEN_DOOR);
            Inventory.setInventory("key", -1);
        }
    }

    public boolean isPlayerOnStairs() {
        return cell.getType().equals(CellType.STAIRS);
    }

    private boolean playerHasKey() {
        return Inventory.getKeyAmount() > 0;
    }

    public String getTileName() {
        return "player";
    }
}
