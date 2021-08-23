package com.codecool.dungeoncrawl.menus;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.utils.items.Inventory;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class GameOverMenu extends Menu {

    public GameOverMenu(Main mainController) {
        super(mainController);
    }


    @Override
    protected void beforeMenuDisplayEvents() {
        createTitleAndBackground();
        mainController.stopEnemiesRefresh();
    }

    @Override
    protected void createTitleAndBackground() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setFill(Color.RED);
        context.fillText("GAME OVER", 360, 220);
    }

    @Override
    protected void createButtons(StackPane stackPane) {
        Button startGame = new Button();

        startGame.setText("Play again");
        startGame.setStyle("-fx-background-color:" +
                "linear-gradient(#f0ff35, #a9ff00)," +
                "radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);" +
                "-fx-background-radius: 30;" +
                "-fx-background-insets: 0;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-text-fill: black;");
        startGame.setOnMouseClicked(mouseEvent -> playAgain());

        Button exit = createExitButton();
        exit.setText("Exit");
        exit.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);"+
                "-fx-background-radius: 30;" +
                "-fx-background-insets: 0,1,2,3,0;" +
                "-fx-text-fill: #654b00;" +
                "-fx-padding: 10 20 10 20;");

        stackPane.getChildren().addAll(canvas, startGame, exit);
        startGame.setTranslateY(-60);
    }

    private void playAgain() {
        String characterName = map.getPlayer().getPlayerName();
        Inventory.inventory.values().clear();
        mainController.createScene(characterName);
        mainController.startGame();
    }


}
