package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.utils.Cell;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Skeleton extends Enemy {
    Map<String, int[]> coordinates;

    public Skeleton(Cell cell) {
        super(cell);
        health = 100;
        strength = 10;
        coordinates = new HashMap<>();
        setCoordinates();
    }

    @Override
    public String getTileName() { return "skeleton"; }

    @Override
    public void move(int dx, int dy) {
        String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
        Random random = new Random();
        String direction = directions[random.nextInt(directions.length)];

        nextCell = cell.getNeighbor(coordinates.get(direction)[0], coordinates.get(direction)[1]);

        if (!isInFightMode) {
            if (nextCell.getType().isWalkable() && nextCell.getItem() == null  && nextCell.getActor() == null && !isPlayerNear()) {
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
        coordinates.put("UP", new int[] {0, -1});
        coordinates.put("DOWN", new int[] {0, 1});
        coordinates.put("LEFT", new int[] {-1, 0});
        coordinates.put("RIGHT", new int[] {1, 0});
    }

    private int[] getCoordinates(String direction) {
        switch (direction) {
            case "UP":
                return new int[] {0, -1};
            case "DOWN":
                return new int[] {0, 1};
            case "LEFT":
                return new int[] {-1, 0};
            case "RIGHT":
                return new int[] {1, 0};
        }
        return null;
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
}
