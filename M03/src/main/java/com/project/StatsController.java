package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class StatsController {

    @FXML
    private VBox statsBox;

    @FXML
    public void initialize() {
        updateStats();
    }

    public void updateStats() {
        statsBox.getChildren().clear();

        // Aquí luego conectaremos con tu Civilization real
        // Por ahora valores placeholder

        // ⭐ RECURSOS
        addStatItem("Comida: 0");
        addStatItem("Madera: 0");
        addStatItem("Hierro: 0");
        addStatItem("Maná: 0");

        // ⭐ EDIFICIOS
        addStatItem("Granja: 0");
        addStatItem("Carpintería: 0");
        addStatItem("Herrería: 0");
        addStatItem("Torre Arcana: 0");
        addStatItem("Iglesia: 0");

        // ⭐ UNIDADES
        addStatItem("Unidades totales: 0");
    }

    private void addStatItem(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("item-label");

        HBox item = new HBox(label);
        item.setSpacing(10);
        item.getStyleClass().add("list-item");

        statsBox.getChildren().add(item);
    }

    @FXML
    private void goBack() {
        UtilsViews.setViewAnimating("Desktop");
    }
}
