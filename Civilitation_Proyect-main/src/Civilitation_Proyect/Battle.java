package Civilitation_Proyect;

import java.util.ArrayList;
import java.util.Random;

public class Battle implements Variables {

    // Ejércitos organizados en Arrays de ArrayList de 9 posiciones según Civilization
    private ArrayList<MilitaryUnit>[] civilizationArmy;
    private ArrayList<MilitaryUnit>[] enemyArmy;

    // Registros de unidades iniciales para calcular el 20% de la condición de fin
    private int initialCivUnits;
    private int initialEnemyUnits;

    // Control del desarrollo en texto y números aleatorios
    private StringBuilder battleLog;
    private Random random;

    // Residuos generados recolectables por el ganador
    private int wasteWood;
    private int wasteIron;

    // Contadores de recursos perdidos para decidir el ganador por pérdidas ponderadas
    private int resourcesLostCiv;
    private int resourcesLostEnemy;
    
    // Guardamos explícitamente el resultado final para el reporte
    private String winnerName = "Empate"; 

    @SuppressWarnings("unchecked")
    public Battle(ArrayList<MilitaryUnit>[] playerArmy, ArrayList<MilitaryUnit>[] rivalArmy) {
        // Clonamos la estructura de los arrays para no mutar el cuartel real durante la simulación de bajas
        this.civilizationArmy = new ArrayList[9];
        this.enemyArmy = new ArrayList[9];
        
        for (int i = 0; i < 9; i++) {
            this.civilizationArmy[i] = new ArrayList<>(playerArmy[i]);
            this.enemyArmy[i] = new ArrayList<>(rivalArmy[i]);
        }

        this.battleLog = new StringBuilder();
        this.random = new Random();
        this.wasteWood = 0;
        this.wasteIron = 0;
        this.resourcesLostCiv = 0;
        this.resourcesLostEnemy = 0;

        // Calculamos el total de unidades iniciales
        this.initialCivUnits = countTotalUnits(this.civilizationArmy);
        this.initialEnemyUnits = countTotalUnits(this.enemyArmy);
    }

    public void startBattle() {
        battleLog.append("--- INICIO DE LA BATALLA ---\n");
        battleLog.append("Unidades iniciales Civilización: ").append(initialCivUnits).append("\n");
        battleLog.append("Unidades iniciales Enemigo: ").append(initialEnemyUnits).append("\n\n");

        int turn = 1;

        // REQUISITO 4: El bucle se detiene si un bando cae al 20% o menos de sus tropas iniciales
        while (countTotalUnits(civilizationArmy) > (initialCivUnits * 0.2) && 
               countTotalUnits(enemyArmy) > (initialEnemyUnits * 0.2)) {
            
            battleLog.append("=== TURNO ").append(turn).append(" ===\n");

            // 1. ATACA LA CIVILIZACIÓN
            executeTurn(civilizationArmy, enemyArmy, "Civilización", "Enemigo", CHANCE_ATTACK_CIVILIZATION_UNITS);

            // Verificar si el enemigo se retira/rinde tras el ataque de la civilización
            if (countTotalUnits(enemyArmy) <= (initialEnemyUnits * 0.2)) break;

            // 2. ATACA EL ENEMIGO
            executeTurn(enemyArmy, civilizationArmy, "Enemigo", "Civilización", CHANCE_ATTACK_ENEMY_UNITS);

            turn++;
            if (turn > 500) {
                battleLog.append("La batalla se ha estancado tras 500 asaltos.\n");
                break;
            }
        }

        battleLog.append("\n--- FIN DE LA BATALLA ---\n");
        battleLog.append("Valor ponderado de pérdidas de la Civilización: ").append(resourcesLostCiv).append("\n");
        battleLog.append("Valor ponderado de pérdidas del Enemigo: ").append(resourcesLostEnemy).append("\n");

        // REQUISITO 5: Evaluar ganador según el bando que haya perdido MENOS valor de recursos ponderado
        if (resourcesLostCiv < resourcesLostEnemy) {
            this.winnerName = "Civilización";
            battleLog.append("¡VICTORIA! Tu civilización sufrió menos pérdidas económicas y gana la batalla.\n");
            // Aplicar experiencia a los supervivientes que quedan en el array interno
            for (ArrayList<MilitaryUnit> group : civilizationArmy) {
                for (MilitaryUnit unit : group) {
                    unit.setExperience(unit.getExperience() + 1);
                }
            }
        } else if (resourcesLostEnemy < resourcesLostCiv) {
            this.winnerName = "Enemigo";
            battleLog.append("¡DERROTA! El enemigo causó un daño estratégico devastador y se proclama vencedor.\n");
        } else {
            this.winnerName = "Empate";
            battleLog.append("¡EMPATE TÉCNICO! Ambas facciones perdieron exactamente el mismo capital militar.\n");
        }
    }

