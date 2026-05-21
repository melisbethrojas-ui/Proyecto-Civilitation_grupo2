package Civilitation_Proyect;

import java.util.ArrayList;
import java.util.Random;

public class Battle implements Variables {

    // Ejércitos organizados en Arrays de ArrayList de 9 posiciones
    private ArrayList<MilitaryUnit>[] civilizationArmy;
    private ArrayList<MilitaryUnit>[] enemyArmy;

    // Registros de unidades iniciales
    private int initialCivUnits;
    private int initialEnemyUnits;

    // Control del desarrollo en texto y números aleatorios
    private StringBuilder battleLog;
    private Random random;

    // Residuos generados recolectables por el ganador
    private int wasteWood;
    private int wasteIron;

    // Contadores de recursos perdidos para decidir el ganador
    private int resourcesLostCiv;
    private int resourcesLostEnemy;
    
    // Guardamos explícitamente el resultado final para el reporte
    private String winnerName = "Empate"; 
    
    //Constructor
    @SuppressWarnings("unchecked")
    public Battle(ArrayList<MilitaryUnit>[] playerArmy, ArrayList<MilitaryUnit>[] rivalArmy) {
        // Clonamos la estructura de los arrays para no mutar el cuartel real de forma directa
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

        this.initialCivUnits = countTotalUnits(this.civilizationArmy);
        this.initialEnemyUnits = countTotalUnits(this.enemyArmy);
    }

    // =====================================================================
    // LÓGICA PRINCIPAL DEL COMBATE
    // =====================================================================
    public void startBattle() {
        System.out.println("\n=======================================================================");
        System.out.println("                  ¡COMIENZA LA BATALLA EN EL IMPERIO!                  ");
        System.out.println("=======================================================================");
        System.out.println("Unidades iniciales Civilización: " + initialCivUnits);
        System.out.println("Unidades iniciales Enemigo: " + initialEnemyUnits);
        
        battleLog.append("--- INICIO DE LA BATALLA ---\n");

        int turn = 1;

        // El bucle se detiene si un bando cae al 20% o menos de sus tropas iniciales
        while (countTotalUnits(civilizationArmy) > (initialCivUnits * 0.2) && 
               countTotalUnits(enemyArmy) > (initialEnemyUnits * 0.2)) {
            
            System.out.println("\n--- ASALTO Nº " + turn + " ---");
            System.out.println("Tropas Aliadas: " + countTotalUnits(civilizationArmy) + " | Enemigos restantes: " + countTotalUnits(enemyArmy));
            battleLog.append("=== TURNO ").append(turn).append(" ===\n");

            // Actualizar el estado de santificación de los ejércitos al inicio del turno
            updateSanctification();

            // 1. ATACA LA CIVILIZACIÓN
            executeTurn(civilizationArmy, enemyArmy, "Civilización", "Enemigo", CHANCE_ATTACK_CIVILIZATION_UNITS);

            // Verificar si el enemigo se retira/rinde tras el ataque de la civilización
            if (countTotalUnits(enemyArmy) <= (initialEnemyUnits * 0.2)) {
                System.out.println("[INFO] El ejército enemigo cae por debajo del 20% y se bate en retirada.");
                break;
            }

            // Volver a validar sacerdotes por si murieron en el contragolpe
            updateSanctification();

            // 2. ATACA EL ENEMIGO
            executeTurn(enemyArmy, civilizationArmy, "Enemigo", "Civilización", CHANCE_ATTACK_ENEMY_UNITS);

            turn++;
            if (turn > 500) {
                System.out.println("[ALERTA] La batalla se ha estancado tras 500 asaltos.");
                break;
            }
        }

        // =====================================================================
        // FIN DE LA BATALLA E INFORME DE RESULTADOS
        // =====================================================================
        System.out.println("\n=======================================================================");
        System.out.println("                       FIN DE LA SIMULACIÓN                            ");
        System.out.println("=======================================================================");

        // Evaluar ganador según pérdidas económicas ponderadas
        if (resourcesLostCiv < resourcesLostEnemy) {
            this.winnerName = "Civilización";
            System.out.println(" ¡VICTORIA! Tu civilización sufrió menos pérdidas económicas y gana la batalla.");
            
            // Aplicar experiencia a los supervivientes
            for (ArrayList<MilitaryUnit> group : civilizationArmy) {
                for (MilitaryUnit unit : group) {
                    unit.setExperience(unit.getExperience() + 1);
                }
            }
        } else if (resourcesLostEnemy < resourcesLostCiv) {
            this.winnerName = "Enemigo";
            System.out.println(" 💀 DERROTA... El enemigo causó un daño estratégico devastador.");
        } else {
            this.winnerName = "Empate";
            System.out.println(" ⚔️ ¡EMPATE TÉCNICO! Ambas facciones perdieron exactamente el mismo capital.");
        }

        // Imprimir el resumen numérico simplificado que no marea al usuario
        System.out.println("\n--- INFORME DE DAÑOS Y ESCOMBROS ---");
        System.out.println("Valor de pérdidas de tu Civilización: " + resourcesLostCiv);
        System.out.println("Valor de pérdidas del Enemigo: " + resourcesLostEnemy);
        System.out.println("Residuos rescatados del campo: Madera: " + wasteWood + " | Hierro: " + wasteIron);
        System.out.println("=======================================================================");
    }

