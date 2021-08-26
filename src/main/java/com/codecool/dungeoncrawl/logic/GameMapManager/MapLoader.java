package com.codecool.dungeoncrawl.logic.GameMapManager;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.utils.*;
import com.codecool.dungeoncrawl.logic.utils.items.*;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.InventoryModel;
import com.codecool.dungeoncrawl.model.ItemModel;

import java.io.InputStream;
import java.util.Scanner;


public class MapLoader {

    public static GameMap loadMap(int level, GameState gameState) {
        InputStream is = MapLoader.class.getResourceAsStream(Level.levels.get(level));


        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);

        if (gameState != null) {
            for (InventoryModel item : gameState.getInventory()) {
                Inventory.inventory.put(item.getItemName(), item.getAmount());
            }
            manageSavedEnemies(gameState, map);
            manageSavedItems(gameState, map);
        }
        Inventory.inventory.putIfAbsent("key", 0);

        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                        case '█':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            if (gameState == null)
                                map.setEnemies(new Skeleton(cell));
                            break;
                        case '$':
                            cell.setType(CellType.FLOOR);
                            if (gameState == null)
                                map.setEnemies(new Spider(cell));
                            break;
                        case '!':
                            cell.setType(CellType.FLOOR);
                            if (gameState == null)
                                map.setEnemies(new Ghost(cell));
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            if (gameState == null)
                                map.setEnemies(new Guard(cell));
                            break;
                        case 't':
                            cell.setType(CellType.TREE);
                            break;
                        case 'w':
                            cell.setType(CellType.WINDOW);
                            break;
                        case 'b':
                            cell.setType(CellType.GATE);
                            break;
                        case 'f':
                            cell.setType(CellType.FENCE);
                            break;
                        case 'p':
                            cell.setType(CellType.PATH);
                            break;
                        case 'e':
                            cell.setType(CellType.ENTRANCE);
                            break;
                        case 'd':
                            cell.setType(CellType.STAIRS);
                            break;
                        case '%':
                            cell.setType(CellType.GRASS);
                            break;
                        case 'S':
                            cell.setType(CellType.FLOOR);
                            if (gameState == null) {
                                map.setItems(new Sword(cell));
                            }
                            Inventory.inventory.putIfAbsent("sword", 0);
                            break;
                        case 'A':
                            cell.setType(CellType.FLOOR);
                            if (gameState == null) {
                                map.setItems(new Axe(cell));
                            }
                            Inventory.inventory.putIfAbsent("axe", 0);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            if (gameState == null) {
                                map.setItems(new Key(cell));
                            }
                            Inventory.inventory.putIfAbsent("key", 0);
                            break;
                        case 'a':
                            cell.setType(CellType.FLOOR);
                            if (gameState == null) {
                                map.setItems(new Armour(cell));
                            }
                            Inventory.inventory.putIfAbsent("armour", 0);
                            break;
                        case 'h':
                            cell.setType(CellType.FLOOR);
                            if (gameState == null) {
                                map.setItems(new Helmet(cell));
                            }
                            Inventory.inventory.putIfAbsent("helmet", 0);
                            break;
                        case 'c':
                            cell.setType(CellType.CLOSED_DOOR);
                            break;
                        case '*':
                            cell.setType(CellType.BARS);
                            break;
                        case 'B':
                            cell.setType(CellType.BARREL);
                            break;
                        case 'C':
                            cell.setType(CellType.CHEST);
                            break;
                        case 'l':
                            cell.setType(CellType.LIGHT);
                            break;
                        case 'F':
                            cell.setType(CellType.FIRE);
                            break;
                        case 'T':
                            cell.setType(CellType.TENT);
                            break;
                        case 'W':
                            cell.setType(CellType.WEB);
                            break;
                        case '^':
                            cell.setType(CellType.WOMAN);
                            break;
                        case '@':
                            cell.setType(level != 1 ? CellType.FLOOR : CellType.PATH);
                            if (gameState == null) {
                                map.setPlayer(new Player(cell));
                            } else {
                                cell = map.getCell(gameState.getPlayer().getX(), gameState.getPlayer().getY());
                                cell.setType(CellType.FLOOR);
                                map.setPlayer(new Player(cell));
                                map.getPlayer().setHealth(-map.getPlayer().getHealth()+gameState.getPlayer().getHp());
                            }
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

    private static void manageSavedState(String name, GameMap map, Cell cell) {
        switch (name) {
            case "spider":
                map.setEnemies(new Spider(cell));
            case "ghost":
                map.setEnemies(new Ghost(cell));
            case "guard":
                map.setEnemies(new Guard(cell));
                break;
            case "skeleton":
                map.setEnemies(new Skeleton(cell));
                break;
            case "key":
                map.setItems(new Key(cell));
                break;
            case "sword":
                map.setItems(new Sword(cell));
                break;
            case "axe":
                map.setItems(new Axe(cell));
                break;
            case "helmet":
                map.setItems(new Helmet(cell));
                break;
            case "armour":
                map.setItems(new Armour(cell));
        }
    }

    private static void manageSavedEnemies(GameState gameState, GameMap map) {
        for (EnemyModel enemy : gameState.getEnemies()) {
            Cell cell = map.getCell(enemy.getX(), enemy.getY());
            cell.setType(CellType.FLOOR);
            manageSavedState(enemy.getEnemyName(), map, cell);
        }
    }

    private static void manageSavedItems(GameState gameState, GameMap map) {
        for (ItemModel item : gameState.getItems()) {
            Cell cell = map.getCell(item.getX(), item.getY());
            cell.setType(CellType.FLOOR);
            manageSavedState(item.getItemName(), map, cell);
        }
    }

}
