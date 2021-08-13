package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
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
    GameMap map1 = MapLoader.loadMap(1);
    GameMap map2 = MapLoader.loadMap(2);
    GameMap map = map1;
    Inventory inventory = map.getPlayer().inventory;
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();
    Label strengthLabel = new Label();
    Button pickUpItem = new Button("pickUp");


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        context.scale(1.4, 1.4);

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
        Cell target = map.getPlayer().getCell();

        inventory.updateInventory(map.getPlayer().getCell().getType(), 1);

        if (target.getTileName().equals("armour")) {
            map.getPlayer().setHealth(target.getActor().getHealth() + target.getType().getIncreaseValue());
        } else {
            map.getPlayer().setStrength(target.getActor().getStrength() + target.getType().getIncreaseValue());
        }

        map.getPlayer().getCell().setType(CellType.FLOOR);
        refresh();
        pickUpItem.setVisible(false);
    };

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE:
                System.exit(0);
            case UP:
                step(0, -1);
                refresh();
                break;
            case DOWN:
                step(0, 1);
                refresh();
                break;
            case LEFT:
                step(-1, 0);
                refresh();
                break;
            case RIGHT:
                step(1,0);
                refresh();
                break;
        }
    }

    private void step(int x, int y) {
        map.getPlayer().fight(x, y);
        map.getPlayer().move(x, y);
        if (!map.getPlayer().getCell().getNeighbor(x, y).isEnemy()) {
            enemyMove();
        }
        if (map.getPlayer().getX() == 27 && map.getPlayer().getY() == 22){
            int oldHealth = map.getPlayer().getHealth();
            int oldStrength = map.getPlayer().getStrength();
            map2.getPlayer().playerName = map.getPlayer().playerName;
            map2.getPlayer().setHealth(oldHealth);
            map2.getPlayer().setStrength(oldStrength);
            map = map2;
        }
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

    private void refresh() {
        if(map.getPlayer().getHealth() <= 0 || (map.getPlayer().getCell().getX() == 2 && map.getPlayer().getCell().getY() == 1)) {
            map = MapLoader.loadMap(0);
        }

        map.getPlayer().tryToOpenDoor();
        pickUpItem.setVisible(map.getPlayer().isPlayerOnItem(map.getPlayer().getCell()));

        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = map.getPlayer().getX() - 50; x < map.getPlayer().getX() + 50; x++) {
            for (int y = map.getPlayer().getY() - 50; y < map.getPlayer().getY() + 50; y++) {
                Cell cell;
                try {
                    cell = map.getCell((x + map.getPlayer().getX()) - map.getHeight()/3, y + map.getPlayer().getY() - map.getWidth()/3);
                } catch (IndexOutOfBoundsException e) {
                    cell = new Cell(map, 1, 1, CellType.EMPTY);
                }
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("\n" + map.getPlayer().getHealth());
        inventoryLabel.setText("" + inventory);
        strengthLabel.setText("" + map.getPlayer().getStrength());
    }
}
