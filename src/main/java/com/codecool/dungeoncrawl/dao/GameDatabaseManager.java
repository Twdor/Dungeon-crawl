package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.utils.items.Item;
import com.codecool.dungeoncrawl.model.*;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class GameDatabaseManager {
    private GameStateDao gameStateDao;
    private PlayerDao playerDao;
    private EnemyDao enemyDao;
    private ItemDao itemDao;
    private InventoryDao inventoryDao;

    private PlayerModel playerModel;


    public void setup() throws SQLException {
        DataSource dataSource = connect();

        this.gameStateDao = new GameStateDaoJdbc(dataSource);
        this.playerDao = new PlayerDaoJdbc(dataSource);
        this.enemyDao = new EnemyDaoJdbc(dataSource);
        this.itemDao = new ItemDaoJdbc(dataSource);
        this.inventoryDao = new InventoryDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {
        this.playerModel = new PlayerModel(player);
        this.playerDao.add(this.playerModel);
    }

    public int saveGameState(String description, int level, Date date) {
        GameState gameState = new GameState(description, level, date, this.playerModel);
        return this.gameStateDao.addStateReturningId(gameState);
    }

    public void saveEnemy(Enemy enemy, int gameStateId) {
        EnemyModel enemyModel = new EnemyModel(enemy);
        this.enemyDao.add(enemyModel, gameStateId);
    }

    public void deleteItemByGameStateId(int gameStateId) {
        this.itemDao.deleteByGameStateId(gameStateId);
    }

    public void deleteEnemyByGameStateId(int gameStateId) {
        this.enemyDao.deleteByGameStateId(gameStateId);
    }

    public void deleteInventoryByGameStateId(int gameStateId) {
        this.inventoryDao.deleteByGameStateId(gameStateId);
    }

    public void saveItem(Item item, int gameStateId) {
        ItemModel itemModel = new ItemModel(item);
        this.itemDao.add(itemModel, gameStateId);
    }

    public void saveInventory(String itemName, int amount, int gameStateId) {
        InventoryModel inventory = new InventoryModel(itemName, amount);
        this.inventoryDao.add(inventory, gameStateId);
    }

    public int[] getIdsByTitle(String title) {
        return this.gameStateDao.getIdsByTitle(title);
    }

    public List<GameState> getLoadGames() {
        return this.gameStateDao.getAll();
    }

    public void updateGameState(GameState gameState) { this.gameStateDao.update(gameState); }

    public void updatePlayer(PlayerModel player) {
        this.playerDao.update(player);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        ApplicationProperties properties = new ApplicationProperties();

        String dbName = properties.readProperty("DATABASE");
        String user = properties.readProperty("USERNAME");
        String password = properties.readProperty("PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
