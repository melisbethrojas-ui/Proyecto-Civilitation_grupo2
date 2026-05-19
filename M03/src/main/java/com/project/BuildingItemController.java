package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BuildingItemController {

    @FXML private ImageView icon;
    @FXML private Label nameLabel;
    @FXML private Label costLabel;
    @FXML private Label effectLabel;
    @FXML private Label statusLabel;
    @FXML private Button actionButton;

    private String buildingName;

    public void setData(String name, String iconName, String cost, String effect, boolean built) {

        this.buildingName = name;

        nameLabel.setText(name);
        costLabel.setText("Coste: " + cost);
        effectLabel.setText("Efecto: " + effect);

        if (built) {
            statusLabel.setText("Estado: Construido");
            actionButton.setText("Mejorar");
        } else {
            statusLabel.setText("Estado: No construido");
            actionButton.setText("Construir");
        }

        icon.setImage(new Image(
            getClass().getResource("/assets/img/" + iconName).toExternalForm()
        ));
    }

    @FXML
    private void onAction() {
        System.out.println("Acción sobre edificio: " + buildingName);
    }
}
