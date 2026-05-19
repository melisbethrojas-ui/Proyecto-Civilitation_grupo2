package com.project;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

public class ResourcesController {

    @FXML
    private VBox resourcesList;

    public void initialize() {
        updateResources();
    }

    public void updateResources() {
        resourcesList.getChildren().clear();

        // Aquí deberías leer los valores reales del modelo
        int food = 120;
        int wood = 80;
        int iron = 40;
        int mana = 10;

        addResourceItem("Comida", "banquet.png", food);
        addResourceItem("Madera", "wood.png", wood);
        addResourceItem("Hierro", "iron.png", iron);
        addResourceItem("Poción", "mana.png", mana);
    }

    private void addResourceItem(String name, String iconName, int amount) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/resourceItem.fxml"));
            HBox item = loader.load();

            ResourceItemController controller = loader.getController();
            controller.setData(name, iconName, amount);

            resourcesList.getChildren().add(item);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() {
        UtilsViews.setViewAnimating("Desktop");
    }
}
