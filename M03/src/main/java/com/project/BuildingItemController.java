package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BuildingItemController {

    @FXML
    private ImageView icon;

    @FXML
    private Label nameLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private Label costLabel;

    @FXML
    private Label effectLabel;

    @FXML
    private Label errorLabel;

    @FXML
    public Button upgradeButton;

    public void setData(
            String name,
            String iconName,
            int level,
            int food,
            int wood,
            int iron,
            String effect
    ) {

        nameLabel.setText(name);

        levelLabel.setText("Nivel actual: " + level);

        costLabel.setText(
                "Comida: " + food +
                " | Madera: " + wood +
                " | Hierro: " + iron
        );

        effectLabel.setText(effect);

        icon.setImage(
                new Image(
                        getClass()
                                .getResource("/assets/img/" + iconName)
                                .toExternalForm()
                )
        );
    }

    public void showError(String msg) {
        errorLabel.setText(msg);
        errorLabel.setVisible(true);
    }

    public void clearError() {
        errorLabel.setVisible(false);
    }
}