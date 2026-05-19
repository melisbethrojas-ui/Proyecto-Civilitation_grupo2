package com.project;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

public class TechController {

    @FXML
    private VBox techList;

    public void initialize() {
        updateTech();
    }

    public void updateTech() {
        techList.getChildren().clear();

        // Datos de ejemplo (luego los conectas con tu modelo)
        addTechItem("Mejorar los ataques", "attack.png", false, 50);
        addTechItem("Mejorar la defensa", "defense.png", true, 80);
    }

    private void addTechItem(String name, String iconName, boolean researched, int cost) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/techItem.fxml"));
            HBox item = loader.load();

            TechItemController controller = loader.getController();
            controller.setData(name, iconName, researched, cost);

            techList.getChildren().add(item);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() {
        UtilsViews.setViewAnimating("Desktop");
    }
}
