package Civilitation_Proyect;

public class Priest extends SpecialUnit {

    public Priest() {
        // Inicializamos con la armadura y el daño base definidos en Variables
        super(ARMOR_PRIEST, BASE_DAMAGE_PRIEST);
    }

    @Override
    public int attack() {
        // Daño base + bonus por experiencia acumulada
        return getBaseDamage() + (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT);
    }

    @Override
    public boolean chanceAttackAgain() {
        // Probabilidad de ataque extra (CHANCE_ATTACK_AGAIN_PRIEST definida en Variables)
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_ATTACK_AGAIN_PRIEST;
    }

    @Override
    public boolean chanceGeneratngWaste() {
        // Probabilidad de generar residuos al morir
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_GENERATNG_WASTE_PRIEST;
    }

    // --- Métodos de coste vinculados a la interfaz Variables ---

    @Override
    public int getWoodCost() {
        return WOOD_COST_PRIEST; // Generalmente 0
    }

    @Override
    public int getFoodCost() {
        return FOOD_COST_PRIEST;
    }

    @Override
    public int getIronCost() {
        return IRON_COST_PRIEST; // Generalmente 0
    }

    @Override
    public int getManaCost() {
        return MANA_COST_PRIEST; // Recurso fundamental junto con la comida
    }
}