    // =====================================================================
    // GESTIÓN DE TURNOS Y HABILIDADES ESPECIALES
    // =====================================================================
    private void executeTurn(ArrayList<MilitaryUnit>[] attackers, ArrayList<MilitaryUnit>[] defenders, 
                             String attackerName, String defenderName, int[] probabilities) {
        
        int attackerGroupIdx = selectGroupBasedOnProbability(attackers, probabilities);
        if (attackerGroupIdx == -1) return; 

        MilitaryUnit attacker = attackers[attackerGroupIdx].get(random.nextInt(attackers[attackerGroupIdx].size()));
        boolean attackAgain = true;

        while (attackAgain && countTotalUnits(defenders) > 0) {
            int defenderGroupIdx = selectDefenderGroupProportionally(defenders);
            if (defenderGroupIdx == -1) break;

            int targetIdx = random.nextInt(defenders[defenderGroupIdx].size());
            MilitaryUnit defender = defenders[defenderGroupIdx].get(targetIdx);

            int damage = attacker.attack();
            
            if (damage > 0) {
                defender.takeDamage(damage);
                battleLog.append("[").append(attackerName).append("] Escombro/Ataque inflige ").append(damage).append(" de daño.\n");

                // Comprobar si la unidad defensora muere
                if (defender.getActualArmor() <= 0) {
                    
                    // Habilidad de resurrección del Mago
                    if (hasMagiciansAlive(defenders) && random.nextInt(100) < CHANCE_MAGICIAN_RESSURECT) {
                        defender.resetArmor(); 
                        System.out.println("   [HABILIDAD] ¡Un Mago del bando " + defenderName + " ha resucitado a su compañero!");
                    } else {
                        // Cálculo de pérdidas ponderadas
                        int weightedUnitValue = defender.getIronCost() + (defender.getWoodCost() / 5) + (defender.getFoodCost() / 10);
                        
                        if (defenderName.equals("Civilización")) {
                            resourcesLostCiv += weightedUnitValue;
                        } else {
                            resourcesLostEnemy += weightedUnitValue;
                        }

                        // Calcular residuos generados
                        calculateWaste(defender);

                        // Eliminación física de la lista
                        defenders[defenderGroupIdx].remove(targetIdx);
                    }
                }
            }

            // Validar repetición de ataque por velocidad (Ataques sucesivos)
            int roll = random.nextInt(100);
            attackAgain = roll < attacker.getChanceAttackAgain();
        }
    }

    // =====================================================================
    // AUXILIARES MATEMÁTICOS Y PROBABILIDADES
    // =====================================================================
    // Selección de grupo atacante por probabilidad
    private int selectGroupBasedOnProbability(ArrayList<MilitaryUnit>[] army, int[] probabilities) {
        int roll = random.nextInt(100);
        int sum = 0;
        
        for (int i = 0; i < probabilities.length; i++) {
            sum += probabilities[i];
            if (roll < sum) {
                if (i < army.length && !army[i].isEmpty()) return i;
            }
        }
        for (int i = 0; i < army.length; i++) {
            if (!army[i].isEmpty()) return i;
        }
        return -1;
    }
    // Selección proporcional del grupo defensor
    private int selectDefenderGroupProportionally(ArrayList<MilitaryUnit>[] defenders) {
        int totalAlive = countTotalUnits(defenders);
        if (totalAlive == 0) return -1;

        int roll = random.nextInt(totalAlive);
        int accumulatedUnits = 0;

        for (int i = 0; i < defenders.length; i++) {
            accumulatedUnits += defenders[i].size();
            if (roll < accumulatedUnits) return i;
        }
        return -1;
    }
    // Actualización del estado de santificación
    private void updateSanctification() {
        boolean civHasPriests = (civilizationArmy.length > 8 && !civilizationArmy[8].isEmpty());
        
        for (ArrayList<MilitaryUnit> group : civilizationArmy) {
            for (MilitaryUnit unit : group) {
                if (unit instanceof AttackUnit) ((AttackUnit) unit).setSanctified(civHasPriests);
                else if (unit instanceof DefenseUnit) ((DefenseUnit) unit).setSanctified(civHasPriests);
            }
        }
        
        for (ArrayList<MilitaryUnit> group : enemyArmy) {
            for (MilitaryUnit unit : group) {
                if (unit instanceof AttackUnit) ((AttackUnit) unit).setSanctified(false);
            }
        }
    }
    // Comprobación de magos vivos
    private boolean hasMagiciansAlive(ArrayList<MilitaryUnit>[] army) {
        return army.length > 7 && !army[7].isEmpty();
    }
    // Cálculo de residuos generados
    private void calculateWaste(MilitaryUnit deadUnit) {
        int roll = random.nextInt(100);
        if (roll < deadUnit.getChanceGeneratinWaste()) {
            int woodRecovered = (deadUnit.getWoodCost() * PERCENTAGE_WASTE) / 100;
            int ironRecovered = (deadUnit.getIronCost() * PERCENTAGE_WASTE) / 100;
            
            this.wasteWood += woodRecovered;
            this.wasteIron += ironRecovered;
        }
    }
    // Conteo total de unidades vivas
    private int countTotalUnits(ArrayList<MilitaryUnit>[] army) {
        int count = 0;
        for (ArrayList<MilitaryUnit> group : army) {
            count += group.size();
        }
        return count;
    }

    // =====================================================================
    // REPORTE HISTÓRICO GLOBAL
    // =====================================================================
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
    public String getBattleDevelopment() { 
        return this.battleLog.toString(); }
    public int getWasteWood() { 
        return this.wasteWood; }
    public int getWasteIron() { 
        return this.wasteIron; }
    public ArrayList<MilitaryUnit>[] getCivilizationArmy() { 
        return civilizationArmy; }
    public ArrayList<MilitaryUnit>[] getEnemyArmy() { 
         return enemyArmy; }
}