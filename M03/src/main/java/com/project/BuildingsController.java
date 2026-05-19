package com.project;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

public class BuildingsController {

    @FXML
    private VBox buildingsList;

    @FXML
    public void initialize() {
        updateBuildings();
    }

    public void updateBuildings() {
        buildingsList.getChildren().clear();

        // Coste real del PDF
        String cost = "5000 comida, 10000 madera, 12000 hierro";

        addBuildingItem("Granja", "farm.png", cost, "+5% comida", false);
        addBuildingItem("Carpintería", "carpentry.png", cost, "+5% madera", false);
        addBuildingItem("Herrería", "smithy.png", cost, "+5% hierro", false);
        addBuildingItem("Torre Arcana", "mana.png", cost, "Genera maná", false);
        addBuildingItem("Iglesia", "church.png", cost, "Permite crear sacerdotes", false);
    }

    private void addBuildingItem(String name, String iconName, String cost, String effect, boolean built) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/buildingItem.fxml"));
            HBox item = loader.load();

            BuildingItemController controller = loader.getController();
            controller.setData(name, iconName, cost, effect, built);

            item.getStyleClass().add("list-item");
            buildingsList.getChildren().add(item);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() {
        UtilsViews.setViewAnimating("Desktop");
    }
}
