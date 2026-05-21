package com.project;

import Logic.Civilization;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DesktopController {

    @FXML private Label foodLabel;
    @FXML private Label woodLabel;
    @FXML private Label ironLabel;
    @FXML private Label manaLabel;

    @FXML private Label attackTechLabel;
    @FXML private Label defenseTechLabel;
    @FXML private Label totalUnitsLabel;
    @FXML private Label threatLabel;

    @FXML
    public void initialize() {
        loadSummary();
        threatLabel.setText("Amenaza: " + GameState.getThreatLevel() + "%");
        new java.util.Timer().schedule(
        new java.util.TimerTask() {
            @Override
            public void run() {
                javafx.application.Platform.runLater(() -> loadSummary());
            }
        },
        60000,   
        60000  
    );
    }

    // Siempre que vuelve al Desktop refresca la informacion
    public void refresh() {
        loadSummary();
        threatLabel.setText("Amenaza: " + GameState.getThreatLevel() + "%");

    }

    private void loadSummary() {

        Civilization civ = GameState.getPlayer();

        // Lee valores desde el controlador de Resources para mostrar el resumen en el Desktop
        ResourcesController rc =
                (ResourcesController) UtilsViews.getController("Resources");

        if (rc != null) {
            foodLabel.setText(String.valueOf(rc.getDisplayedFood()));
            woodLabel.setText(String.valueOf(rc.getDisplayedWood()));
            ironLabel.setText(String.valueOf(rc.getDisplayedIron()));
            manaLabel.setText(String.valueOf(rc.getDisplayedMana()));
        }

        attackTechLabel.setText("Ataque: " + civ.getTechnologyAttack());
        defenseTechLabel.setText("Defensa: " + civ.getTechnologyDefense());

        int totalUnits = 0;
        for (int i = 0; i < civ.army.length; i++) {
            totalUnits += civ.army[i].size();
        }

        totalUnitsLabel.setText("Unidades: " + totalUnits);
        threatLabel.setText("Amenaza: " + GameState.getThreatLevel() + "%");
    }

    // NAVEGACIÓN

    @FXML private void openUnits() { UtilsViews.setViewAnimating("Units"); }
    @FXML private void openBuildings() { UtilsViews.setViewAnimating("Buildings"); }
    @FXML private void openBattle() { UtilsViews.setViewAnimating("Battle"); }
    @FXML private void openStats() { UtilsViews.setViewAnimating("Stats"); }
    @FXML private void openResources() { UtilsViews.setViewAnimating("Resources"); }
    @FXML private void openTech() { UtilsViews.setViewAnimating("Tech"); }
}
