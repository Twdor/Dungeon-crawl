package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.utils.Inventory;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.Random;


public class Main extends Application {
    GameMap map = MapLoader.loadMap(1, null);
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    double contextScale = 1.4;
    int minX, minY, maxX, maxY;
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();
    Label strengthLabel = new Label();
    Button pickUpItem = new Button("pickUp");


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
            map.getPlayer().playerName = name;
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


        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
        borderPane.setLeft(inventoryUi);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
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
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1,0);
                refresh();
                break;
        }
    }
//    private void onKeyPressed(KeyEvent keyEvent) {
//        switch (keyEvent.getCode()) {
//            case ESCAPE:
//                System.exit(0);
//            case UP:
//                step(0, -1);
//                refresh();
//                break;
//            case DOWN:
//                step(0, 1);
//                refresh();
//                break;
//            case LEFT:
//                step(-1, 0);
//                refresh();
//                break;
//            case RIGHT:
//                step(1,0);
//                refresh();
//                break;
//        }
//    }

    private void step(int x, int y) {
        map.getPlayer().fight(x, y);
        map.getPlayer().move(x, y);
        if (!map.getPlayer().getCell().getNeighbor(x, y).isEnemy()) {
            enemyMove();
        }
//        if (map.getPlayer().getX() == 27 && map.getPlayer().getY() == 22){
//            int oldHealth = map.getPlayer().getHealth();
//            int oldStrength = map.getPlayer().getStrength();
//            map2.getPlayer().playerName = map.getPlayer().playerName;
//            map2.getPlayer().setHealth(oldHealth);
//            map2.getPlayer().setStrength(oldStrength);
//            map = map2;
//        }
    }

    private void enemyDirection(String direction, Cell cell) {
        switch (direction) {
            case "UP":
                map.getCell(cell.getX(), cell.getY()).getActor().move(0, -1);
                break;
            case "DOWN":
                map.getCell(cell.getX(), cell.getY()).getActor().move(0, 1);
                break;
            case "LEFT":
                map.getCell(cell.getX(), cell.getY()).getActor().move(-1, 0);
                break;
            case "RIGHT":
                map.getCell(cell.getX(), cell.getY()).getActor().move(1, 0);
                break;
        }
    }

    private void enemyMove() {
        String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
        Random random = new Random();
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() instanceof Skeleton) {
                    String direction = directions[random.nextInt(4)];
                    enemyDirection(direction, cell);
                }
            }
        }
    }

    private void setBounds() {
        minX = (int) (map.getPlayer().getX()-((map.getWidth()/contextScale-1)/2));
        minY = (int) (map.getPlayer().getY()-((map.getHeight()/contextScale-1)/2));
        maxX = (int) (map.getPlayer().getX()+((map.getWidth()/contextScale+1)/2));
        maxY = (int) (map.getPlayer().getY()+((map.getHeight()/contextScale+2)/2));

        if (minX < 0) { maxX -= minX; minX = 0; }
        if (maxX > map.getWidth()-1) { maxX = map.getWidth()-1; minX = (int) (map.getWidth()-1 - map.getWidth()/contextScale); }
        if (minY <= 0) { maxY -= minY-1; minY = 0; }
        if (maxY > map.getHeight()-1) { maxY = map.getHeight()-1; minY = (int) (map.getHeight()-1 - map.getHeight()/contextScale); }
    }

    private void refresh() {
        map = map.getPlayer().isPlayerOnStairs() ? MapLoader.loadMap(2, map.getPlayer()) : map.getPlayer().isDead() ? MapLoader.loadMap(0, null) : map;
        pickUpItem.setVisible(map.getPlayer().isPlayerOnItem());

        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        setBounds();
        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x - minX, y -minY);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(),x - minX, y -minY);
                } else {
                    Tiles.drawTile(context, cell,x - minX, y -minY);
                }
            }
        }
        healthLabel.setText("\n" + map.getPlayer().getHealth());
        inventoryLabel.setText("" + Inventory.getInventoryToString());
        strengthLabel.setText("" + map.getPlayer().getStrength());
    }
}
