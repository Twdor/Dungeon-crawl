package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.GameMapManager.Level;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.utils.Cell;
import com.codecool.dungeoncrawl.logic.utils.items.Inventory;
import com.codecool.dungeoncrawl.logic.utils.items.Item;
import com.codecool.dungeoncrawl.menus.GameOverMenu;
import com.codecool.dungeoncrawl.menus.MainMenu;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import com.codecool.dungeoncrawl.logic.GameMapManager.GameMap;
import com.codecool.dungeoncrawl.logic.GameMapManager.MapLoader;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.util.Date;
import java.sql.SQLException;


public class Main extends Application {
    int currentLevel = 1;
    GameMap map;
    Canvas canvas = new Canvas(
            35 * Tiles.TILE_WIDTH,
            28 * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(500),
                    event -> refresh()));
    double contextScale = 1.4;
    int minX, minY, maxX, maxY;
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();
    Label strengthLabel = new Label();
    Button pickUpItem = new Button("pickUp");

    Button sword = new Button();
    Button armour = new Button();
    Button key = new Button();

    boolean isKeyPressed = false;
    boolean isAtLeastOneEnemyAlive = true;
    Stage stage;
    GameOverMenu gameOverMenu;
    Scene scene;
    BorderPane borderPane;
    public GameDatabaseManager dbManager;
    MainMenu menu;
    String title;
    String testTitle = "testare";


    public int getCurrentLevel() { return this.currentLevel; }

    public void setCurrentLevel(int currentLevel) { this.currentLevel = currentLevel; }

    public static void main(String[] args) {
        launch(args);
    }

    public GameMap getMap() { return this.map; }

    public Canvas getCanvas() { return this.canvas; }

    public Stage getStage() { return this.stage; }

    public GameDatabaseManager getDbManager() { return this.dbManager; }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        Level.setLevels();
        dbManager = new GameDatabaseManager();
        dbManager.setup();

        context.scale(contextScale, contextScale);
        this.stage = primaryStage;

        menu = new MainMenu(this);
        menu.handleMenu();
    }


    public void createScene(String characterName, GameState gameState) {
        this.map = MapLoader.loadMap(currentLevel, gameState);
        map.getPlayer().setPlayerName(characterName);

        GridPane ui = new GridPane();
        GridPane inventoryUi = new GridPane();

        inventoryUi.setPrefWidth(200);
        inventoryUi.setPadding(new Insets(10));

        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        Text text = new Text("Inventory: ");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        text.setFill(Color.CORAL);

        inventoryUi.add(text, 0, 0);
        inventoryUi.add(sword, 0, 3);
        inventoryUi.add(armour, 0, 4);
        inventoryUi.add(key, 0, 5);
        inventoryUi.add(pickUpItem, 0, 10);

        inventoryUi.setVgap(7);


        ui.add(new Label(characterName), 0, 3, 1, 1);

        ui.add(new Label("Character name: "), 0, 0); //

        ui.add(new Label(""), 0, 3); //empty space

        ui.add(new Label("\nHealth: "), 0, 4);
        ui.add(healthLabel, 1, 4);
        ui.add(new Label("Strength: "), 0, 5);
        ui.add(strengthLabel, 1, 5);

        pickUpItem.setFocusTraversable(false);
        pickUpItem.setOnAction(pickUpItemEvent);
        pickUpItem.setVisible(false);

        sword.setFocusTraversable(false);
        sword.setOnAction(useItemEvent);
        sword.setUnderline(true);
        sword.setDisable(Inventory.inventory.get("sword") == 0);

        armour.setFocusTraversable(false);
        armour.setOnAction(useItemEvent);
        armour.setUnderline(true);
        armour.setDisable(Inventory.inventory.get("armour") == 0);

        key.setFocusTraversable(false);
        key.setOnAction(useItemEvent);
        key.setUnderline(true);
        key.setDisable(true);

        borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
        borderPane.setLeft(inventoryUi);

        scene = new Scene(borderPane);
    }

    public void run() {
        gameOverMenu = new GameOverMenu(this);

        stage.setScene(scene);
        enemyRefresh();
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);


        stage.setTitle("Dungeon Crawl");
        stage.show();
    }

    private void save(){
        VBox savePauseRoot = new VBox(5);
        savePauseRoot.getChildren().add(new Label("SAVE GAME"));
        savePauseRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
        savePauseRoot.setAlignment(Pos.CENTER);
        savePauseRoot.setPadding(new Insets(350));

        TextField textField = new TextField();
        textField.setStyle("-fx-max-width: 150");
        textField.setPromptText("Title");
        Button save = new Button("Save");
        Button cancel = new Button("Cancel");
        savePauseRoot.getChildren().addAll(textField, save, cancel);

        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initOwner(stage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(savePauseRoot, Color.TRANSPARENT));

        stopEnemiesRefresh();

        cancel.setOnAction(event -> {
            borderPane.setEffect(null);
            popupStage.hide();
            enemyRefresh();
        });

        save.setOnAction(event -> {
            if (textField.getText().length() > 0) {
                int[] ids = dbManager.getIdsByTitle(textField.getText());
                if (ids != null) {
                    Text warningText = new Text();
                    warningText.setText("This title already exists, do you want to override the save or new save?");
                    Button override = new Button("Override");
                    Button newSave = new Button("New Save");
                    savePauseRoot.getChildren().remove(save);
                    savePauseRoot.getChildren().addAll(warningText, override, newSave);
                    override.setOnAction(e -> {
                        borderPane.setEffect(null);
                        popupStage.hide();
                        overrideSavedGame(textField.getText(), ids);
                        enemyRefresh();
                    });
                    newSave.setOnAction(e -> {
                        borderPane.setEffect(null);
                        popupStage.hide();
                        saveNewGame(textField.getText());
                        enemyRefresh();
                    });
                } else {
                    borderPane.setEffect(null);
                    popupStage.hide();
                    saveNewGame(textField.getText());
                    enemyRefresh();
                }
            }
        });

        popupStage.show();
    }

    private void pauseGame(){
        this.borderPane.setEffect(new GaussianBlur());

        VBox pauseRoot = new VBox(5);
        pauseRoot.getChildren().add(new Label("Paused"));
        pauseRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
        pauseRoot.setAlignment(Pos.CENTER);
        pauseRoot.setPadding(new Insets(20));

        Button saveGame = new Button("Save");
        Button resume = new Button("Resume");
        Button exitButton = new Button("Exit");
        pauseRoot.getChildren().addAll(resume, saveGame, exitButton);

        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initOwner(stage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));

        stopEnemiesRefresh();

        resume.setOnAction(event -> {
            borderPane.setEffect(null);
            popupStage.hide();
            enemyRefresh();
        });

        exitButton.setOnAction(event -> stage.close());

        saveGame.setOnAction(event -> {
            borderPane.setEffect(null);
            popupStage.hide();
            save();
        });

        popupStage.show();

    }

    public void enemyRefresh() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void stopEnemiesRefresh() {
        timeline.stop();
    }

    private void moveEnemies() {
        if (map.getEnemies().size() == 0) {
            stopEnemiesRefresh();
            isAtLeastOneEnemyAlive = false;
            return;
        }
        try {
            for (Actor enemy : map.getEnemies()) {
                enemy.move(0, 0);
            }
        } catch (Exception ignored) {
        }

    }

    EventHandler<ActionEvent> pickUpItemEvent = actionEvent -> {
        Cell cell = map.getPlayer().getCell();
        String item = cell.getItem().getName();

        Inventory.inventory.put(item,Inventory.inventory.get(item)+1);

        if (item.equals("sword"))
            sword.setDisable(Inventory.inventory.get(item) < 1);
        else if (item.equals("armour"))
            armour.setDisable(Inventory.inventory.get(item) < 1);


        map.removeItem(cell.getItem());
        cell.deleteItem();

        refresh();
        pickUpItem.setVisible(false);
    };

    EventHandler<ActionEvent> useItemEvent = actionEvent -> {
        String s = actionEvent.getTarget().toString();
        if (s.contains("sword")){
            map.getPlayer().setStrength(30);
            Inventory.inventory.put("sword", Inventory.inventory.get("sword")-1);
            sword.setDisable(Inventory.inventory.get("sword") < 1);
        } else if (s.contains("armour")) {
            map.getPlayer().setHealth(100);
            Inventory.inventory.put("armour", Inventory.inventory.get("armour")-1);
            armour.setDisable(Inventory.inventory.get("armour") < 1);
        }

        refresh();
    };

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                if (map.getPlayer().getY() != 0)
                    map.getPlayer().move(0, -1);
                isKeyPressed = true;
                refresh();
                break;
            case DOWN:
                if (map.getPlayer().getY() != map.getHeight() - 2)
                    map.getPlayer().move(0, 1);
                isKeyPressed = true;
                refresh();
                break;
            case LEFT:
                if (map.getPlayer().getX() != 0)
                    map.getPlayer().move(-1, 0);
                isKeyPressed = true;
                refresh();
                break;
            case RIGHT:
                if (map.getPlayer().getX() != map.getWidth() - 2)
                    map.getPlayer().move(1, 0);
                isKeyPressed = true;
                refresh();
                break;
            case S:
                pauseGame();
                break;
        }
    }

    private void setBounds() {
        minX = (int) (map.getPlayer().getX() - ((map.getWidth() / contextScale - 1) / 2));
        minY = (int) (map.getPlayer().getY() - ((map.getHeight() / contextScale - 1) / 2));
        maxX = (int) (map.getPlayer().getX() + ((map.getWidth() / contextScale + 1) / 2));
        maxY = (int) (map.getPlayer().getY() + ((map.getHeight() / contextScale + 2) / 2));

        if (minX < 0) {
            maxX -= minX;
            minX = 0;
        }
        if (maxX > map.getWidth() - 1) {
            maxX = map.getWidth() - 1;
            minX = (int) (map.getWidth() - 1 - map.getWidth() / contextScale);
        }
        if (minY <= 0) {
            maxY -= minY - 1;
            minY = 0;
        }
        if (maxY > map.getHeight() - 1) {
            maxY = map.getHeight() - 1;
            minY = (int) (map.getHeight() - 1 - map.getHeight() / contextScale);
        }
    }

    private void checkForGameOver() {
        if (map.getPlayer().isDead()) {
            gameOverMenu.handleMenu();
        }
    }

    private void checkForNextLevel() {
        if (map.getPlayer().isPlayerOnStairs()) {
            Player player = map.getPlayer();
            this.currentLevel += 1;
            map = MapLoader.loadMap(this.currentLevel, null);
            map.getPlayer().setPlayerName(player.getPlayerName());
            map.getPlayer().setHealth(-map.getPlayer().getHealth()+player.getHealth());
            map.getPlayer().setStrength(-map.getPlayer().getStrength()+player.getStrength());
            enemyRefresh();
            isAtLeastOneEnemyAlive = true;
        }
    }

    private void refresh() {
        checkForGameOver();
        checkForNextLevel();

        if (!isKeyPressed && isAtLeastOneEnemyAlive)
            moveEnemies();

        pickUpItem.setVisible(map.getPlayer().isPlayerOnItem());

        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        setBounds();

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x - minX, y - minY);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x - minX, y - minY);
                } else {
                    Tiles.drawTile(context, cell, x - minX, y - minY);
                }
            }
        }
        healthLabel.setText("\n" + map.getPlayer().getHealth());
        sword.setText("sword "+ Inventory.inventory.get("sword"));
        armour.setText("armour "+ Inventory.inventory.get("armour"));
        key.setText("key "+ Inventory.inventory.get("key"));
        strengthLabel.setText("" + map.getPlayer().getStrength());

        isKeyPressed = !isKeyPressed;

    }

    private void overrideSavedGame(String title, int[] ids) {
        int gameStateId = ids[0];
        int playerModelId = ids[1];
        Date date = new Date();

        PlayerModel playerModel = new PlayerModel(this.map.getPlayer());
        playerModel.setId(playerModelId);
        dbManager.updatePlayer(playerModel);

        GameState gameState = new GameState(title, this.currentLevel, new java.sql.Date(date.getTime()));
        gameState.setId(gameStateId);
        dbManager.updateGameState(gameState);

        dbManager.deleteEnemyByGameStateId(gameStateId);
        dbManager.deleteItemByGameStateId(gameStateId);
        dbManager.deleteInventoryByGameStateId(gameStateId);

        for (Enemy enemy : this.map.getEnemies()) {
            this.dbManager.saveEnemy(enemy, gameStateId);
        }
        for (Item item : this.map.getItems()) {
            this.dbManager.saveItem(item, gameStateId);
        }
        for (String item : Inventory.inventory.keySet()) {
            this.dbManager.saveInventory(item, Inventory.inventory.get(item), gameStateId);
        }
    }

    private void saveNewGame(String title) {
        this.dbManager.savePlayer(this.map.getPlayer());
        Date date = new Date();
        int gameStateId =  this.dbManager.saveGameState(title, this.currentLevel, new java.sql.Date(date.getTime()));


        for (Enemy enemy : this.map.getEnemies()) {
            this.dbManager.saveEnemy(enemy, gameStateId);
        }
        for (Item item : this.map.getItems()) {
            this.dbManager.saveItem(item, gameStateId);
        }
        for (String item : Inventory.inventory.keySet()) {
            this.dbManager.saveInventory(item, Inventory.inventory.get(item), gameStateId);
        }
    }
}
