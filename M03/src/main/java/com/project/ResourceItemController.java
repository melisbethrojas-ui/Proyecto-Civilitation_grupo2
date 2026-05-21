package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResourceItemController {

    @FXML private ImageView icon;
    @FXML private Label nameLabel;
    @FXML private Label amountLabel;

    public void setData(String name, String iconName, int amount) {

        nameLabel.setText(name);
        amountLabel.setText("Cantidad: " + amount);

        icon.setImage(new Image(
                getClass().getResource("/assets/img/" + iconName).toExternalForm()
        ));
    }
}