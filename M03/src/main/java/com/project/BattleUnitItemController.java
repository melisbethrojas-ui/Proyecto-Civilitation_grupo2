package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BattleUnitItemController {

    @FXML private ImageView icon;
    @FXML private Label nameLabel;
    @FXML private Label statsLabel;

    public void setData(String name, int hp, int atk, int def, String iconName) {
        nameLabel.setText(name);
        statsLabel.setText("HP: " + hp + "  ATK: " + atk + "  DEF: " + def);

        icon.setImage(new Image(
            getClass().getResource("/assets/img/" + iconName).toExternalForm()
        ));
    }
}
