package com.project;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

public class DefensesController {

    @FXML
    private VBox defensesList;

    public void initialize() {
        updateDefenses();
    }

    public void updateDefenses() {
        defensesList.getChildren().clear();

        addDefenseItem("Defensa básica", "arrow_tower.png", false, 50);
        addDefenseItem("Defensa pesada", "catapult_tower.png", true, 120);
        addDefenseItem("Defensa avanzada", "rocket_tower.png", false, 200);
    }

    private void addDefenseItem(String name, String iconName, boolean built, int cost) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/defenseItem.fxml"));
            HBox item = loader.load();

            DefenseItemController controller = loader.getController();
            controller.setData(name, iconName, built, cost);

            defensesList.getChildren().add(item);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() {
        UtilsViews.setViewAnimating("Desktop");
    }
}
