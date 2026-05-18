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

        // El bucle se detiene si un bando cae al 20% o menos de sus tropas iniciales
        while (countTotalUnits(civilizationArmy) > (initialCivUnits * 0.2) && 
               countTotalUnits(enemyArmy) > (initialEnemyUnits * 0.2)) {
            
            battleLog.append("=== TURNO ").append(turn).append(" ===\n");

            // REQUISITO PDF: Actualizar el estado de santificación de los ejércitos al inicio del turno
            updateSanctification();

            // 1. ATACA LA CIVILIZACIÓN
            executeTurn(civilizationArmy, enemyArmy, "Civilización", "Enemigo", CHANCE_ATTACK_CIVILIZATION_UNITS);

            // Verificar si el enemigo se retira/rinde tras el ataque de la civilización
            if (countTotalUnits(enemyArmy) <= (initialEnemyUnits * 0.2)) break;

            // REQUISITO PDF: Volver a validar sacerdotes por si murieron en el contragolpe
            updateSanctification();

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

        // Evaluar ganador según el bando que haya perdido MENOS valor de recursos ponderado
        if (resourcesLostCiv < resourcesLostEnemy) {
            this.winnerName = "Civilización";
            battleLog.append("¡VICTORIA! Tu civilización sufrió menos pérdidas económicas y gana la batalla.\n");
            
            // Aplicar experiencia a los supervivientes que quedan en el ejército clonado
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
        
        // Seleccionar el GRUPO ATACANTE usando probabilidades fijas
        int attackerGroupIdx = selectGroupBasedOnProbability(attackers, probabilities);
        if (attackerGroupIdx == -1) return; 

        MilitaryUnit attacker = attackers[attackerGroupIdx].get(random.nextInt(attackers[attackerGroupIdx].size()));
        boolean attackAgain = true;

        while (attackAgain && countTotalUnits(defenders) > 0) {
            // Seleccionar el GRUPO DEFENSOR proporcionalmente a las tropas vivas
            int defenderGroupIdx = selectDefenderGroupProportionally(defenders);
            if (defenderGroupIdx == -1) break;

            int targetIdx = random.nextInt(defenders[defenderGroupIdx].size());
            MilitaryUnit defender = defenders[defenderGroupIdx].get(targetIdx);

            int damage = attacker.attack();
            
            if (damage > 0) {
                defender.takeDamage(damage);
                battleLog.append("[").append(attackerName).append("] ")
                         .append(defender.toString()) // Usamos toString() para el nombre de la unidad
                         .append(" inflige ").append(damage).append(" de daño a ")
                         .append(defender.toString()).append(" ").append(defenderName)
                         .append(" (Armadura actual: ").append(defender.getActualArmor()).append(")\n");

                // Comprobar si la unidad defensora "cae en combate"
                if (defender.getActualArmor() <= 0) {
                    
                    // REQUISITO PDF: Habilidad especial de resurrección del Mago (si hay magos vivos en el bando defensor)
                    if (hasMagiciansAlive(defenders) && random.nextInt(100) < CHANCE_MAGICIAN_RESSURECT) {
                        defender.resetArmor(); // Restablece su vida base original
                        battleLog.append("   [HABILIDAD] ¡Un Magician del bando ").append(defenderName)
                                 .append(" ha resucitado a un ").append(defender.toString()).append("!\n");
                    } else {
                        // Si no revive, se procesa su muerte definitiva
                        battleLog.append("   -> ¡¡ ").append(defender.toString()).append(" destruido !!\n");
                        
                        // Fórmula matemática exacta del PDF para recursos ponderados
                        int weightedUnitValue = defender.getIronCost() + 
                                                (defender.getWoodCost() / 5) + 
                                                (defender.getFoodCost() / 10);
                        
                        if (defenderName.equals("Civilización")) {
                            resourcesLostCiv += weightedUnitValue;
                        } else {
                            resourcesLostEnemy += weightedUnitValue;
                        }

                        // Calcular escombros/chatarra (Corregido error tipográfico de la constante)
                        calculateWaste(defender);

                        // Eliminar físicamente del sub-ejército
                        defenders[defenderGroupIdx].remove(targetIdx);
                    }
                }
            }

            // Validar repetición de ataque por velocidad
            int roll = random.nextInt(100);
            attackAgain = roll < attacker.getChanceAttackAgain();
            if (attackAgain && countTotalUnits(defenders) > 0) {
                battleLog.append("   (Habilidad: El ").append(attacker.toString()).append(" arremete de nuevo consecutivamente)\n");
            }
        }
    }

    private int selectGroupBasedOnProbability(ArrayList<MilitaryUnit>[] army, int[] probabilities) {
        int roll = random.nextInt(100);
        int sum = 0;
        
        // Buscamos según el rango del array de probabilidades asignado (evita desbordamientos con el enemigo)
        for (int i = 0; i < probabilities.length; i++) {
            sum += probabilities[i];
            if (roll < sum) {
                if (i < army.length && !army[i].isEmpty()) return i;
            }
        }
        // Salvaguarda: si cae en un porcentaje vacío, busca la primera categoría con tropas
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

    // REQUISITO PDF: Gestión automática y en tiempo real de la bendición sacerdotal
    private void updateSanctification() {
        // En la civilización, los sacerdotes se guardan tradicionalmente en el índice 8
        boolean civHasPriests = (civilizationArmy.length > 8 && !civilizationArmy[8].isEmpty());
        
        // Aplicamos santificación a todas las unidades de la civilización según la presencia de Sacerdotes
        for (ArrayList<MilitaryUnit> group : civilizationArmy) {
            for (MilitaryUnit unit : group) {
                if (unit instanceof AttackUnit) {
                    ((AttackUnit) unit).setSanctified(civHasPriests);
                } else if (unit instanceof DefenseUnit) {
                    ((DefenseUnit) unit).setSanctified(civHasPriests);
                }
            }
        }
        
        // El ejército enemigo no tiene sacerdotes según el enunciado, por ende siempre se setea en false
        for (ArrayList<MilitaryUnit> group : enemyArmy) {
            for (MilitaryUnit unit : group) {
                if (unit instanceof AttackUnit) {
                    ((AttackUnit) unit).setSanctified(false);
                }
            }
        }
    }

    // Verifica si quedan magos activos en el ejército (el mago ocupa el índice 7)
    private boolean hasMagiciansAlive(ArrayList<MilitaryUnit>[] army) {
        return army.length > 7 && !army[7].isEmpty();
    }

    private void calculateWaste(MilitaryUnit deadUnit) {
        int roll = random.nextInt(100);
        if (roll < deadUnit.getChanceGeneratinWaste()) {
            // Corregido: PERCENTAGE_WASTE para que compile con tu interfaz Variables
            int woodRecovered = (deadUnit.getWoodCost() * PERCENTAGE_WASTE) / 100;
            int ironRecovered = (deadUnit.getIronCost() * PERCENTAGE_WASTE) / 100;
            
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

    // Devuelve el reporte formateado de la batalla actual
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