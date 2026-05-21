package com.project;

import Logic.Battle;
import Logic.Civilization;
import Logic.MilitaryUnit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class BattleController {

    @FXML private VBox civList;
    @FXML private VBox enemyList;

    @FXML private Label civUnitsLabel;
    @FXML private Label enemyUnitsLabel;

    @FXML private Label winnerLabel;
    @FXML private Label wasteLabel;

    @FXML private TextArea logArea;

    @FXML private Button startButton;

    private ArrayList<MilitaryUnit>[] enemyArmy;

   @FXML
    public void initialize() {

    // Inicializar arrays vacíos para evitar null
    enemyArmy = new ArrayList[9];
    for (int i = 0; i < 9; i++) {
        enemyArmy[i] = new ArrayList<>();
    }

    generateEnemy();
    updateArmyLabels();
    loadInitialArmyLists();

    Battle auto = GameState.getLastAutoBattle();
    if (auto != null) {
        logArea.setText(auto.getBattleDevelopment());
    }

    }


    private void generateEnemy() {
        enemyArmy = EnemyGenerator.generateEnemyArmy();
    }

    private void updateArmyLabels() {
        Civilization civ = GameState.getPlayer();

        civUnitsLabel.setText("Tus tropas: " + countUnits(civ.army));
        enemyUnitsLabel.setText("Tropas enemigas: " + countUnits(enemyArmy));
    }

    private int countUnits(ArrayList<MilitaryUnit>[] army) {
        int total = 0;
        for (ArrayList<MilitaryUnit> group : army) total += group.size();
        return total;
    }

    private void loadInitialArmyLists() {
        civList.getChildren().clear();
        enemyList.getChildren().clear();

        Civilization civ = GameState.getPlayer();

        addArmyToList(civList, civ.army, civ.army);
        addArmyToList(enemyList, enemyArmy, enemyArmy);
    }

    @FXML
    private void startBattle() {

        Civilization civ = GameState.getPlayer();

        Battle battle = new Battle(civ.army, enemyArmy);
        battle.startBattle();

        winnerLabel.setText("Ganador: " + battle.getBattleReport(1).split("\n")[2]);

        wasteLabel.setText("Escombros: Madera " + battle.getWasteWood() +
                " | Hierro " + battle.getWasteIron());

        logArea.setText(battle.getBattleDevelopment());

        civList.getChildren().clear();
        enemyList.getChildren().clear();

        addArmyToList(civList, civ.army, battle.getCivilizationArmy());
        addArmyToList(enemyList, enemyArmy, battle.getEnemyArmy());

        refreshAll();
    }

    // ============================================================
    //  NUEVO: MAPEO MANUAL DE ICONOS (SIN JSON, SIN DEFAULT)
    // ============================================================

    private String getIconForUnit(String unitName) {
        return switch (unitName) {
            case "Swordsman" -> "swordsman.png";
            case "Spearman" -> "spear.png";
            case "Crossbow" -> "crossbow.png";
            case "Cannon" -> "cannon.png";
            case "Catapult" -> "catapult_tower.png";
            case "ArrowTower" -> "catapult_tower.png";
            case "RocketLauncherTower" -> "rocket_tower.png";
            case "Magician" -> "magician.png";
            case "Priest" -> "priest.png";
            default -> null;
        };
    }

    // ============================================================
    //  NUEVO: addUnitBattle (igual que Units pero adaptado)
    // ============================================================

    private void addUnitBattle(
            VBox list,
            String name,
            String iconName,
            int initialCount,
            int finalCount,
            int atk,
            int def
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/assets/battleUnitItem.fxml")
            );

            HBox item = loader.load();
            BattleUnitItemController controller = loader.getController();

            controller.setData(name, iconName, initialCount, finalCount, atk, def);

            list.getChildren().add(item);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    //  ADAPTADO: addArmyToList usando iconos manuales
    // ============================================================

    private void addArmyToList(VBox list,
                               ArrayList<MilitaryUnit>[] initial,
                               ArrayList<MilitaryUnit>[] finalArmy) {

        for (int i = 0; i < 9; i++) {

            int initialCount = initial[i].size();
            int finalCount = finalArmy[i].size();

            if (initialCount == 0 && finalCount == 0) continue;

            String name;
            if (initialCount > 0) {
                name = initial[i].get(0).getClass().getSimpleName();
            } else {
                name = finalArmy[i].get(0).getClass().getSimpleName();
            }

            String icon = getIconForUnit(name);

            int atk = 0;
            int def = 0;

            if (initialCount > 0) {
                atk = initial[i].get(0).attack();
                def = initial[i].get(0).getActualArmor();
            }

            addUnitBattle(list, name, icon, initialCount, finalCount, atk, def);
        }
    }

    private void refreshAll() {

        DesktopController desktop =
                (DesktopController) UtilsViews.getController("Desktop");
        if (desktop != null) desktop.refresh();

        StatsController stats =
                (StatsController) UtilsViews.getController("Stats");
        if (stats != null) stats.updateStats();

        UnitsController units =
                (UnitsController) UtilsViews.getController("Units");
        if (units != null) units.updateUnits();

        ResourcesController resources =
                (ResourcesController) UtilsViews.getController("Resources");
        if (resources != null) resources.updateResources();
    }

    @FXML
    private void goBack() {
        UtilsViews.setViewAnimating("Desktop");
    }
}
