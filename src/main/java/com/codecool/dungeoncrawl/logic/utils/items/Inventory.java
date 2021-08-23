package com.codecool.dungeoncrawl.logic.utils.items;


import java.util.HashMap;
import java.util.Map;

public class Inventory {
    public static Map<Item, Integer> inventory = new HashMap<>();
    public static Map<String, Item> allPossibleItems;
    private static boolean isPossibleItems = false;


    private static void createAllPossibleItems() {
        allPossibleItems = new HashMap<>();
        allPossibleItems.put("sword", new Sword("sword", 10));
        allPossibleItems.put("key", new Key("key", 0));
        allPossibleItems.put("armour", new Armour("armour", 100));
        isPossibleItems = true;
    }

    public static int getKeyAmount() {
        return inventory.get(allPossibleItems.get("key"));
    }

    public static void setInventory(String itemName, int amount) {
        if (!isPossibleItems) {
            createAllPossibleItems();
        }
        Item item = allPossibleItems.get(itemName);
        int value = 0;
        try {
            value = inventory.get(item);
        } catch (Exception ignored) {}
        inventory.put(item, value+amount);
    }

    public static String getInventoryToString() {
        StringBuilder sb = new StringBuilder("\n");
        for (Item item : inventory.keySet()) {
            sb.append(item.getName()).append("  ").append(inventory.get(item)).append("\n\n");
        }

        return sb.toString();
    }
}
