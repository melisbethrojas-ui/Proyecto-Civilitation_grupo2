package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BattleUnitItemController {

    @FXML private ImageView icon;
    @FXML private Label nameLabel;
    @FXML private Label initialLabel;
    @FXML private Label remainingLabel;
    @FXML private Label lossesLabel;
    @FXML private Label statsLabel;

    public void setData(
            String name,
            String iconName,
            int initial,
            int remaining,
            int atk,
            int def
    ) {
        nameLabel.setText(name);

        icon.setImage(new Image(
                getClass().getResource("/assets/img/" + iconName).toExternalForm()
        ));

        initialLabel.setText("Inicial: " + initial);
        remainingLabel.setText("Restantes: " + remaining);

        int losses = initial - remaining;
        lossesLabel.setText("Pérdidas: " + losses);

        statsLabel.setText("ATK: " + atk + " | DEF: " + def);
    }
}
