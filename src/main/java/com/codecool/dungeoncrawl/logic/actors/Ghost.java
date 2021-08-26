package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.utils.Cell;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Ghost extends Enemy {
    Map<String, int[]> coordinates;

    Cell playerCell;

    public Ghost(Cell cell) {
        super(cell);
        health = 400;
        strength = 20;
        coordinates = new HashMap<>();
        setCoordinates();
        this.enemyName = "ghost";
    }

    @Override
    public void move(int dx, int dy) {
        String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
        Random random = new Random();
        String direction = directions[random.nextInt(directions.length)];

            nextCell = cell.getNeighbor(coordinates.get(direction)[0], coordinates.get(direction)[1]);
            tryToMoveToPlayer();


        if (!isInFightMode) {
            if (nextCell.getItem() == null && nextCell.getActor() == null && !isPlayerNear()) {
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

    private boolean isPlayerNear() {
        boolean isPlayerNear = false;
        for (String direction : coordinates.keySet()) {
            try {
                Cell nextCell = cell.getNeighbor(coordinates.get(direction)[0], coordinates.get(direction)[1]);
                if (nextCell.getActor() != null && nextCell.getActor().getTileName().equals("player")) {
                    isPlayerNear = true;
                    break;
                }
            } catch (Exception ignored) {}

        }
        return isPlayerNear;
    }

    public void tryToMoveToPlayer() {
        for (int x = -5; x <= 5; x++) {
            int j = 0;
            for (int y = -5; y <= 5; y++){
                try {
                    playerCell = cell.getNeighbor(x, y);
                    if (playerCell.getActor().getTileName().equals("player")) {
                        this.nextCell = cell.getNeighbor(Integer.compare(x, 0), Integer.compare(y, 0));
                        j = y;
                        break;
                    }
                } catch (Exception ignored) {}
            }
            if ((this.nextCell == cell.getNeighbor(x == 0 ? 0 : 1, j == 0 ? 0 : 1)) && (x != 0 && j != 0)) {
                break;
            }
        }
    }

//    @Override
//    public String getTileName() {
//        return "ghost";
//    }

}
