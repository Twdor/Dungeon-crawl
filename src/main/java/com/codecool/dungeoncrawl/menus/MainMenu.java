package com.codecool.dungeoncrawl.menus;


import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;


public class MainMenu extends Menu{

    private TextField textField;


    public MainMenu(Main mainController){
        super(mainController);

    }

    @Override
    protected void createButtons(StackPane stackPane){
        Button startGame = new Button();
        startGame.setText("Start Game");
        startGame.setStyle("-fx-background-color:" +
                "linear-gradient(#f0ff35, #a9ff00)," +
                "radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);" +
                "-fx-background-radius: 30;" +
                "-fx-background-insets: 0;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-text-fill: black;");
        startGame.setOnMouseClicked(mouseEvent -> startNewGame(textField));

        Button loadGame = new Button();
        loadGame.setStyle("-fx-background-color:" +
                "linear-gradient(#ffd65b, #e68400)," +
                "linear-gradient(#ffef84, #f2ba44)," +
                "linear-gradient(#ffea6a, #efaa22)," +
                "linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%)," +
                "linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));" +
                "-fx-background-radius: 30;" +
                "-fx-background-insets: 0,1,2,3,0;" +
                "-fx-text-fill: #654b00;" +
                "-fx-padding: 10 20 10 20;");
        loadGame.setText("Load saved game");

        loadGame.setOnMouseClicked(e -> loadGameScreen());

        textField = new TextField ();
        textField.setStyle("-fx-max-width: 150");
        textField.setPromptText("Nick");

        Button exit = createExitButton();
        exit.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);"+
                "-fx-background-radius: 30;" +
                "-fx-background-insets: 0,1,2,3,0;" +
                "-fx-text-fill: #654b00;" +
                "-fx-padding: 10 20 10 20;");
        exit.setOnAction(event -> stage.close());

        stackPane.getChildren().addAll(canvas, startGame, loadGame, exit, textField);
        startGame.setTranslateY(-120);
        loadGame.setTranslateY(-40);
        textField.setTranslateY(-80);

    }

    @Override
    protected void createTitleAndBackground(){
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setFill(Color.RED);
        context.fillText("MAIN MENU", 360, 170);
    }

    private void startNewGame(TextField textField){
        String characterName = textField.getText();
        if (characterName.length() > 0){
            stage.hide();
            mainController.createScene(characterName, null);
            mainController.run();
        }
    }


    private void loadGameScreen(){
        Group group = new Group();
        BorderPane borderPane = new BorderPane();
        GridPane stackPane = new GridPane();

        Button cancel = new Button("cancel");
        cancel.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);"+
                "-fx-background-radius: 30;" +
                "-fx-background-insets: 0,1,2,3,0;" +
                "-fx-text-fill: #654b00;" +
                "-fx-padding: 10 20 10 20;");
        cancel.setOnAction(event -> {
            handleMenu();
        });

//        cancel.setTranslateX(360);


        group.getChildren().addAll(borderPane, stackPane);
        stage.hide();
        context.clearRect(0.0, 0.0, 700.0, 700.0);
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        borderPane.setCenter(canvas);
        stage.setTitle("Saved Games");

        ScrollBar sc = new ScrollBar();
        sc.setLayoutX(canvas.getWidth()-sc.getWidth());
        sc.setMin(0);
        sc.setOrientation(Orientation.VERTICAL);
        sc.setPrefHeight(180);
        sc.setMax(360);


        List<GameState> savedGames = mainController.getDbManager().getLoadGames();
        stackPane.setPadding(new Insets(10));
        stackPane.setVgap(10);
        int positionY = 1;
        for (GameState savedState : savedGames) {
            Button saveBtn = new Button();

            saveBtn.setText(String.format(
                    "Title:   %s%n" +
                    "Saved at:   %s%n" +
                    "Level:   %s%n" +
                    "Nick:   %s%n",
                    savedState.getTitle(),
                    savedState.getSavedAt(),
                    savedState.getCurrentMap(),
                    savedState.getPlayer().getPlayerName())
            );
            saveBtn.setStyle(
                    "-fx-border-color: #ff0000;" +
                    " -fx-border-width: 5px;" +
                    "-fx-text-fill: #ff0000;");
//                    "-fx-font-size: 0.5em;");
            saveBtn.setUnderline(true);
            stackPane.add(saveBtn, 1, positionY);

            sc.valueProperty().addListener((ov, old_val, new_val) -> group.setLayoutY(-new_val.doubleValue()));

            saveBtn.setOnAction(e -> {

                PlayerModel playerModel = savedState.getPlayer();
                mainController.setCurrentLevel(savedState.getCurrentMap());

                mainController.createScene(playerModel.getPlayerName(), savedState);

                mainController.run();
            });
            positionY += 1;
        }
        if (savedGames.isEmpty()) {
            Text noSaves = new Text("No current saved game! ");
            noSaves.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            noSaves.setFill(Color.RED);
            stackPane.add(noSaves, 1, positionY);
        }

        stackPane.add(cancel, 1, positionY+1);

        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.show();
    }



}
