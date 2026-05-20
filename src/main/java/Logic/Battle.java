package Logic;

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

    // Contadores de recursos perdidos para decidir el ganador (ponderados)
    private int resourcesLostCiv;
    private int resourcesLostEnemy;
    
    // Guardamos explícitamente el resultado final para el reporte
    private String winnerName = "Empate"; 
    
    // NUEVAS ESTRUCTURAS SOLICITADAS
    // [0] = Civilización, [1] = Enemigo | [0-8] = Tipos de unidad
    private int[][] initialArmies;
    
    // [0] = Civilización, [1] = Enemigo | [0] = Comida, [1] = Madera, [2] = Hierro, [3] = Ponderado
    private int[][] resourcesLooses;

    // Constructor
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

        // Inicialización de las nuevas matrices e históricos iniciales
        this.initialArmies = new int[2][9];
        this.resourcesLooses = new int[2][4];
        initInitialArmies();
    }

    // =====================================================================
    // MÉTODOS COMPLEMENTARIOS SOLICITADOS
    // =====================================================================
    
    /**
     * Registra la cantidad inicial de cada tipo de unidad en la matriz para ambos bandos.
     */
    public void initInitialArmies() {
        for (int i = 0; i < 9; i++) {
            this.initialArmies[0][i] = this.civilizationArmy[i].size();
            this.initialArmies[1][i] = this.enemyArmy[i].size();
        }
    }

    /**
     * Actualiza la matriz resourcesLooses sumando los costes reales de la unidad caída
     * y recalculando el valor ponderado acumulado.
     */
    public void updateResourcesLooses(String side, MilitaryUnit deadUnit) {
        int index = side.equals("Civilización") ? 0 : 1;
        
        // Sumar pérdidas netas por tipo de recurso
        this.resourcesLooses[index][0] += deadUnit.getFoodCost();
        this.resourcesLooses[index][1] += deadUnit.getWoodCost();
        this.resourcesLooses[index][2] += deadUnit.getIronCost();
        
        // Calcular y acumular el valor ponderado
        int weightedUnitValue = deadUnit.getIronCost() + (deadUnit.getWoodCost() / 5) + (deadUnit.getFoodCost() / 10);
        this.resourcesLooses[index][3] += weightedUnitValue;

        // Mantener la sincronización con las variables originales de la clase
        if (index == 0) {
            this.resourcesLostCiv = this.resourcesLooses[0][3];
        } else {
            this.resourcesLostEnemy = this.resourcesLooses[1][3];
        }
    }

    /**
     * Devuelve el coste acumulado en recursos de un bando específico sumando comida, madera e hierro.
     */
    public int fleetResourceCost(String side) {
        int index = side.equals("Civilización") ? 0 : 1;
        return this.resourcesLooses[index][0] + this.resourcesLooses[index][1] + this.resourcesLooses[index][2];
    }

    /**
     * Devuelve el recuento original total de unidades de un bando desde la matriz inicial.
     */
    public int initialFleetNumber(String side) {
        int index = side.equals("Civilización") ? 0 : 1;
        int total = 0;
        for (int i = 0; i < 9; i++) {
            total += this.initialArmies[index][i];
        }
        return total;
    }

    /**
     * Calcula el porcentaje de tropas que le quedan a un bando en base a su estado inicial.
     */
    public double remainderPercentageFleet(ArrayList<MilitaryUnit>[] army, String side) {
        int initial = initialFleetNumber(side);
        if (initial == 0) return 0.0;
        return ((double) countTotalUnits(army) / initial) * 100.0;
    }

    /**
     * Helper para duplicar logs y asegurar que todo termine EXACTAMENTE igual en consola y en battleLog.
     */
    private void logMessage(String message) {
        System.out.println(message);
        this.battleLog.append(message).append("\n");
    }

    // =====================================================================
    // LÓGICA PRINCIPAL DEL COMBATE
    // =====================================================================
    public void startBattle() {
        // GESTIÓN DE VICTORIA/DERROTA AUTOMÁTICA SI UN EJÉRCITO EMPIEZA CON 0 UNIDADES
        if (initialCivUnits == 0 || initialEnemyUnits == 0) {
            logMessage("\n=======================================================================");
            logMessage("             ¡ALERTA: CONDICIONES DE COMBATE INVÁLIDAS!                ");
            logMessage("=======================================================================");
            
            if (initialCivUnits == 0 && initialEnemyUnits == 0) {
                this.winnerName = "Empate";
                logMessage("[INFO] Ambos ejércitos se presentaron al campo sin unidades. Batalla cancelada en empate.");
            } else if (initialCivUnits == 0) {
                this.winnerName = "Enemigo";
                logMessage("[INFO] Tu civilización no dispone de tropas. ¡Victoria automática para el Enemigo!");
            } else {
                this.winnerName = "Civilización";
                logMessage("[INFO] El ejército enemigo no dispone de tropas. ¡Victoria automática para la Civilización!");
                // Aplicar experiencia a los supervivientes por la victoria directa
                for (ArrayList<MilitaryUnit> group : civilizationArmy) {
                    for (MilitaryUnit unit : group) {
                        unit.setExperience(unit.getExperience() + 1);
                    }
                }
            }
            finalizarYGuardarBD();
            return;
        }

        // DESARROLLO ESTÁNDAR DE LA BATALLA
        logMessage("\n=======================================================================");
        logMessage("                  ¡COMIENZA LA BATALLA EN EL IMPERIO!                  ");
        logMessage("=======================================================================");
        logMessage("Unidades iniciales Civilización: " + initialCivUnits);
        logMessage("Unidades iniciales Enemigo: " + initialEnemyUnits);
        logMessage("--- INICIO DE LA BATALLA ---");

        int turn = 1;

        // El bucle se detiene si un bando cae al 20% o menos de sus tropas iniciales
        while (countTotalUnits(civilizationArmy) > (initialCivUnits * 0.2) && 
               countTotalUnits(enemyArmy) > (initialEnemyUnits * 0.2)) {
            
            logMessage("\n--- ASALTO Nº " + turn + " ---");
            logMessage("Tropas Aliadas: " + countTotalUnits(civilizationArmy) + " (" + String.format("%.2f", remainderPercentageFleet(civilizationArmy, "Civilización")) + "%)" +
                       " | Enemigos restantes: " + countTotalUnits(enemyArmy) + " (" + String.format("%.2f", remainderPercentageFleet(enemyArmy, "Enemigo")) + "%)");
            logMessage("=== TURNO " + turn + " ===");

            // Actualizar el estado de santificación de los ejércitos al inicio del turno
            updateSanctification();

            // 1. ATACA LA CIVILIZACIÓN
            executeTurn(civilizationArmy, enemyArmy, "Civilización", "Enemigo", CHANCE_ATTACK_CIVILIZATION_UNITS);

            // Verificar si el enemigo se retira/rinde tras el ataque de la civilización
            if (countTotalUnits(enemyArmy) <= (initialEnemyUnits * 0.2)) {
                logMessage("[INFO] El ejército enemigo cae por debajo del 20% y se bate en retirada.");
                break;
            }

            // Volver a validar sacerdotes por si murieron en el contragolpe
            updateSanctification();

            // 2. ATACA EL ENEMIGO
            executeTurn(enemyArmy, civilizationArmy, "Enemigo", "Civilización", CHANCE_ATTACK_ENEMY_UNITS);

            // Verificar si la civilización se retira tras el ataque enemigo
            if (countTotalUnits(civilizationArmy) <= (initialCivUnits * 0.2)) {
                logMessage("[INFO] Tu ejército civilizado cae por debajo del 20% y se bate en retirada.");
                break;
            }

            turn++;
            if (turn > 500) {
                logMessage("[ALERTA] La batalla se ha estancado tras 500 asaltos.");
                break;
            }
        }

        // =====================================================================
        // FIN DE LA BATALLA E INFORME DE RESULTADOS
        // =====================================================================
        logMessage("\n=======================================================================");
        logMessage("                       FIN DE LA SIMULACIÓN                            ");
        logMessage("=======================================================================");

        // Evaluar ganador según pérdidas económicas ponderadas (índice 3 de la matriz)
        if (resourcesLooses[0][3] < resourcesLooses[1][3]) {
            this.winnerName = "Civilización";
            logMessage(" ¡VICTORIA! Tu civilización sufrió menos pérdidas económicas y gana la batalla.");
            
            // Aplicar experiencia a los supervivientes
            for (ArrayList<MilitaryUnit> group : civilizationArmy) {
                for (MilitaryUnit unit : group) {
                    unit.setExperience(unit.getExperience() + 1);
                }
            }
        } else if (resourcesLooses[1][3] < resourcesLooses[0][3]) {
            this.winnerName = "Enemigo";
            logMessage(" 💀 DERROTA... El enemigo causó un daño estratégico devastador.");
        } else {
            this.winnerName = "Empate";
            logMessage(" ⚔️ ¡EMPATE TÉCNICO! Ambas facciones perdieron exactamente el mismo capital.");
        }

        // Imprimir el resumen numérico con el desglose exacto de todos los recursos
        logMessage("\n--- INFORME DE DAÑOS Y ESCOMBROS ---");
        logMessage("Valor de pérdidas ponderadas de tu Civilización: " + resourcesLooses[0][3]);
        logMessage("   -> Desglose real: Comida: " + resourcesLooses[0][0] + " | Madera: " + resourcesLooses[0][1] + " | Hierro: " + resourcesLooses[0][2]);
        logMessage("Valor de pérdidas ponderadas del Enemigo: " + resourcesLooses[1][3]);
        logMessage("   -> Desglose real: Comida: " + resourcesLooses[1][0] + " | Madera: " + resourcesLooses[1][1] + " | Hierro: " + resourcesLooses[1][2]);
        logMessage("Residuos rescatados del campo: Madera: " + wasteWood + " | Hierro: " + wasteIron);
        logMessage("=======================================================================");

        finalizarYGuardarBD();
    }

    /**
     * Centraliza la persistencia e historial en la Base de Datos para evitar duplicar código.
     */
    private void finalizarYGuardarBD() {
        try {
            java.sql.Connection conexion = java.sql.DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/civilizations_db", "root", ""
            );

            String sql = "INSERT INTO batallas (numero_batalla, resultado, residuos_madera, residuos_hierro, "
                       + "coste_comida_perdido_civi, coste_madera_perdido_civi, coste_hierro_perdido_civi, "
                       + "coste_comida_perdido_enem, coste_madera_perdido_enem, coste_hierro_perdido_enem, "
                       + "desarrollo_paso_a_paso) VALUES (1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            java.sql.PreparedStatement ps = conexion.prepareStatement(sql);
            
            ps.setString(1, this.winnerName);
            ps.setInt(2, this.wasteWood);
            ps.setInt(3, this.wasteIron);
            
            // Pérdidas desglosadas reales de la Civilización
            ps.setInt(4, this.resourcesLooses[0][0]);
            ps.setInt(5, this.resourcesLooses[0][1]);
            ps.setInt(6, this.resourcesLooses[0][2]);
            
            // Pérdidas desglosadas reales del Enemigo
            ps.setInt(7, this.resourcesLooses[1][0]);
            ps.setInt(8, this.resourcesLooses[1][1]);
            ps.setInt(9, this.resourcesLooses[1][2]);
            
            ps.setString(10, this.getBattleDevelopment());

            ps.executeUpdate();
            ps.close();
            conexion.close();
            
            System.out.println("[MySQL] ¡Historial de batalla guardado automáticamente con éxito!");

        } catch (java.sql.SQLException e) {
            System.err.println("[Error MySQL] No se pudo guardar el historial: " + e.getMessage());
        }
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
                logMessage("[" + attackerName + "] " + attacker.getClass().getSimpleName() + " ataca a " + defender.getClass().getSimpleName() + " e inflige " + damage + " de daño.");

                // Comprobar si la unidad defensora muere
                if (defender.getActualArmor() <= 0) {
                    
                    // Habilidad de resurrección del Mago
                    if (hasMagiciansAlive(defenders) && random.nextInt(100) < CHANCE_MAGICIAN_RESSURECT) {
                        defender.resetArmor(); 
                        logMessage("   [HABILIDAD] ¡Un Mago del bando " + defenderName + " ha resucitado a su compañero " + defender.getClass().getSimpleName() + "!");
                    } else {
                        // Registro exacto de bajas y recursos desglosados mediante la nueva matriz
                        logMessage("   [BAJA] " + defender.getClass().getSimpleName() + " del bando " + defenderName + " ha sido eliminado.");
                        updateResourcesLooses(defenderName, defender);

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
            if (attackAgain && countTotalUnits(defenders) > 0) {
                logMessage("   [HABILIDAD] ¡Velocidad de ataque activada! " + attacker.getClass().getSimpleName() + " encadena otro golpe.");
            }
        }
    }

    // =====================================================================
    // AUXILIARES MATEMÁTICOS Y PROBABILIDADES
    // =====================================================================
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

    private boolean hasMagiciansAlive(ArrayList<MilitaryUnit>[] army) {
        return army.length > 7 && !army[7].isEmpty();
    }

    private void calculateWaste(MilitaryUnit deadUnit) {
        int roll = random.nextInt(100);
        if (roll < deadUnit.getChanceGeneratinWaste()) {
            int woodRecovered = (deadUnit.getWoodCost() * PERCENTAGE_WASTE) / 100;
            int ironRecovered = (deadUnit.getIronCost() * PERCENTAGE_WASTE) / 100;
            
            this.wasteWood += woodRecovered;
            this.wasteIron += ironRecovered;
            logMessage("   [ESCOMBROS] Se han recuperado del cadáver: Madera: " + woodRecovered + " | Hierro: " + ironRecovered);
        }
    }

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
        report.append("Tropas iniciales: Jugador: ").append(initialFleetNumber("Civilización")).append(" | Enemigo: ").append(initialFleetNumber("Enemigo")).append("\n");
        report.append("Tropas restantes: Jugador: ").append(countTotalUnits(civilizationArmy)).append(" | Enemigo: ").append(countTotalUnits(enemyArmy)).append("\n");
        report.append("Pérdidas Netas Jugador - Comida: ").append(resourcesLooses[0][0]).append(" | Madera: ").append(resourcesLooses[0][1]).append(" | Hierro: ").append(resourcesLooses[0][2]).append("\n");
        report.append("Pérdidas Ponderadas Jugador: ").append(resourcesLooses[0][3]).append("\n");
        report.append("Pérdidas Netas Enemigo - Comida: ").append(resourcesLooses[1][0]).append(" | Madera: ").append(resourcesLooses[1][1]).append(" | Hierro: ").append(resourcesLooses[1][2]).append("\n");
        report.append("Pérdidas Ponderadas Enemigo: ").append(resourcesLooses[1][3]).append("\n");
        report.append("Escombros del campo de batalla: Madera: ").append(wasteWood).append(" | Hierro: ").append(wasteIron).append("\n");
        report.append("=========================================\n");
        return report.toString();
    }

    // --- GETTERS ---
    public String getBattleDevelopment() { 
        return this.battleLog.toString(); 
    }
    public int getWasteWood() { 
        return this.wasteWood; 
    }
    public int getWasteIron() { 
        return this.wasteIron; 
    }
    public ArrayList<MilitaryUnit>[] getCivilizationArmy() { 
        return civilizationArmy; 
    }
    public ArrayList<MilitaryUnit>[] getEnemyArmy() { 
         return enemyArmy; 
    }
}