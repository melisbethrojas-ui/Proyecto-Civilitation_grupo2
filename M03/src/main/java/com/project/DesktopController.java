package com.project;

import javafx.fxml.FXML;

public class DesktopController {

    @FXML
    public void initialize() {
        // ⭐ AÑADIDO: si hay botones en el FXML, aplicarán estilo automáticamente
        // (no rompe nada aunque no existan)
        // UtilsViews.applyGlobalButtonStyles(); // solo si luego quieres activar un estilo global
    }

    @FXML
    private void openUnits() {
        UtilsViews.setViewAnimating("Units");
    }

    @FXML
    private void openBuildings() {
        UtilsViews.setViewAnimating("Buildings");
    }

    @FXML
    private void openBattle() {
        UtilsViews.setViewAnimating("Battle");
    }

    @FXML
    private void openStats() {
        UtilsViews.setViewAnimating("Stats");
    }

    @FXML
    private void openResources() {
        UtilsViews.setViewAnimating("Resources");
    }

    @FXML
    private void openDefenses() {
        UtilsViews.setViewAnimating("Defenses");
    }

    @FXML
    private void openTech() {
        UtilsViews.setViewAnimating("Tech");
    }
}
