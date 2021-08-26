package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class GameStateDaoJdbc implements GameStateDao {
    private final DataSource dataSource;
    private final PlayerDao playerDao;
    private final EnemyDao enemyDao;
    private final ItemDao itemDao;
    private final InventoryDao inventoryDao;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
        this.playerDao = new PlayerDaoJdbc(dataSource);
        this.enemyDao = new EnemyDaoJdbc(dataSource);
        this.itemDao = new ItemDaoJdbc(dataSource);
        this.inventoryDao = new InventoryDaoJdbc(dataSource);
    }


    @Override
    public int addStateReturningId(GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (title, current_map, saved_at, player_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, state.getTitle());
            statement.setInt(2, state.getCurrentMap());
            statement.setDate(3, state.getSavedAt());
            statement.setInt(4, state.getPlayer().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            state.setId(resultSet.getInt(1));
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE game_state SET title = ?, current_map = ?, saved_at = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, state.getTitle());
            st.setInt(2, state.getCurrentMap());
            st.setDate(3, state.getSavedAt());
            st.setInt(4, state.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[] getIdsByTitle(String title) {
        try (Connection conn = this.dataSource.getConnection()) {
            String sql = "SELECT id, player_id FROM game_state WHERE title LIKE ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, title);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new int[] {rs.getInt(1), rs.getInt(2)};
        } catch (SQLException e) {
            throw new RuntimeException("Error while game_state with title: " + title, e);
        }

    }

    @Override
    public List<GameState> getAll() {
        try (Connection conn = this.dataSource.getConnection()) {
            String sql = "SELECT * FROM game_state";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<GameState> gameStates = new ArrayList<>();
            while (rs.next()) {
                PlayerModel playerModel = this.playerDao.get(rs.getInt(5));
                GameState state = new GameState(rs.getString(2), rs.getInt(3), rs.getDate(4), playerModel);
                state.setId(rs.getInt(1));

                List<EnemyModel> enemies = this.enemyDao.getAll(state.getId());
                for (EnemyModel enemy : enemies) {
                    state.setEnemies(enemy);
                }

                List<ItemModel> items = this.itemDao.getAllByGameStateId(state.getId());
                for (ItemModel item : items) {
                    state.setItems(item);
                }
                List<InventoryModel> inventory = this.inventoryDao.getAllByGameStateId(state.getId());
                for (InventoryModel inventoryModel : inventory) {
                    state.setInventory(inventoryModel);
                }

                gameStates.add(state);
            }
            return gameStates;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all game states", e);
        }

    }
}
