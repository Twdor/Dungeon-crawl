package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.utils.Cell;
import com.codecool.dungeoncrawl.logic.utils.CellType;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;
    private List<Actor> enemies = new ArrayList<>();
    private int level;


    private Player player;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public void setLevel(int level) { this.level = level; }

    public int getLevel() { return level; }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() { return player; }

    public List<Actor> getEnemies() { return enemies; }

    public void setEnemies(Enemy enemy) { enemies.add(enemy); }

    public void removeEnemy(Enemy enemy) { enemies.remove(enemy); }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
