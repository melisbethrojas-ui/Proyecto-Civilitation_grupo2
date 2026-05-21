package com.project;

import Logic.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class UnitsController {

    @FXML private VBox attackList;
    @FXML private VBox defenseList;
    @FXML private VBox specialList;

    @FXML
    public void initialize() {
        updateUnits();
        ResourcesController rc =
            (ResourcesController) UtilsViews.getController("Resources");
        if (rc != null) rc.updateResources();

        updateUnits();
    }

    public void updateUnits() {

        attackList.getChildren().clear();
        defenseList.getChildren().clear();
        specialList.getChildren().clear();

        Civilization civ = GameState.getPlayer();
        ArrayList<MilitaryUnit>[] army = civ.army;

        // UNIDADES DE ATAQUE
        addSection(attackList, "UNIDADES DE ATAQUE");

        addUnit(attackList, "Swordsman", "swordsman.png", army[0], 0, civ::newSwordsman);
        addUnit(attackList, "Spearman", "spear.png", army[1], 1, civ::newSpearman);
        addUnit(attackList, "Crossbow", "crossbow.png", army[2], 2, civ::newCrossbow);
        addUnit(attackList, "Cannon", "cannon.png", army[3], 3, civ::newCannon);

        // UNIDADES DE DEFENSA
        addSection(defenseList, "UNIDADES DE DEFENSA");

        addUnit(defenseList, "Arrow Tower", "arrow_tower.png", army[4], 4, civ::newArrowTower);
        addUnit(defenseList, "Catapult", "catapult_tower.png", army[5], 5, civ::newCatapult);
        addUnit(defenseList, "Rocket Launcher Tower", "rocket_tower.png", army[6], 6, civ::newRocketLauncher);

        // UNIDADES ESPECIALES
        addSection(specialList, "UNIDADES ESPECIALES");

        addUnit(specialList, "Magician", "magician.png", army[7], 7, civ::newMagician);
        addUnit(specialList, "Priest", "priest.png", army[8], 8, civ::newPriest);
    }

    private void addSection(VBox box, String title) {

        Label label = new Label(title);
        label.getStyleClass().add("section-title");

        HBox container = new HBox(label);
        container.getStyleClass().add("section-box");

        box.getChildren().add(container);
    }

    private void addUnit(
            VBox targetList,
            String name,
            String iconName,
            ArrayList<MilitaryUnit> list,
            int index,
            RecruitAction action
    ) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/assets/unitItem.fxml")
            );

            HBox item = loader.load();

            UnitItemController controller = loader.getController();

            int count = list.size();

            int atk = 0;
            int armor = 0;
            
            Civilization civ = GameState.getPlayer();

            if (count > 0) {
                MilitaryUnit unit = list.get(0);
                atk = unit.attack() + civ.getTechnologyAttack();
                armor = unit.getActualArmor() + civ.getTechnologyDefense();

            }

            int manaCost = 0;
            if (index == 7) manaCost = Variables.MANA_COST_MAGICIAN;
            if (index == 8) manaCost = Variables.MANA_COST_PRIEST;

            controller.setData(
                    name,
                    iconName,
                    atk,
                    armor,
                    Variables.FOOD_COST_UNITS[index],
                    Variables.WOOD_COST_UNITS[index],
                    Variables.IRON_COST_UNITS[index],
                    manaCost,
                    count
            );

            controller.btn1.setOnAction(e -> tryRecruit(controller, action, 1));
            controller.btn5.setOnAction(e -> tryRecruit(controller, action, 5));
            controller.btn10.setOnAction(e -> tryRecruit(controller, action, 10));

            targetList.getChildren().add(item);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // RECLUTAMIENTO
    private void tryRecruit(UnitItemController controller, RecruitAction action, int n) {

        controller.clearError();

        try {
            action.recruit(n);
        } catch (ResourceException | BuildingException e) {
            controller.showError(e.getMessage());
            return; // ⭐ NO refrescar si hubo error
        }

        // refrescar si el reclutamiento fue exitoso
        updateUnits();

        StatsController stats =
                (StatsController) UtilsViews.getController("Stats");
        if (stats != null) stats.updateStats();

        ResourcesController resources =
                (ResourcesController) UtilsViews.getController("Resources");
        if (resources != null) resources.updateResources();

        DesktopController desktop =
                (DesktopController) UtilsViews.getController("Desktop");
        if (desktop != null) desktop.refresh();
    }

    @FXML
    private void goBack() {
        UtilsViews.setViewAnimating("Desktop");

        DesktopController desktop =
                (DesktopController) UtilsViews.getController("Desktop");

        if (desktop != null) desktop.refresh();
    }

    @FunctionalInterface
    interface RecruitAction {
        void recruit(int n) throws ResourceException, BuildingException;
    }
}
