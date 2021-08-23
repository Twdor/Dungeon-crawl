package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.utils.Cell;
import com.codecool.dungeoncrawl.logic.utils.items.Inventory;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
//import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import java.sql.SQLException;






public class Main extends Application {
    GameMap map = MapLoader.loadMap(1, null);
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
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
    boolean isKeyPressed = false;
    boolean isAtLeastOneEnemyAlive = true;

//    GameDatabaseManager dbManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        context.scale(contextScale, contextScale);

        GridPane ui = new GridPane();
        GridPane inventoryUi = new GridPane();

        inventoryUi.setPrefWidth(200);
        inventoryUi.setPadding(new Insets(10));

        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        inventoryUi.add(new Label("Inventory: "), 0, 0);
        inventoryUi.add(inventoryLabel, 5, 5);
        inventoryUi.add(pickUpItem, 0, 10);

        TextField textField = new TextField();
        textField.setFocusTraversable(false);

        Button button = new Button("Enter name");

        // deletes text/input field and button
        button.setOnAction(action -> {
            String name = textField.getText();
            map.getPlayer().setPlayerName(name);
            ui.getChildren().remove(textField);
            ui.getChildren().remove(button);
            ui.add(new Label(name), 0, 3, 1, 1);
        });

        ui.add(new Label("Character name: "), 0, 0); //
        ui.add(textField, 0, 1, 2, 1);
        ui.add(button, 0, 2, 2, 1);

        ui.add(new Label(""), 0, 3); //empty space

        ui.add(new Label("\nHealth: "), 0, 4);
        ui.add(healthLabel, 1, 4);
        ui.add(new Label("Strength: "), 0, 5);
        ui.add(strengthLabel, 1, 5);

        pickUpItem.setFocusTraversable(false);
        pickUpItem.setOnAction(itemEvent);
        pickUpItem.setVisible(false);

//        setupDbManager();

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
        borderPane.setLeft(inventoryUi);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        enemyRefresh();
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);


        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    public void enemyRefresh() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void moveEnemies() {
        if (map.getEnemies().size() == 0) {
            timeline.stop();
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

    EventHandler<ActionEvent> itemEvent = actionEvent -> {
        Cell cell = map.getPlayer().getCell();

        Inventory.setInventory(cell.getItem().getName(), 1);

        if (cell.getTileName().equals("armour")) {
            map.getPlayer().setHealth(cell.getActor().getHealth() + cell.getItem().getIncreaseValue());
        } else {
            map.getPlayer().setStrength(cell.getActor().getStrength() + cell.getItem().getIncreaseValue());
        }

        cell.deleteItem();
        refresh();
        pickUpItem.setVisible(false);
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

    private void refresh() {
        if (map.getPlayer().isPlayerOnStairs()) {
            map = MapLoader.loadMap(2, map.getPlayer());
            enemyRefresh();
            isAtLeastOneEnemyAlive = true;
        }

        if (!isKeyPressed && isAtLeastOneEnemyAlive) moveEnemies();

//        map = map.getPlayer().isPlayerOnStairs() ? MapLoader.loadMap(2, map.getPlayer()) : map.getPlayer().isDead() ? MapLoader.loadMap(0, null) : map;
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
        inventoryLabel.setText("" + Inventory.getInventoryToString());
        strengthLabel.setText("" + map.getPlayer().getStrength());

        isKeyPressed = !isKeyPressed;


//        private void setupDbManager () {
//            dbManager = new GameDatabaseManager();
//            try {
//                dbManager.setup();
//            } catch (SQLException ex) {
//                System.out.println("Cannot connect to database.");
//            }
//        }
//
//        private void exit () {
//            try {
//                stop();
//            } catch (Exception e) {
//                System.exit(1);
//            }
//            System.exit(0);
//        }
    }
}
