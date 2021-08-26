package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.EnemyModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnemyDaoJdbc implements EnemyDao{
    private final DataSource dataSource;

    public EnemyDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(EnemyModel enemy, int game_state_id) {
        try (Connection conn = this.dataSource.getConnection()) {
            String sql = "INSERT INTO enemy (enemy_name, hp, x, y, game_state_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, enemy.getEnemyName());
            statement.setInt(2, enemy.getHp());
            statement.setInt(3, enemy.getX());
            statement.setInt(4, enemy.getY());
            statement.setInt(5, game_state_id);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            enemy.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByGameStateId(int game_state_id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM enemy WHERE game_state_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, game_state_id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<EnemyModel> getAll(int game_state_id) {
        try (Connection conn = this.dataSource.getConnection()) {
            String sql = "SELECT enemy_name, hp, x, y FROM enemy WHERE game_state_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, game_state_id);
            ResultSet rs = st.executeQuery();
            List<EnemyModel> enemyModels = new ArrayList<>();
            while (rs.next()) {
                EnemyModel enemyModel = new EnemyModel(rs.getString(1), rs.getInt(3), rs.getInt(4));
                enemyModel.setHp(rs.getInt(2));
                enemyModels.add(enemyModel);
            }
            return enemyModels;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all enemies", e);
        }
    }
}
