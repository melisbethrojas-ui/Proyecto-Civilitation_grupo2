package com.project;

import Logic.Civilization;
import Logic.Variables;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class ResourcesController {

    @FXML private VBox resourcesList;

    @FXML
    public void initialize() {
        updateResources();

        // Actualización automática cada minuto
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    javafx.application.Platform.runLater(() -> updateResources());
                }
            },
            60000,
            60000
        );
    }

    public void updateResources() {

        resourcesList.getChildren().clear();

        Civilization civ = GameState.getPlayer();

        int baseFood = Variables.CIVILIZATION_FOOD_GENERATED;
        int baseWood = Variables.CIVILIZATION_WOOD_GENERATED;
        int baseIron = Variables.CIVILIZATION_IRON_GENERATED;

        // mostrar recursos reales
        addSection("CURRENT RESOURCES");

        addResource("Food", "banquet.png", civ.food);
        addResource("Wood", "wood.png", civ.wood);
        addResource("Iron", "iron.png", civ.iron);
        addResource("Mana", "mana.png", civ.mana);

        // Mostrar generación real por minuto
        addSection("RESOURCE GENERATION (/min)");

        addStat("Food/min: " + (baseFood + civ.farm * Variables.CIVILIZATION_FOOD_GENERATED_PER_FARM));
        addStat("Wood/min: " + (baseWood + civ.carpentry * Variables.CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY));
        addStat("Iron/min: " + (baseIron + civ.smithy * Variables.CIVILIZATION_IRON_GENERATED_PER_SMITHY));
        addStat("Mana/min: " + (civ.magicTower * Variables.CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER));
    }

    private void addSection(String title) {
        Label label = new Label(title);
        label.getStyleClass().add("section-title");

        HBox box = new HBox(label);
        box.getStyleClass().add("section-box");

        resourcesList.getChildren().add(box);
    }

    private void addStat(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("item-label");

        HBox box = new HBox(label);
        box.getStyleClass().add("list-item");

        resourcesList.getChildren().add(box);
    }

    private void addResource(String name, String icon, int amount) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/assets/resourceItem.fxml")
            );

            HBox item = loader.load();

            ResourceItemController c = loader.getController();
            c.setData(name, icon, amount);

            resourcesList.getChildren().add(item);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() {
        UtilsViews.setViewAnimating("Desktop");

        DesktopController desktop =
                (DesktopController) UtilsViews.getController("Desktop");

        if (desktop != null) desktop.refresh();
    }

    // Getters corregidos: SIEMPRE devuelven recursos reales
    public int getDisplayedFood() {
        return GameState.getPlayer().food;
    }

    public int getDisplayedWood() {
        return GameState.getPlayer().wood;
    }

    public int getDisplayedIron() {
        return GameState.getPlayer().iron;
    }

    public int getDisplayedMana() {
        return GameState.getPlayer().mana;
    }
}
