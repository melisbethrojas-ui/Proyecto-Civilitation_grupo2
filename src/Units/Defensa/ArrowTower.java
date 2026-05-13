package src.Units.Defensa;

public class ArrowTower extends DefenseUnit {

    // Constructor: usa los valores definidos en Variables
    public ArrowTower() {
        super(ARMOR_ARROWTOWER, BASE_DAMAGE_ARROWTOWER);
    }

    // Costes específicos de esta defensa
    @Override
    public int getFoodCost() {
        return FOOD_COST_ARROWTOWER;
    }

    @Override
    public int getWoodCost() {
        return WOOD_COST_ARROWTOWER;
    }

    @Override
    public int getGoldCost() {
        return 0; // No usa oro
    }

    @Override
    public int getIronCost() {
        return IRON_COST_ARROWTOWER;
    }

    @Override
    public int getManaCost() {
        return MANA_COST_ARROWTOWER;
    }

    // Probabilidad de generar residuos (específica de ArrowTower)
    @Override
    public int getChanceGeneratingWaste() {
        return CHANCE_GENERATNG_WASTE_ARROWTOWER;
    }

    // Probabilidad de atacar dos veces (específica de ArrowTower)
    @Override
    public int getChanceAttackAgain() {
        return CHANCE_ATTACK_AGAIN_ARROWTOWER;
    }
}
