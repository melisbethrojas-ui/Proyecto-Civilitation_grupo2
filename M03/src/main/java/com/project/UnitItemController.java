package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UnitItemController {

    @FXML private ImageView icon;

    @FXML private Label unitName;
    @FXML private Label stats;
    @FXML private Label cost;
    @FXML private Label errorLabel;

    @FXML public Button btn1;
    @FXML public Button btn5;
    @FXML public Button btn10;

    public void setData(
        String name,
        String iconName,
        int atk,
        int armor,
        int food,
        int wood,
        int iron,
        int mana,
        int unitCount
    ) {

    // Nombre con cantidad
    unitName.setText(name + " (x" + unitCount + ")");

    stats.setText(
            "ATK: " + atk +
            " | DEF: " + armor
    );

    cost.setText(
            "Food: " + food +
            " Wood: " + wood +
            " Iron: " + iron +
            " Mana: " + mana
    );

    try {
        icon.setImage(
                new Image(
                        getClass().getResource("/assets/img/" + iconName).toExternalForm()
                )
        );
    } catch (Exception e) {
        icon.setImage(null);
    }

    clearError();
    }


    public void clearError() {
        if (errorLabel != null) {
            errorLabel.setText("");
            errorLabel.setVisible(false);
            errorLabel.setManaged(false);
        }
    }

    public void showError(String msg) {
        if (errorLabel != null) {
            errorLabel.setText(msg);
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
        }
    }

}