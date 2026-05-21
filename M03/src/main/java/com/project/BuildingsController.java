package com.project;

import Logic.Civilization;
import Logic.Variables;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BuildingsController {

    @FXML private VBox productionList;
    @FXML private VBox specialList;

    @FXML
    public void initialize() {
        updateBuildings();
    }

    public void updateBuildings() {

        productionList.getChildren().clear();
        specialList.getChildren().clear();

        Civilization civ = GameState.getPlayer();

        // EDIFICIOS DE PRODUCCIÓN
        addSection(productionList, "EDIFICIOS DE PRODUCCIÓN");

        addBuilding(
                productionList,
                "Granja",
                "farm.png",
                civ.farm,
                Variables.FOOD_COST_FARM,
                Variables.WOOD_COST_FARM,
                Variables.IRON_COST_FARM,
                "+" + Variables.CIVILIZATION_FOOD_GENERATED_PER_FARM + " comida/min",
                () -> civ.newFarm()
        );

        addBuilding(
                productionList,
                "Carpintería",
                "carpentry.png",
                civ.carpentry,
                Variables.FOOD_COST_CARPENTRY,
                Variables.WOOD_COST_CARPENTRY,
                Variables.IRON_COST_CARPENTRY,
                "+" + Variables.CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY + " madera/min",
                () -> civ.newCarpentry()
        );

        addBuilding(
                productionList,
                "Herrería",
                "smithy.png",
                civ.smithy,
                Variables.FOOD_COST_SMITHY,
                Variables.WOOD_COST_SMITHY,
                Variables.IRON_COST_SMITHY,
                "+" + Variables.CIVILIZATION_IRON_GENERATED_PER_SMITHY + " hierro/min",
                () -> civ.newSmithy()
        );

        // =========================
        // EDIFICIOS ESPECIALES
        // =========================
        addSection(specialList, "EDIFICIOS ESPECIALES");

        addBuilding(
                specialList,
                "Torre Arcana",
                "mana.png",
                civ.magicTower,
                Variables.FOOD_COST_MAGICTOWER,
                Variables.WOOD_COST_MAGICTOWER,
                Variables.IRON_COST_MAGICTOWER,
                "Genera maná y desbloquea Magos",
                () -> civ.newMagicTower()
        );

        addBuilding(
                specialList,
                "Iglesia",
                "church.png",
                civ.church,
                Variables.FOOD_COST_CHURCH,
                Variables.WOOD_COST_CHURCH,
                Variables.IRON_COST_CHURCH,
                "Desbloquea Sacerdotes",
                () -> civ.newChurch()
        );

    }

    private void addSection(VBox box, String title) {

        Label label = new Label(title);
        label.getStyleClass().add("section-title");

        HBox container = new HBox(label);
        container.getStyleClass().add("section-box");

        box.getChildren().add(container);
    }

    private void addBuilding(
            VBox target,
            String name,
            String icon,
            int level,
            int foodCost,
            int woodCost,
            int ironCost,
            String effect,
            UpgradeAction action
    ) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/assets/buildingItem.fxml"));

            HBox item = loader.load();

            BuildingItemController controller = loader.getController();

            controller.setData(
                    name,
                    icon,
                    level,
                    foodCost,
                    woodCost,
                    ironCost,
                    effect
            );
            controller.upgradeButton.setOnAction(e -> {

                controller.clearError();
                Civilization civ = GameState.getPlayer();

                // Nivel antes de construir
                int before = level;

                try {
                    action.upgrade(); // llama a newFarm(), newCarpentry(), etc.
                } catch (Exception ex) {
                    controller.showError(ex.getMessage());
                    return; // si hay error, no seguimos
                }

                // Nivel después de construir (para verificar que realmente se construyó)
                int after = 0;

                switch (name) {
                    case "Granja": after = civ.farm; break;
                    case "Carpintería": after = civ.carpentry; break;
                    case "Herrería": after = civ.smithy; break;
                    case "Torre Arcana": after = civ.magicTower; break;
                    case "Iglesia": after = civ.church; break;
                }

                // Si no cambió el nivel, algo falló
                if (after == before) {
                    controller.showError("No se pudo construir.");
                    return;
                }

                // Si todo fue bien → refrescar GUI
                updateBuildings();

                ResourcesController resources =
                        (ResourcesController) UtilsViews.getController("Resources");

                if (resources != null) resources.updateResources();

                StatsController stats =
                        (StatsController) UtilsViews.getController("Stats");

                if (stats != null) stats.updateStats();
            });


            target.getChildren().add(item);

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

    @FunctionalInterface
    interface UpgradeAction {
        void upgrade() throws Exception;
    }
}
