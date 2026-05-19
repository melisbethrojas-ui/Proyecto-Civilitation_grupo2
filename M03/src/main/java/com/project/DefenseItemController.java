package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DefenseItemController {

    @FXML private ImageView icon;
    @FXML private Label nameLabel;
    @FXML private Label statusLabel;
    @FXML private Label costLabel;
    @FXML private Button actionButton;

    private String defenseName;

    public void setData(String name, String iconName, boolean built, int cost) {

        this.defenseName = name;

        nameLabel.setText(name);
        icon.setImage(new Image(getClass().getResource("/assets/img/" + iconName).toExternalForm()));

        if (built) {
            statusLabel.setText("Estado: Construida");
            actionButton.setText("Mejorar");
        } else {
            statusLabel.setText("Estado: No construida");
            actionButton.setText("Construir");
        }

        costLabel.setText("Coste: " + cost + " madera");
    }

    @FXML
    private void onAction() {
        // Aquí llamas a tu modelo
        System.out.println("Acción sobre defensa: " + defenseName);
    }
}
