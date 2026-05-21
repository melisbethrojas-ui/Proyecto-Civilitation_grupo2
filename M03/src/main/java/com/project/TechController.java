package com.project;

import Logic.Civilization;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TechController {

    @FXML
    private VBox techList;

    public void initialize() {
        updateTech();
    }

    public void updateTech() {

        techList.getChildren().clear();

        Civilization civ = GameState.getPlayer();

        addTechItem(
                "Mejorar Ataque",
                "attack.png",
                civ.isAttackTechResearched(),
                civ.getAttackTechCost()
        );

        addTechItem(
                "Mejorar Defensa",
                "defense.png",
                civ.isDefenseTechResearched(),
                civ.getDefenseTechCost()
        );
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

    private void addTechItem(String name, String iconName, boolean researched, int cost) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/assets/techItem.fxml")
            );

            HBox item = loader.load();
            TechItemController controller = loader.getController();

            controller.setData(name, iconName, researched, cost);

            techList.getChildren().add(item);

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

}
