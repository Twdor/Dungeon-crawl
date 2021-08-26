package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.EnemyModel;

import java.util.List;

public interface EnemyDao {
    void add(EnemyModel enemy, int game_state_id);
    void deleteByGameStateId(int game_state_id);
    List<EnemyModel> getAll(int game_state_id);
}
