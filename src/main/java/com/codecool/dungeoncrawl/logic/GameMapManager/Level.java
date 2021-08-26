package com.codecool.dungeoncrawl.logic.GameMapManager;

import java.util.HashMap;
import java.util.Map;

public class Level {
    public static Map<Integer, String> levels = new HashMap<>();

    private Level() {}

    public static void setLevels() {
        if (levels.size() == 0) {
            levels.put(1, "/initialMap.txt");
            levels.put(2, "/map.txt");
            levels.put(3, "/map2.txt");
            levels.put(4, "/map3.txt");
        }
    }

}
