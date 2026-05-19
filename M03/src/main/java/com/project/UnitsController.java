package com.project;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

public class UnitsController {

    @FXML
    private VBox unitsList;

    public void initialize() {
        updateUnits();
    }

    public void updateUnits() {
        unitsList.getChildren().clear();

        addUnitItem("Unidad cuerpo a cuerpo básica", "swordsman.png", 10, 5, 30, 20, 10);
        addUnitItem("Unidad anti‑caballería", "spear.png", 12, 4, 25, 15, 5);
        addUnitItem("Unidad de disparo pesado", "crossbow.png", 15, 3, 20, 10, 5);
        addUnitItem("Unidad de asedio", "cannon.png", 25, 2, 40, 30, 20);
        addUnitItem("Unidad mágica", "magician.png", 20, 5, 15, 25, 0);
        addUnitItem("Unidad de apoyo", "priest.png", 5, 5, 20, 10, 0);
    }

    private void addUnitItem(String name, String iconName, int atk, int def, int hp, int costFood, int costIron) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/unitItem.fxml"));
            HBox item = loader.load();

            UnitItemController controller = loader.getController();
            controller.setData(name, iconName, atk, def, hp, costFood, costIron);

            unitsList.getChildren().add(item);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() {
        UtilsViews.setViewAnimating("Desktop");
    }
}
