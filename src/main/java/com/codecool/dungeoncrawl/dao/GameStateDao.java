package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;

import java.util.List;

public interface GameStateDao {
    int addStateReturningId(GameState state);
    void update(GameState state);
    int[] getIdsByTitle(String title);
    List<GameState> getAll();
}
