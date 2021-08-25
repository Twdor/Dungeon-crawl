package com.codecool.dungeoncrawl.logic.utils.items;

import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.utils.Cell;


public abstract class Item implements Drawable {
    protected Cell cell;
    protected String name;

    public Item(Cell cell, String name) {
        this.name = name;

        if (!Inventory.inventory.containsKey(this.name))
            Inventory.inventory.put(this.name, 0);

        this.cell = cell;
        this.cell.setItem(this);
    }

    public abstract void managePowersForPlayer(Player player);

    public String getName() { return name; }

}
