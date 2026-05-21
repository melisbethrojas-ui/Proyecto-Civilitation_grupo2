package com.project;

import Logic.Civilization;
import Logic.Variables;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class StatsController {

    @FXML private VBox statsBox;

    @FXML
    public void initialize() {
        updateStats();
    }

    public void updateStats() {

        statsBox.getChildren().clear();

        Civilization civ = GameState.getPlayer();

        // =========================
        // TECNOLOGÍA
        // =========================

        addSection("TECNOLOGÍA");

        addStat("Ataque: " + civ.getTechnologyAttack());
        addStat("Defensa: " + civ.getTechnologyDefense());

        // =========================
        // EDIFICIOS
        // =========================

        addSection("EDIFICIOS");

        addStat("Granjas: " + civ.farm);
        addStat("Herrerías: " + civ.smithy);
        addStat("Carpinterías: " + civ.carpentry);
        addStat("Torres Mágicas: " + civ.magicTower);
        addStat("Iglesias: " + civ.church);

        // =========================
        // UNIDADES DEFENSIVAS
        // =========================

        addSection("UNIDADES DEFENSIVAS");

        addStat("Torre de Flechas: " + civ.army[4].size());
        addStat("Catapultas: " + civ.army[5].size());
        addStat("Lanzacohetes: " + civ.army[6].size());

        // =========================
        // UNIDADES DE ATAQUE
        // =========================

        addSection("UNIDADES DE ATAQUE");

        addStat("Espadachines: " + civ.army[0].size());
        addStat("Lancero: " + civ.army[1].size());
        addStat("Ballesteros: " + civ.army[2].size());
        addStat("Cañones: " + civ.army[3].size());

        // =========================
        // UNIDADES ESPECIALES
        // =========================

        addSection("UNIDADES ESPECIALES");

        addStat("Magos: " + civ.army[7].size());
        addStat("Sacerdotes: " + civ.army[8].size());

        // =========================
        // RECURSOS (LEÍDOS DESDE RESOURCES)
        // =========================

        addSection("RECURSOS");

        ResourcesController rc =
                (ResourcesController) UtilsViews.getController("Resources");

        if (rc != null) {
            addStat("Comida: " + rc.getDisplayedFood());
            addStat("Madera: " + rc.getDisplayedWood());
            addStat("Hierro: " + rc.getDisplayedIron());
            addStat("Maná: " + rc.getDisplayedMana());
        } else {
            addStat("Comida: " + civ.food);
            addStat("Madera: " + civ.wood);
            addStat("Hierro: " + civ.iron);
            addStat("Maná: " + civ.mana);
        }

        // =========================
        // GENERACIÓN DE RECURSOS (/min)
        // =========================

        addSection("GENERACIÓN DE RECURSOS (/min)");

        int foodPerMin = Variables.CIVILIZATION_FOOD_GENERATED +
                civ.farm * Variables.CIVILIZATION_FOOD_GENERATED_PER_FARM;

        int woodPerMin = Variables.CIVILIZATION_WOOD_GENERATED +
                civ.carpentry * Variables.CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY;

        int ironPerMin = Variables.CIVILIZATION_IRON_GENERATED +
                civ.smithy * Variables.CIVILIZATION_IRON_GENERATED_PER_SMITHY;

        int manaPerMin = civ.magicTower * Variables.CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER;

        addStat("Comida/min: " + foodPerMin);
        addStat("Madera/min: " + woodPerMin);
        addStat("Hierro/min: " + ironPerMin);
        addStat("Maná/min: " + manaPerMin);
    }

    // =========================
    // MÉTODOS DE UI
    // =========================

    private void addSection(String title) {

        Label label = new Label(title);
        label.getStyleClass().add("section-title");

        HBox box = new HBox(label);
        box.getStyleClass().add("section-box");

        statsBox.getChildren().add(box);
    }

    private void addStat(String text) {

        Label label = new Label(text);
        label.getStyleClass().add("item-label");

        HBox box = new HBox(label);
        box.getStyleClass().add("list-item");

        statsBox.getChildren().add(box);
    }

    @FXML
    private void goBack() {
        UtilsViews.setViewAnimating("Desktop");

        DesktopController desktop =
                (DesktopController) UtilsViews.getController("Desktop");

        if (desktop != null) desktop.refresh();
    }

}
