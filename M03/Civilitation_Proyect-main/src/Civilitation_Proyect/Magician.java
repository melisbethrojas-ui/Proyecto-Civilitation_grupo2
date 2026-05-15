package Civilitation_Proyect;

/**
 * Clase Magician: Unidad especial de tipo mágico.
 * Requiere una MagicTower para ser reclutado y consume Maná.
 */
public class Magician extends SpecialUnit {

    public Magician() {
        // Inicializamos con armadura y daño base desde la interfaz Variables
        super(ARMOR_MAGICIAN, BASE_DAMAGE_MAGICIAN);
    }

    @Override
    public int attack() {
        // Al ser una unidad especial, el daño escala con la experiencia
        return getBaseDamage() + (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT);
    }

    /**
     * Lógica de ataque extra para el Mago.
     */
    @Override
    public boolean chanceAttackAgain() {
        // El Mago suele tener una probabilidad media-alta de repetir ataque (ej. 40%)
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_ATTACK_AGAIN_MAGICIAN;
    }

    /**
     * Probabilidad de generar residuos al morir.
     */
    @Override
    public boolean chanceGeneratngWaste() {
        // CHANCE_GENERATNG_WASTE_MAGICIAN definido en Variables
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_GENERATNG_WASTE_MAGICIAN;
    }

    // --- Métodos de coste vinculados a Variables ---

    @Override
    public int getWoodCost() {
        return WOOD_COST_MAGICIAN; // Generalmente 0
    }

    @Override
    public int getFoodCost() {
        return FOOD_COST_MAGICIAN;
    }

    @Override
    public int getIronCost() {
        return IRON_COST_MAGICIAN; // Generalmente 0
    }

    @Override
    public int getManaCost() {
        return MANA_COST_MAGICIAN; // Recurso clave para esta unidad
    }
}