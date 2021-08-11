package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Guard;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(int level) {
        InputStream is;
        if (level == 2) {
            is = MapLoader.class.getResourceAsStream("/map2.txt");
        } else {
            is = MapLoader.class.getResourceAsStream("/map.txt");
        }
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
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
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            new Guard(cell);
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
                            cell.setType(CellType.SWORD);
                            break;
                        case 'k':
                            cell.setType(CellType.KEY);
                            break;
                        case 'a':
                            cell.setType(CellType.ARMOUR);
                            break;
                        case 'c':
                            cell.setType(CellType.CLOSED_DOOR);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}