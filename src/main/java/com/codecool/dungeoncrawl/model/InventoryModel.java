package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.utils.items.Inventory;
import com.codecool.dungeoncrawl.logic.utils.items.Item;

public class InventoryModel extends BaseModel{

    private String itemName;
    private int amount;

    public InventoryModel(String itemName, int amount) {
        this.itemName = itemName;
        this.amount = amount;
    }

//    public InventoryModel(Inventory inventory) {
//        this.itemName = item.getName();
//        this.amount = inventory.;
//    }


    public String getItemName() {
        return this.itemName;
    }

    public int getAmount() { return  this.amount; }
}
