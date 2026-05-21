package com.project;

import Logic.Civilization;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.input.KeyCombination;

public class Main extends Application {

    final int WINDOW_WIDTH = 600;
    final int WINDOW_HEIGHT = 400;
    final int MIN_WIDTH = 600;
    final int MIN_HEIGHT = 400;

    @Override
    public void start(Stage stage) throws Exception {
        GameState.initGame(); 
        // genera recursos cada minuto
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    Civilization civ = GameState.getPlayer();
                    civ.generateResources();
                }
            },
            60000, 
            60000   
        );


        stage.setWidth(1280);
        stage.setHeight(1000);
        stage.setMaximized(true);

        // permitir redimensionar
        stage.setResizable(true);

        
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        // Carrega la vista inicial des del fitxer FXML
        UtilsViews.parentContainer.setStyle("-fx-font: 14 arial;");
        UtilsViews.addView(getClass(), "Desktop", "/assets/gui/desktop.fxml");
        UtilsViews.addView(getClass(), "Units", "/assets/gui/units.fxml");
        UtilsViews.addView(getClass(), "Buildings", "/assets/gui/buildings.fxml");
        UtilsViews.addView(getClass(), "Battle", "/assets/gui/battle.fxml");
        UtilsViews.addView(getClass(), "Stats", "/assets/gui/stats.fxml");
        UtilsViews.addView(getClass(), "Resources", "/assets/gui/resources.fxml");
        UtilsViews.addView(getClass(), "Tech", "/assets/gui/tech.fxml");

        UtilsViews.setView("Desktop");
        Scene scene = new Scene(UtilsViews.parentContainer);

        scene.getStylesheets().add(getClass().getResource("/assets/gui/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Civilizations 2526");
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);
        stage.show();

        // Afegeix una icona només si no és un Mac
        if (!System.getProperty("os.name").contains("Mac")) {
            Image icon = new Image("file:icons/icon.png");
            stage.getIcons().add(icon);
        }

        // guardar el stage en UtilsViews para usar fullscreen desde controladores
        UtilsViews.setStage(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
        @Override
    public void stop() {
        System.exit(0);
    }

}
