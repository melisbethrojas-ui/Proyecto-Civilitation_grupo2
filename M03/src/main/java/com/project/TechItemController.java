package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TechItemController {

    @FXML private ImageView icon;
    @FXML private Label nameLabel;
    @FXML private Label statusLabel;
    @FXML private Label costLabel;
    @FXML private Button actionButton;

    private String techName;

    public void setData(String name, String iconName, boolean researched, int cost) {

        this.techName = name;

        nameLabel.setText(name);
        icon.setImage(new Image(getClass().getResource("/assets/img/" + iconName).toExternalForm()));

        if (researched) {
            statusLabel.setText("Estado: Investigada");
            actionButton.setText("Mejorar");
        } else {
            statusLabel.setText("Estado: No investigada");
            actionButton.setText("Investigar");
        }

        costLabel.setText("Coste: " + cost + " mana");
    }

    @FXML
    private void onAction() {
        // Aquí llamas a tu modelo
        System.out.println("Acción sobre tecnología: " + techName);
    }
}
