package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ItemModel;

import java.util.List;

public interface ItemDao {
    void add(ItemModel itemModel, int game_state_id);
    void deleteByGameStateId(int game_state_id);
    List<ItemModel> getAllByGameStateId(int game_state_id);
}
