package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ItemModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ItemDaoJdbc implements ItemDao{
    private final DataSource dataSource;

    public ItemDaoJdbc(DataSource dataSource) { this.dataSource = dataSource; }

    @Override
    public void add(ItemModel item, int game_state_id) {
        try (Connection conn = this.dataSource.getConnection()) {
            String sql = "INSERT INTO item (item_name, x, y, game_state_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getItemName());
            statement.setInt(2, item.getX());
            statement.setInt(3, item.getY());
            statement.setInt(4, game_state_id);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            item.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByGameStateId(int game_state_id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM item WHERE game_state_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, game_state_id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ItemModel> getAllByGameStateId(int game_state_id) {
        try (Connection conn = this.dataSource.getConnection()) {
            String sql = "SELECT item_name, x, y FROM item WHERE game_state_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, game_state_id);
            ResultSet rs = st.executeQuery();
            List<ItemModel> itemModels = new ArrayList<>();
            while (rs.next()) {
                ItemModel itemModel = new ItemModel(rs.getString(1), rs.getInt(2), rs.getInt(3));
                itemModels.add(itemModel);
            }
            return itemModels;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all items", e);
        }
    }
}
