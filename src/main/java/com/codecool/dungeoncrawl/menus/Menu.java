package com.codecool.dungeoncrawl.menus;


import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.GameMapManager.GameMap;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public abstract class Menu {
    protected final Main mainController;
    protected final Canvas canvas;
    protected Stage stage;
    protected final GraphicsContext context;
    protected GameMap map;


    public Menu(Main mainController){
        this.mainController = mainController;
        this.canvas = mainController.getCanvas();
        this.stage = mainController.getStage();
        this.context = canvas.getGraphicsContext2D();
        this.map = mainController.getMap();
    }

    public void handleMenu(){
        beforeMenuDisplayEvents();
        Scene menuScene = drawMenu();
        stage.setScene(menuScene);
        stage.show();
    }

    protected void beforeMenuDisplayEvents(){}

    protected Scene drawMenu(){
        StackPane stackPane = new StackPane();
        createTitleAndBackground();
        createButtons(stackPane);
        return new Scene(stackPane);
    }
    protected void createTitleAndBackground(){}
    protected void createButtons(StackPane stackPane){}

    protected Button createExitButton(){
        Button exit = new Button();
        exit.setText("Exit");
        exit.setOnMouseClicked(mouseEvent -> stage.close());
        return exit;
    }
}
