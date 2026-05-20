package com.project;

import Logic.Civilization;
import Logic.Battle;
import Logic.MilitaryUnit;
import java.util.ArrayList;
import java.util.Random;

public class GameState {

    private static Civilization player;
    private static Civilization enemy;
    private static Battle battle;

    // 🔥 NUEVO: amenaza simulada
    private static int nextThreatMinutes = 5;

    public static void initGame() {
        player = new Civilization();
        enemy = new Civilization();
        battle = null;

        generateNextThreat();
    }

    public static Civilization getPlayer() {
        return player;
    }

    public static Civilization getEnemy() {
        return enemy;
    }

    public static Battle getBattle() {
        return battle;
    }

    public static void startBattle() {
        ArrayList<MilitaryUnit>[] playerArmy = player.getArmy();
        ArrayList<MilitaryUnit>[] enemyArmy = enemy.getArmy();
        battle = new Battle(playerArmy, enemyArmy);
    }

    // =========================
    // 🔥 AMENAZAS
    // =========================

    public static int getNextThreat() {
        return nextThreatMinutes;
    }

    public static void generateNextThreat() {
        Random r = new Random();
        nextThreatMinutes = 3 + r.nextInt(8); // 3 a 10 minutos
    }

    public static void advanceThreatTimer() {
        nextThreatMinutes--;
        if (nextThreatMinutes <= 0) {
            startBattle();      // o evento futuro
            generateNextThreat();
        }
    }
}