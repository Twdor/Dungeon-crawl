package com.codecool.dungeoncrawl.logic.utils.items;


import java.util.HashMap;
import java.util.Map;


public class Inventory {
    public static Map<String, Integer> inventory = new HashMap<>();


    private Inventory() {}

    public static String getInventoryToString() {
        StringBuilder sb = new StringBuilder("\n");
        for (String item : inventory.keySet()) {
            sb.append(item).append("  ").append(inventory.get(item)).append("\n\n");
        }

        return sb.toString();
    }
}
