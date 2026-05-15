package Civilitation_Proyect;

/**
 * Clase Spearman: Unidad de ataque básica (infantería ligera).
 * Se caracteriza por ser barata de producir y equilibrada en combate.
 */
public class Spearman extends AttackUnit {

    public Spearman() {
        // Inicializamos con armadura y daño base desde la interfaz Variables
        super(ARMOR_SPEARMAN, BASE_DAMAGE_SPEARMAN);
    }

    @Override
    public int attack() {
        // Daño base aumentado por la experiencia de la unidad
        return getBaseDamage() + (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT);
    }

    @Override
    public boolean chanceAttackAgain() {
        // Probabilidad de ataque extra (suele ser baja para unidades básicas, ej. 5-10%)
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_ATTACK_AGAIN_SPEARMAN;
    }

    @Override
    public boolean chanceGeneratngWaste() {
        // Probabilidad de generar residuos al ser derrotado
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_GENERATNG_WASTE_SPEARMAN;
    }

    // --- Métodos de coste obligatorios (Persona 1) ---

    @Override
    public int getWoodCost() {
        return WOOD_COST_SPEARMAN;
    }

    @Override
    public int getFoodCost() {
        return FOOD_COST_SPEARMAN;
    }

    @Override
    public int getIronCost() {
        return IRON_COST_SPEARMAN; // Generalmente bajo o 0
    }

    @Override
    public int getManaCost() {
        return MANA_COST_SPEARMAN; // Siempre 0
    }
}