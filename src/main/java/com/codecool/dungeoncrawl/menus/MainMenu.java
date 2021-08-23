package com.codecool.dungeoncrawl.menus;


import com.codecool.dungeoncrawl.Main;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


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
        if (textField.getText().length() > 0){
            stage.hide();
            mainController.createScene(textField.getText());
            mainController.startGame();
        }
    }

    private void loadGameScreen(){
        Group group = new Group();
        BorderPane borderPane = new BorderPane();
        StackPane stackPane = new StackPane();
        group.getChildren().addAll(borderPane, stackPane);
        stage.hide();
        context.clearRect(0.0, 0.0, 700.0, 700.0);
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setFill(Color.RED);
        context.fillText("SAVED GAMES", 360, 170);
        borderPane.setCenter(canvas);

        // TODO implement getLoadSavedGames from DB

        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.show();
    }

}
