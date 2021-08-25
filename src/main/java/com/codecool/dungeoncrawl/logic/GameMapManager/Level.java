package com.codecool.dungeoncrawl.logic.GameMapManager;

import java.util.HashMap;
import java.util.Map;

public class Level {
    public static Map<Integer, String> levels = new HashMap<>();

    private Level() {}

    public static void setLevels() {
        if (levels.size() == 0) {
            levels.put(1, "/map.txt");
            levels.put(2, "/map2.txt");
        }
    }

}
