package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.utils.Cell;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public abstract class Enemy extends Actor {
    protected String enemyName;
    protected Map<String, int[]> coordinates = new HashMap<>();


    public Enemy(Cell cell) {
        super(cell);
        setCoordinates();
    }

    @Override
    public void move(int dx, int dy) {
        String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
        Random random = new Random();
        String direction = directions[random.nextInt(directions.length)];

        this.nextCell = this.cell.getNeighbor(coordinates.get(direction)[0], coordinates.get(direction)[1]);
        tryToMoveToPlayer();

        if (!isInFightMode) {
            if (isValidMove() && !isPlayerNear()) {
                this.isInFightMode = false;
                setMovement();
            } else if (isPlayerNear()) {
                this.isInFightMode = true;
                fight();
            }
        } else if (isPlayerNear()) {
            fight();
        } else { this.isInFightMode = false; }
    }

    private void setCoordinates() {
        this.coordinates.put("UP", new int[] {0, -1});
        this.coordinates.put("DOWN", new int[] {0, 1});
        this.coordinates.put("LEFT", new int[] {-1, 0});
        this.coordinates.put("RIGHT", new int[] {1, 0});
    }

    private boolean isPlayerNear() {
        boolean isPlayerNear = false;
        for (String direction : coordinates.keySet()) {
            try {
                Cell nextCell = cell.getNeighbor(coordinates.get(direction)[0], coordinates.get(direction)[1]);
                if (nextCell.getActor() != null && nextCell.getActor().getTileName().equals("player")) {
                    isPlayerNear = true;
                    this.nextCell = nextCell;
                    break;
                }
            } catch (Exception ignored) {}

        }
        return isPlayerNear;

    }

    protected boolean isValidMove() {
        return nextCell.getType().isWalkable() && nextCell.getItem() == null  && nextCell.getActor() == null;
    }

    protected void tryToMoveToPlayer() {}

    public String getEnemyName() { return this.enemyName; }

    @Override
    public String getTileName() {
        return this.enemyName;
    }

}
