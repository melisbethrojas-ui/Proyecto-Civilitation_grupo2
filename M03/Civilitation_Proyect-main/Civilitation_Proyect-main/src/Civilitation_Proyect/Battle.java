package Civilitation_Proyect;

import java.util.ArrayList;
import java.util.Random;

public class Battle implements Variables {

    private ArrayList<MilitaryUnit>[] playerArmy;
    private ArrayList<MilitaryUnit>[] enemyArmy;
    private StringBuilder battleLog;
    private int initialPlayerUnits;
    private int initialEnemyUnits;
    private int wasteWood = 0;
    private int wasteIron = 0;

    public Battle(ArrayList<MilitaryUnit>[] playerArmy, int battleCount) {
        this.playerArmy = playerArmy;
        this.battleLog = new StringBuilder();
        this.enemyArmy = generateEnemyArmy(battleCount);
        this.initialPlayerUnits = countUnits(playerArmy);
        this.initialEnemyUnits = countUnits(enemyArmy);
    }

    // Cuenta el total de unidades vivas en un ejército
    private int countUnits(ArrayList<MilitaryUnit>[] army) {
        int total = 0;
        for (ArrayList<MilitaryUnit> group : army) {
            if (group != null) total += group.size();
        }
        return total;
    }

    // Genera el ejército enemigo basado en los recursos iniciales y el incremento por batalla
    private ArrayList<MilitaryUnit>[] generateEnemyArmy(int battleCount) {
        ArrayList<MilitaryUnit>[] army = new ArrayList[9];
        for (int i = 0; i < 9; i++) army[i] = new ArrayList<>();

        double multiplier = Math.pow(1 + (ENEMY_FLEET_INCREASE / 100.0), battleCount);
        int iron = (int) (IRON_BASE_ENEMY_ARMY * multiplier);
        int wood = (int) (WOOD_BASE_ENEMY_ARMY * multiplier);
        int food = (int) (FOOD_BASE_ENEMY_ARMY * multiplier);

        Random r = new Random();
        // El enemigo solo compra unidades de ataque (índices 0-3: Swordsman, Spearman, Crossbow, Cannon)
        while (true) {
            int type = r.nextInt(4); 
            if (wood >= WOOD_COST_UNITS[type] && iron >= IRON_COST_UNITS[type] && food >= FOOD_COST_UNITS[type]) {
                wood -= WOOD_COST_UNITS[type];
                iron -= IRON_COST_UNITS[type];
                food -= FOOD_COST_UNITS[type];
                
                if (type == 0) army[0].add(new Swordsman());
                else if (type == 1) army[1].add(new Spearman());
                else if (type == 2) army[2].add(new Crossbow());
                else if (type == 3) army[3].add(new Cannon());
            } else {
                break; 
            }
        }
        return army;
    }

    // Método principal que ejecuta la batalla
    public String startBattle() {
        battleLog.append("--- INICIO DE LA BATALLA ---\n");
        
        // La batalla termina si un bando pierde el 80% de sus tropas (queda el 20%)
        while (countUnits(playerArmy) > initialPlayerUnits * 0.2 && 
               countUnits(enemyArmy) > initialEnemyUnits * 0.2) {
            
            battleLog.append("\n>> TURNO DEL JUGADOR\n");
            executeTurn(playerArmy, enemyArmy, "Jugador");
            
            if (countUnits(enemyArmy) > initialEnemyUnits * 0.2) {
                battleLog.append("\n>> TURNO DEL ENEMIGO\n");
                executeTurn(enemyArmy, playerArmy, "Enemigo");
            }
        }
        return finalizeBattle();
    }

    private void executeTurn(ArrayList<MilitaryUnit>[] attackers, ArrayList<MilitaryUnit>[] defenders, String name) {
        for (ArrayList<MilitaryUnit> group : attackers) {
            for (int i = 0; i < group.size(); i++) {
                if (countUnits(defenders) <= 0) return;
                performAttack(group.get(i), defenders, name);
            }
        }
    }

    private void performAttack(MilitaryUnit attacker, ArrayList<MilitaryUnit>[] defenders, String name) {
        int targetGroupIdx = selectTargetGroup(defenders);
        if (targetGroupIdx == -1) return;

        ArrayList<MilitaryUnit> targetGroup = defenders[targetGroupIdx];
        int victimIdx = (int) (Math.random() * targetGroup.size());
        MilitaryUnit victim = targetGroup.get(victimIdx);

        int damage = attacker.attack();
        victim.takeDamage(damage);
        
        battleLog.append(name).append(" ataca con ").append(attacker.getClass().getSimpleName())
                 .append(" a ").append(victim.getClass().getSimpleName())
                 .append(" causando ").append(damage).append(" de daño.\n");

        if (!victim.isAlive()) {
            battleLog.append("¡Unidad enemiga destruida!\n");
            calculateWaste(victim);
            targetGroup.remove(victimIdx);
        } else {
            // Probabilidad de ataque extra
            boolean extraAttack = false;
            if (attacker instanceof AttackUnit) extraAttack = ((AttackUnit) attacker).chanceAttackAgain();
            else if (attacker instanceof DefenseUnit) extraAttack = ((DefenseUnit) attacker).chanceAttackAgain();

            if (extraAttack) {
                battleLog.append("¡ATAQUE EXTRA de ").append(attacker.getClass().getSimpleName()).append("!\n");
                performAttack(attacker, defenders, name);
            }
        }
    }

    // Selección de objetivo basada en la cantidad de unidades de cada grupo (Probabilidad)
    private int selectTargetGroup(ArrayList<MilitaryUnit>[] army) {
        int totalUnits = countUnits(army);
        if (totalUnits == 0) return -1;
        int random = (int) (Math.random() * totalUnits);
        int cumulative = 0;
        for (int i = 0; i < army.length; i++) {
            cumulative += army[i].size();
            if (random < cumulative) return i;
        }
        return -1;
    }

    private void calculateWaste(MilitaryUnit victim) {
        // Se genera el 70% del coste en recursos si se cumple la probabilidad de waste
        boolean generates = false;
        if (victim instanceof AttackUnit) generates = ((AttackUnit) victim).chanceGeneratngWaste();
        else if (victim instanceof DefenseUnit) generates = ((DefenseUnit) victim).chanceGeneratngWaste();

        if (generates) {
            wasteWood += (victim.getWoodCost() * PERCENTATGE_WASTE) / 100;
            wasteIron += (victim.getIronCost() * PERCENTATGE_WASTE) / 100;
        }
    }

    private String finalizeBattle() {
        boolean playerWon = countUnits(playerArmy) > initialPlayerUnits * 0.2;
        
        if (playerWon) {
            battleLog.append("\n¡VICTORIA FINAL!\n");
            // Supervivientes ganan experiencia y recuperan armadura
            for (ArrayList<MilitaryUnit> group : playerArmy) {
                for (MilitaryUnit u : group) {
                    u.setExperience(u.getExperience() + 1);
                    u.resetArmor();
                }
            }
        } else {
            battleLog.append("\nDERROTA. La civilización ha caído.\n");
        }
        
        battleLog.append("Recursos recuperados (Waste): Madera: ").append(wasteWood)
                 .append(", Hierro: ").append(wasteIron).append("\n");
        
        return battleLog.toString();
    }

    public int getWasteWood() { return wasteWood; }
    public int getWasteIron() { return wasteIron; }
}