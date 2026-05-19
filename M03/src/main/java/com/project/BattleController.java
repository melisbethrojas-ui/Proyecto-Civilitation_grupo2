package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class BattleController {

    @FXML
    private Label resultLabel;

    @FXML
    private TextArea logArea;

    @FXML
    public void initialize() {
        resultLabel.setText("Aún no se ha simulado ninguna batalla.");
        logArea.setText("");

        logArea.getStyleClass().add("log-area");
    }

    @FXML
    private void simulateBattle() {

        // Limpia el log
        logArea.setText("");
        resultLabel.setText("Simulando...");

        // EJEMPLO (luego lo conectamos con tu clase Battle real)
        logArea.appendText("Turno 1:\n");
        logArea.appendText("- Espadachín golpea a Orco por 10\n");
        logArea.appendText("- Orco golpea a Arquero por 12\n\n");

        logArea.appendText("Turno 2:\n");
        logArea.appendText("- Arquero elimina a Orco\n\n");

        resultLabel.setText("Resultado: Victoria");
    }

    @FXML
    private void goBack() {
        UtilsViews.setViewAnimating("Desktop");
    }
}
