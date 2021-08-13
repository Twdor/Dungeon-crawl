package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("enemy skeleton", new Tile(29, 6));
        tileMap.put("sword", new Tile(0, 30));
        tileMap.put("armour", new Tile(6, 23));
        tileMap.put("key", new Tile(16, 23));
        tileMap.put("closed door", new Tile(3, 4));
        tileMap.put("open door", new Tile(4, 4));
        tileMap.put("enemy guard", new Tile(31, 0));
        tileMap.put("tree", new Tile(4, 2));
        tileMap.put("window", new Tile(12, 17));
        tileMap.put("fence", new Tile(2, 3));
        tileMap.put("gate", new Tile(5, 4));
        tileMap.put("path", new Tile(19, 1));
        tileMap.put("entrance", new Tile(3, 3));
        tileMap.put("grass", new Tile(5, 0));
        tileMap.put("stairs", new Tile(3, 6));
        tileMap.put("flag", new Tile(17, 8));
        tileMap.put("bars", new Tile(5, 3));
        tileMap.put("barrel", new Tile(14, 14));
        tileMap.put("chest", new Tile(8, 6));
        tileMap.put("light", new Tile(4, 15));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
