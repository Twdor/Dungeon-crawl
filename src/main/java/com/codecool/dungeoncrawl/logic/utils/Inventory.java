package com.codecool.dungeoncrawl.logic.utils;

import com.codecool.dungeoncrawl.logic.CellType;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    static Map<CellType, Integer> inventory;


    public Inventory() {
        inventory = new HashMap<>();
        inventory.put(CellType.ARMOUR, 0);
        inventory.put(CellType.SWORD, 0);
        inventory.put(CellType.KEY, 0);
    }

    public void updateInventory(CellType item, int value) {
        inventory.put(item, inventory.get(item)+value);
    }


    public Map<CellType, Integer> getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        for (CellType item : inventory.keySet()) {
            sb.append(item.getTileName()).append("  ").append(inventory.get(item)).append("\n\n");
        }

        return sb.toString();
    }
}
