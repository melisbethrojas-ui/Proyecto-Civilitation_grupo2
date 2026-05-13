package src.Logic;

import java.util.ArrayList;
import java.util.Random;

import src.Interfaces.MilitaryUnit;
import src.Units.Ataque.Cannon;
import src.Units.Ataque.Crossbow;
import src.Units.Ataque.Spearman;
import src.Units.Ataque.Swordsman;
import src.Units.Defensa.ArrowTower;
import src.Units.Defensa.Catapult;
import src.Units.Defensa.RocketLauncherTower;
import src.Units.Especial.Magician;
import src.Units.Especial.Priest;


public class Battle {

    private Civilization civ;
    private ArrayList<MilitaryUnit>[] enemyArmy;
    private Random rnd = new Random();

    @SuppressWarnings("unchecked")
    public Battle(Civilization civ) {
        this.civ = civ;

        // Crear ejército enemigo
        enemyArmy = new ArrayList[9];
        for (int i = 0; i < enemyArmy.length; i++) {
            enemyArmy[i] = new ArrayList<>();
        }

        generateEnemyArmy();
    }

    // ============================
    //   CREAR EJÉRCITO ENEMIGO
    // ============================

    private void generateEnemyArmy() {

        int n = civ.battles + 1; // dificultad creciente

        for (int i = 0; i < n; i++) enemyArmy[0].add(new Swordsman());
        for (int i = 0; i < n; i++) enemyArmy[1].add(new Spearman());
        for (int i = 0; i < n; i++) enemyArmy[2].add(new Crossbow());
        for (int i = 0; i < n; i++) enemyArmy[3].add(new Cannon());

        for (int i = 0; i < n; i++) enemyArmy[4].add(new ArrowTower());
        for (int i = 0; i < n; i++) enemyArmy[5].add(new Catapult());
        for (int i = 0; i < n; i++) enemyArmy[6].add(new RocketLauncherTower());

        for (int i = 0; i < n; i++) enemyArmy[7].add(new Magician(i, i));
        for (int i = 0; i < n; i++) enemyArmy[8].add(new Priest(i, i));
    }

    // ============================
    //   SELECCIÓN DE UNIDADES
    // ============================

    private MilitaryUnit getRandomUnit(ArrayList<MilitaryUnit>[] army) {
        while (true) {
            int type = rnd.nextInt(army.length);
            if (army[type].size() > 0) {
                int pos = rnd.nextInt(army[type].size());
                return army[type].get(pos);
            }
        }
    }

    private void removeDead(ArrayList<MilitaryUnit>[] army) {
        for (ArrayList<MilitaryUnit> list : army) {
            list.removeIf(u -> !u.isAlive());
        }
    }

    private boolean armyAlive(ArrayList<MilitaryUnit>[] army) {
        for (ArrayList<MilitaryUnit> list : army) {
            if (list.size() > 0) return true;
        }
        return false;
    }

    // ============================
    //   RESURRECCIÓN (PRIEST)
    // ============================

    private void resurrect(ArrayList<MilitaryUnit>[] army) {
        for (MilitaryUnit u : army[8]) { // Priest
            if (rnd.nextInt(100) < 20) { // 20% resurrección
                for (int i = 0; i < army.length; i++) {
                    if (army[i].size() > 0) {
                        army[i].get(0).resetArmor();
                        break;
                    }
                }
            }
        }
    }

    // ============================
    //   ATAQUE DOBLE
    // ============================

    private boolean attackAgain(MilitaryUnit u) {
        return rnd.nextInt(100) < u.getChanceAttackAgain();
    }

    // ============================
    //   GENERAR RESIDUOS
    // ============================

    private void generateWaste(MilitaryUnit u, Civilization civ) {
        if (rnd.nextInt(100) < u.getChanceGeneratingWaste()) {
            civ.wood += 1;
        }
    }

    // ============================
    //   BATALLA COMPLETA
    // ============================

    public boolean startBattle() {

        System.out.println("=== COMIENZA LA BATALLA ===");

        while (armyAlive(civ.army) && armyAlive(enemyArmy)) {

            // --- ATAQUE JUGADOR → ENEMIGO ---
            MilitaryUnit attacker = getRandomUnit(civ.army);
            MilitaryUnit defender = getRandomUnit(enemyArmy);

            defender.takeDamage(attacker.attack());
            generateWaste(attacker, civ);

            if (attackAgain(attacker)) {
                defender.takeDamage(attacker.attack());
            }

            removeDead(enemyArmy);

            if (!armyAlive(enemyArmy)) break;

            // --- ATAQUE ENEMIGO → JUGADOR ---
            MilitaryUnit eAttacker = getRandomUnit(enemyArmy);
            MilitaryUnit eDefender = getRandomUnit(civ.army);

            eDefender.takeDamage(eAttacker.attack());
            generateWaste(eAttacker, civ);

            if (attackAgain(eAttacker)) {
                eDefender.takeDamage(eAttacker.attack());
            }

            removeDead(civ.army);

            // --- RESURRECCIÓN ---
            resurrect(civ.army);
            resurrect(enemyArmy);
        }

        boolean win = armyAlive(civ.army);
        if (win) civ.battles++;

        System.out.println(win ? "¡Victoria!" : "Derrota...");
        return win;
    }
}
