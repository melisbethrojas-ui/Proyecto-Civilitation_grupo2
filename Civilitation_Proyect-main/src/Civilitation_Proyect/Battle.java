package Civilitation_Proyect;
import java.util.ArrayList;
import java.util.Random;

public class Battle {

    // Ejércitos organizados para el combate
    private ArrayList<MilitaryUnit> civilizationArmy;
    private ArrayList<MilitaryUnit> enemyArmy;

    // Almacén para el desarrollo de la batalla en texto (para la interfaz o la web)
    private StringBuilder battleLog;
    private Random random;

    // Residuos generados durante el combate
    private int wasteWood;
    private int wasteIron;

    public Battle(ArrayList<MilitaryUnit> playerArmy, ArrayList<MilitaryUnit> rivalArmy) {
        this.civilizationArmy = new ArrayList<>(playerArmy);
        this.enemyArmy = new ArrayList<>(rivalArmy);
        this.battleLog = new StringBuilder();
        this.random = new Random();
        this.wasteWood = 0;
        this.wasteIron = 0;
    }

    /**
     * Desarrolla el combate completo por turnos hasta que un bando sea derrotado.
     */
    public void startBattle() {
        battleLog.append("--- INICIO DE LA BATALLA ---\n");
        battleLog.append("Unidades de la Civilización: ").append(civilizationArmy.size()).append("\n");
        battleLog.append("Unidades del Enemigo: ").append(enemyArmy.size()).append("\n\n");

        int turn = 1;

        // El bucle sigue mientras ambos tengan tropas vivas
        while (!civilizationArmy.isEmpty() && !enemyArmy.isEmpty()) {
            battleLog.append("=== TURNO ").append(turn).append(" ===\n");

            // 1. ATACA LA CIVILIZACIÓN
            if (!civilizationArmy.isEmpty() && !enemyArmy.isEmpty()) {
                executeTurn(civilizationArmy, enemyArmy, "Civilización", "Enemigo", Variables.CHANCE_ATTACK_CIVILIZATION_UNITS);
            }

            // 2. ATACA EL ENEMIGO
            if (!civilizationArmy.isEmpty() && !enemyArmy.isEmpty()) {
                executeTurn(enemyArmy, civilizationArmy, "Enemigo", "Civilización", Variables.CHANCE_ATTACK_ENEMY_UNITS);
            }

            turn++;
            // Límite de seguridad para evitar bucles infinitos en pruebas
            if (turn > 1000) {
                battleLog.append("La batalla se ha extendido demasiado. Empate técnico.\n");
                break;
            }
        }

        // Determinar ganador
        battleLog.append("\n--- FIN DE LA BATALLA ---\n");
        if (civilizationArmy.isEmpty() && enemyArmy.isEmpty()) {
            battleLog.append("Destrucción mutua. No queda nadie en pie.\n");
        } else if (civilizationArmy.isEmpty()) {
            battleLog.append("¡Derrota! El ejército enemigo ha vencido.\n");
        } else {
            battleLog.append("¡Victoria! Tu civilización ha ganado el combate.\n");
            // Aplicar experiencia a los supervivientes
            for (MilitaryUnit unit : civilizationArmy) {
                unit.setExperience(unit.getExperience() + 1);
            }
            battleLog.append("Las unidades supervivientes ganan +1 de experiencia.\n");
        }
    }

    /**
     * Ejecuta la lógica de ataque de un bando contra otro basándose en las probabilidades fijas.
     */
    private void executeTurn(ArrayList<MilitaryUnit> attackers, ArrayList<MilitaryUnit> defenders, 
                             String attackerName, String defenderName, int[] probabilities) {
        
        // Seleccionar atacante aleatorio del bando
        MilitaryUnit attacker = attackers.get(random.nextInt(attackers.size()));
        
        // Calcular si repite ataque por sus probabilidades individuales
        boolean attackAgain = true;
        
        while (attackAgain && !defenders.isEmpty()) {
            // Seleccionar defensor aleatorio del bando contrario
            int defenderIdx = random.nextInt(defenders.size());
            MilitaryUnit defender = defenders.get(defenderIdx);

            int damage = attacker.attack();
            
            // Si el atacante es un Sacerdote, su daño es 0 pero podría activar lógica de soporte si se implementa
            if (damage > 0) {
                defender.takeDamage(damage);
                battleLog.append("[").append(attackerName).append("] ")
                         .append(attacker.getClass().getSimpleName())
                         .append(" ataca a ").append(defender.getClass().getSimpleName())
                         .append(" infligiendo ").append(damage).append(" de daño. ")
                         .append("(Armadura restante: ").append(Math.max(0, defender.getActualArmor())).append(")\n");

                // Comprobar si el defensor ha muerto
                if (defender.getActualArmor() <= 0) {
                    battleLog.append("¡¡ ").append(defender.getClass().getSimpleName()).append(" ").append(defenderName).append(" ha sido destruido !!\n");
                    
                    // Lógica de cálculo de residuos/escombros
                    calculateWaste(defender);
                    
                    // Se elimina del ejército activo
                    defenders.remove(defenderIdx);
                }
            }

            // Comprobar si se repite el tiro en este mismo turno
            int roll = random.nextInt(100);
            attackAgain = roll < attacker.getChanceAttackAgain();
            if (attackAgain && !defenders.isEmpty()) {
                battleLog.append("¡Habilidad activada! El ").append(attacker.getClass().getSimpleName()).append(" repite ataque de forma consecutiva.\n");
            }
        }
    }

    /**
     * Calcula los residuos de madera y hierro recolectados al caer una unidad.
     */
    private void calculateWaste(MilitaryUnit deadUnit) {
        int roll = random.nextInt(100);
        // Usamos el nombre del método que coincide con tu interfaz corregida
        if (roll < deadUnit.getChanceGeneratinWaste()) {
            int woodRecovered = (deadUnit.getWoodCost() * Variables.PERCENTATGE_WASTE) / 100;
            int ironRecovered = (deadUnit.getIronCost() * Variables.PERCENTATGE_WASTE) / 100;
            
            this.wasteWood += woodRecovered;
            this.wasteIron += ironRecovered;
            
            battleLog.append("  [ESCOMBROS] Se han generado ").append(woodRecovered)
                     .append(" de madera y ").append(ironRecovered).append(" de hierro en el campo.\n");
        }
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

    public ArrayList<MilitaryUnit> getCivilizationArmy() {
        return civilizationArmy;
    }

    public ArrayList<MilitaryUnit> getEnemyArmy() {
        return enemyArmy;
    }
}