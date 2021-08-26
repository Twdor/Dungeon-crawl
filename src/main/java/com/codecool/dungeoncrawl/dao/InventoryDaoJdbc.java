package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.InventoryModel;
import com.codecool.dungeoncrawl.model.ItemModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDaoJdbc implements InventoryDao{
    private final DataSource dataSource;

    public InventoryDaoJdbc(DataSource dataSource) { this.dataSource = dataSource; }

    @Override
    public void add(InventoryModel inventory, int game_state_id) {
        try (Connection conn = this.dataSource.getConnection()) {
            String sql = "INSERT INTO inventory (item_name, amount, game_state_id) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, inventory.getItemName());
            statement.setInt(2, inventory.getAmount());
            statement.setInt(3, game_state_id);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            inventory.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByGameStateId(int game_state_id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM inventory WHERE game_state_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, game_state_id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<InventoryModel> getAllByGameStateId(int game_state_id) {
        try (Connection conn = this.dataSource.getConnection()) {
            String sql = "SELECT item_name, amount FROM inventory WHERE game_state_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, game_state_id);
            ResultSet rs = st.executeQuery();
            List<InventoryModel> inventoryModels = new ArrayList<>();
            while (rs.next()) {
                InventoryModel inventoryModel = new InventoryModel(rs.getString(1), rs.getInt(2));
                inventoryModels.add(inventoryModel);
            }
            return inventoryModels;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all inventory", e);
        }
    }
}