    private void executeTurn(ArrayList<MilitaryUnit>[] attackers, ArrayList<MilitaryUnit>[] defenders, 
                             String attackerName, String defenderName, int[] probabilities) {
        
        // REQUISITO 2: Seleccionar el GRUPO ATACANTE usando los porcentajes fijos del PDF
        int attackerGroupIdx = selectGroupBasedOnProbability(attackers, probabilities);
        if (attackerGroupIdx == -1) return; // No hay unidades atacantes disponibles

        MilitaryUnit attacker = attackers[attackerGroupIdx].get(random.nextInt(attackers[attackerGroupIdx].size()));
        boolean attackAgain = true;

        while (attackAgain && countTotalUnits(defenders) > 0) {
            // REQUISITO 3: Seleccionar el GRUPO DEFENSOR proporcionalmente a la cantidad de tropas vivas
            int defenderGroupIdx = selectDefenderGroupProportionally(defenders);
            if (defenderGroupIdx == -1) break;

            int targetIdx = random.nextInt(defenders[defenderGroupIdx].size());
            MilitaryUnit defender = defenders[defenderGroupIdx].get(targetIdx);

            int damage = attacker.attack();
            
            if (damage > 0) {
                defender.takeDamage(damage);
                battleLog.append("[").append(attackerName).append("] ")
                         .append(attacker.getClass().getSimpleName())
                         .append(" inflige ").append(damage).append(" de daño a ")
                         .append(defender.getClass().getSimpleName()).append(" ").append(defenderName)
                         .append(" (Armadura actual: ").append(Math.max(0, defender.getActualArmor())).append(")\n");

                // Comprobar si la unidad defensora muere
                if (defender.getActualArmor() <= 0) {
                    battleLog.append("   -> ¡¡ ").append(defender.getClass().getSimpleName()).append(" destruido !!\n");
                    
                    // CORRECCIÓN CRÍTICA: Fórmula matemática exacta del PDF para recursos ponderados:
                    // Valor = Hierro + (Madera / 5) + (Comida / 10)
                    int weightedUnitValue = defender.getIronCost() + 
                                            (defender.getWoodCost() / 5) + 
                                            (defender.getFoodCost() / 10);
                    
                    if (defenderName.equals("Civilización")) {
                        resourcesLostCiv += weightedUnitValue;
                    } else {
                        resourcesLostEnemy += weightedUnitValue;
                    }

                    // Calcular escombros/chatarra
                    calculateWaste(defender);

                    // Eliminar físicamente del sub-ejército
                    defenders[defenderGroupIdx].remove(targetIdx);
                }
            }

            // Validar repetición de ataque por habilidad de velocidad
            int roll = random.nextInt(100);
            attackAgain = roll < attacker.getChanceAttackAgain();
            if (attackAgain && countTotalUnits(defenders) > 0) {
                battleLog.append("   (Habilidad: El ").append(attacker.getClass().getSimpleName()).append(" arremete de nuevo consecutivamente)\n");
            }
        }
    }

    private int selectGroupBasedOnProbability(ArrayList<MilitaryUnit>[] army, int[] probabilities) {
        int roll = random.nextInt(100);
        int sum = 0;
        for (int i = 0; i < probabilities.length; i++) {
            sum += probabilities[i];
            if (roll < sum) {
                if (!army[i].isEmpty()) return i;
            }
        }
        for (int i = 0; i < army.length; i++) {
            if (!army[i].isEmpty()) return i;
        }
        return -1;
    }

    private int selectDefenderGroupProportionally(ArrayList<MilitaryUnit>[] defenders) {
        int totalAlive = countTotalUnits(defenders);
        if (totalAlive == 0) return -1;

        int roll = random.nextInt(totalAlive);
        int accumulatedUnits = 0;

        for (int i = 0; i < defenders.length; i++) {
            accumulatedUnits += defenders[i].size();
            if (roll < accumulatedUnits) {
                return i;
            }
        }
        return -1;
    }

    private void calculateWaste(MilitaryUnit deadUnit) {
        int roll = random.nextInt(100);
        if (roll < deadUnit.getChanceGeneratinWaste()) {
            int woodRecovered = (deadUnit.getWoodCost() * PERCENTATGE_WASTE) / 100;
            int ironRecovered = (deadUnit.getIronCost() * PERCENTATGE_WASTE) / 100;
            
            this.wasteWood += woodRecovered;
            this.wasteIron += ironRecovered;
            
            battleLog.append("   [CAMPO] Escombros rescatados: +").append(woodRecovered)
                     .append(" madera, +").append(ironRecovered).append(" hierro.\n");
        }
    }

    private int countTotalUnits(ArrayList<MilitaryUnit>[] army) {
        int count = 0;
        for (ArrayList<MilitaryUnit> group : army) {
            count += group.size();
        }
        return count;
    }

    // --- REQUISITO DEL ENUNCIADO PARA EL HISTORIAL ---
    // Devuelve el reporte formateado de la batalla actual indicando el número correlativo que lleva la civilización
    public String getBattleReport(int battlesCount) {
        StringBuilder report = new StringBuilder();
        report.append("=========================================\n");
        report.append("REPORT DE BATALLA Nº ").append(battlesCount).append("\n");
        report.append("=========================================\n");
        report.append("GANADOR: ").append(this.winnerName).append("\n");
        report.append("Tropas iniciales: Jugador: ").append(initialCivUnits).append(" | Enemigo: ").append(initialEnemyUnits).append("\n");
        report.append("Tropas restantes: Jugador: ").append(countTotalUnits(civilizationArmy)).append(" | Enemigo: ").append(countTotalUnits(enemyArmy)).append("\n");
        report.append("Pérdidas ponderadas Jugador: ").append(resourcesLostCiv).append("\n");
        report.append("Pérdidas ponderadas Enemigo: ").append(resourcesLostEnemy).append("\n");
        report.append("Escombros del campo de batalla: Madera: ").append(wasteWood).append(" | Hierro: ").append(wasteIron).append("\n");
        report.append("=========================================\n");
        return report.toString();
    }

    // --- GETTERS ---
    public String getBattleDevelopment() { return this.battleLog.toString(); }
    public int getWasteWood() { return this.wasteWood; }
    public int getWasteIron() { return this.wasteIron; }
    public ArrayList<MilitaryUnit>[] getCivilizationArmy() { return civilizationArmy; }
    public ArrayList<MilitaryUnit>[] getEnemyArmy() { return enemyArmy; }
}