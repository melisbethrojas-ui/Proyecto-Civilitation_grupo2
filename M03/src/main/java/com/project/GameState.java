package com.project;

import Logic.Civilization;
import Logic.Battle;
import Logic.MilitaryUnit;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameState {

    private static Civilization player;
    private static Civilization enemy;
    private static Battle battle;

    // AMENAZA Y BATALLA AUTOMÁTICA
    private static int threatLevel = 0;
    private static Battle lastAutoBattle;

    public static long gameStartTime;

    private static Timer resourceTimer;
    private static Timer battleTimer;

    // INICIALIZACIÓN DEL JUEGO
    public static void initGame() {

        player = new Civilization();
        enemy = new Civilization();
        battle = null;

        gameStartTime = System.currentTimeMillis();

        startResourceLoop();   // Recursos cada 1 minuto
        startBattleLoop();     // Batalla automática cada 3 minutos
    }

    // GETTERS
    public static Civilization getPlayer() {
        return player;
    }

    public static Civilization getEnemy() {
        return enemy;
    }

    public static Battle getBattle() {
        return battle;
    }

    // AMENAZA
    public static int getThreatLevel() {
        return threatLevel;
    }

    public static void increaseThreat(int amount) {
        threatLevel = Math.min(100, threatLevel + amount);
    }

    public static void decreaseThreat(int amount) {
        threatLevel = Math.max(0, threatLevel - amount);
    }

    // ÚLTIMA BATALLA AUTOMÁTICA

    public static void setLastAutoBattle(Battle b) {
        lastAutoBattle = b;
    }

    public static Battle getLastAutoBattle() {
        return lastAutoBattle;
    }

    // BATALLA MANUAL
    public static void startBattle() {
        ArrayList<MilitaryUnit>[] playerArmy = player.army;
        ArrayList<MilitaryUnit>[] enemyArmy = enemy.army;
        battle = new Battle(playerArmy, enemyArmy);
    }

    // RECURSOS AUTOMÁTICOS CADA 1 MINUTO
    private static void startResourceLoop() {

        resourceTimer = new Timer();

        resourceTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (player != null) {

                    player.generateResources();
                    enemy.generateResources();
                }
            }
        }, 60000, 60000); // 1 minuto
    }

    // BATALLA AUTOMÁTICA CADA 3 MINUTOS
    private static void startBattleLoop() {

        battleTimer = new Timer();

        battleTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (player != null) {

                    // Generar ejército enemigo
                    ArrayList<MilitaryUnit>[] enemyArmy = EnemyGenerator.generateEnemyArmy();

                    // Ejecutar batalla automática
                    Battle autoBattle = new Battle(player.army, enemyArmy);
                    autoBattle.startBattle();

                    // Guardar última batalla automática
                    setLastAutoBattle(autoBattle);

                    // Subir amenaza
                    increaseThreat(10);

                    // Si amenaza muy alta → batalla extra
                    if (getThreatLevel() >= 80) {
                        Battle extra = new Battle(player.army, EnemyGenerator.generateEnemyArmy());
                        extra.startBattle();
                        setLastAutoBattle(extra);
                        increaseThreat(5);
                    }

                    // Actualizar vistas
                    DesktopController desktop = (DesktopController) UtilsViews.getController("Desktop");
                    if (desktop != null) desktop.refresh();

                    StatsController stats = (StatsController) UtilsViews.getController("Stats");
                    if (stats != null) stats.updateStats();

                    UnitsController units = (UnitsController) UtilsViews.getController("Units");
                    if (units != null) units.updateUnits();

                    ResourcesController resources = (ResourcesController) UtilsViews.getController("Resources");
                    if (resources != null) resources.updateResources();
                }
            }
        }, 180000, 180000); // 3 minutos
    }

    // DETENER TIMERS
    public static void stopGameLoop() {
        if (resourceTimer != null) resourceTimer.cancel();
        if (battleTimer != null) battleTimer.cancel();
    }
}
