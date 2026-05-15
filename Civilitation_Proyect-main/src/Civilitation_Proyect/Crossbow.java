package Civilitation_Proyect;

public class Crossbow extends AttackUnit {

    public Crossbow() {
        // Inicializamos con la armadura y el daño base definidos en Variables
        super(ARMOR_CROSSBOW, BASE_DAMAGE_CROSSBOW);
    }

    @Override
    public int attack() {
        // Daño base + (puntos de experiencia * plus por experiencia)
        return getBaseDamage() + (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT);
    }

    @Override
    public boolean chanceAttackAgain() {
        // Probabilidad de ataque extra (suele ser un valor medio, ej. 30%)
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_ATTACK_AGAIN_CROSSBOW;
    }

    @Override
    public boolean chanceGeneratngWaste() {
        // Probabilidad de generar escombros al ser destruido
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_GENERATNG_WASTE_CROSSBOW;
    }

    // --- Métodos de coste (M03 Requerido) ---

    @Override
    public int getWoodCost() {
        return WOOD_COST_CROSSBOW;
    }

    @Override
    public int getFoodCost() {
        return FOOD_COST_CROSSBOW;
    }

    @Override
    public int getIronCost() {
        return IRON_COST_CROSSBOW;
    }

    @Override
    public int getManaCost() {
        return MANA_COST_CROSSBOW; // Generalmente 0 para unidades no mágicas
    }
}