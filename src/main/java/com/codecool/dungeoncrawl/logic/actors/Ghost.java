package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.utils.Cell;

public class Ghost extends Enemy {


    public Ghost(Cell cell) {
        super(cell);
        this.health = 400;
        this.strength = 20;
        this.enemyName = "ghost";
    }

    @Override
    public void tryToMoveToPlayer() {
        for (int x = -5; x <= 5; x++) {
            int j = 0;
            for (int y = -5; y <= 5; y++){
                try {
                    Cell playerCell = cell.getNeighbor(x, y);
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

    @Override
    protected boolean isValidMove() { return true; }

}
