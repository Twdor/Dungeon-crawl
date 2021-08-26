package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.InventoryModel;

import java.util.List;

public interface InventoryDao {
    void add(InventoryModel inventoryModel, int game_state_id);
    void deleteByGameStateId(int game_state_id);
    List<InventoryModel> getAllByGameStateId(int game_state_id);
}
