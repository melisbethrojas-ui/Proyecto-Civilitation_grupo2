package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UnitItemController {

    @FXML private ImageView icon;
    @FXML private Label nameLabel;
    @FXML private Label statsLabel;
    @FXML private Label costLabel;
    @FXML private Button actionButton;

    private String unitName;

    public void setData(String name, String iconName, int attack, int defense, int hp, int costFood, int costIron) {

        this.unitName = name;

        nameLabel.setText(name);
        statsLabel.setText("ATK: " + attack + "  DEF: " + defense + "  HP: " + hp);
        costLabel.setText("Coste: " + costFood + " comida, " + costIron + " hierro");

        icon.setImage(new Image(getClass().getResource("/assets/img/" + iconName).toExternalForm()));
    }

    @FXML
    private void onAction() {
        // Aquí llamas a tu modelo real
        System.out.println("Crear unidad: " + unitName);
    }
}
