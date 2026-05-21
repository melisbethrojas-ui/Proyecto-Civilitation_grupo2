package com.project;

import Logic.Civilization;
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
    private boolean researched;

    public void setData(String name, String iconName, boolean researched, int cost) {

        this.techName = name;
        this.researched = researched;

        nameLabel.setText(name);

        icon.setImage(new Image(
                getClass().getResource("/assets/img/" + iconName).toExternalForm()
        ));

        if (researched) {
            statusLabel.setText("Estado: Investigada");
            statusLabel.getStyleClass().remove("danger-text");
            actionButton.setText("Completado");
            actionButton.setDisable(true);   // ⭐ BLOQUEAR BOTÓN
        } else {
            statusLabel.setText("Estado: No investigada");
            actionButton.setText("Investigar");
            actionButton.setDisable(false);
        }

        costLabel.setText("Coste: " + cost + " hierro");
    }

    @FXML
    private void onAction() {

        Civilization civ = GameState.getPlayer();

        try {
            if (techName.contains("Ataque")) {
                civ.researchAttackTech();
            } else {
                civ.researchDefenseTech();
            }
        } catch (Exception e) {
            statusLabel.setText(e.getMessage());
            statusLabel.getStyleClass().add("danger-text");
            return;
        }

        // ⭐ ACTUALIZAR ESTADO DEL ITEM
        statusLabel.setText("Estado: Investigada");
        actionButton.setText("Completado");
        actionButton.setDisable(true);

        // ⭐ REFRESCAR TODA LA GUI
        TechController tech =
                (TechController) UtilsViews.getController("Tech");
        if (tech != null) tech.updateTech();

        ResourcesController resources =
                (ResourcesController) UtilsViews.getController("Resources");
        if (resources != null) resources.updateResources();

        StatsController stats =
                (StatsController) UtilsViews.getController("Stats");
        if (stats != null) stats.updateStats();

        DesktopController desktop =
                (DesktopController) UtilsViews.getController("Desktop");
        if (desktop != null) desktop.refresh();
    }
}
