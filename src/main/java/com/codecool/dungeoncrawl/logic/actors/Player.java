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

    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public String getPlayerName() { return this.playerName; }

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
                || nextCell.getType() == CellType.STAIRS
                || nextCell.getType() == CellType.GRASS
                || nextCell.getType() == CellType.PATH
                || nextCell.getType() == CellType.ENTRANCE
                || nextCell.getType() == CellType.WOMAN;
    }


    public void tryToOpenDoor() {
        if (nextCell.getType().equals(CellType.CLOSED_DOOR) && playerHasKey()) {
            nextCell.setType(CellType.OPEN_DOOR);
            Inventory.inventory.put("key", 0);
        }
    }

    public boolean isPlayerOnStairs() {
        return cell.getType().equals(CellType.STAIRS) || cell.getType().equals(CellType.ENTRANCE);
    }

    private boolean playerHasKey() {
        return Inventory.inventory.get("key") > 0;
    }

    public String getTileName() {
        return "player";
    }
}
