package com.codecool.dungeoncrawl.model;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


public class GameState extends BaseModel {
    private final Date savedAt;
    private final String title;
    private final int currentMap;
    private final List<EnemyModel> enemies = new ArrayList<>();
    private final List<ItemModel> items = new ArrayList<>();
    private final List<InventoryModel> inventory = new ArrayList<>();
    private PlayerModel player;


    public GameState(String title, int currentMap, Date savedAt, PlayerModel player) {
        this.title = title;
        this.currentMap = currentMap;
        this.savedAt = savedAt;
        this.player = player;
    }

    public GameState(String title, int currentMap, Date savedAt) {
        this.title = title;
        this.currentMap = currentMap;
        this.savedAt = savedAt;
    }

    public String getTitle() { return this.title; }

    public Date getSavedAt() { return this.savedAt; }

    public int getCurrentMap() {
        return this.currentMap;
    }

    public void setEnemies(EnemyModel enemy) {
        this.enemies.add(enemy);
    }

    public List<EnemyModel> getEnemies () { return this.enemies; }

    public PlayerModel getPlayer() {
        return this.player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    public void setItems(ItemModel item) { this.items.add(item); }

    public List<ItemModel> getItems() { return this.items; }

    public void setInventory(InventoryModel inventory) { this.inventory.add(inventory); }

    public List<InventoryModel> getInventory() { return this.inventory; }
}
