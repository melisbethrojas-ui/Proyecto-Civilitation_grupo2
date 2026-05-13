package Proyecto_Civilitation.src.Units.Defensa;

public class Catapult extends DefenseUnit {

    // Constructor: usa los valores definidos en Variables
    public Catapult() {
        super(ARMOR_CATAPULT, BASE_DAMAGE_CATAPULT);
    }

    // Costes específicos de esta defensa
    @Override
    public int getFoodCost() {
        return FOOD_COST_CATAPULT;
    }

    @Override
    public int getWoodCost() {
        return WOOD_COST_CATAPULT;
    }

    @Override
    public int getGoldCost() {
        return 0; // No usa oro
    }

    @Override
    public int getIronCost() {
        return IRON_COST_CATAPULT;
    }

    @Override
    public int getManaCost() {
        return MANA_COST_CATAPULT;
    }

    // Probabilidad de generar residuos (específica de Catapult)
    @Override
    public int getChanceGeneratingWaste() {
        return CHANCE_GENERATNG_WASTE_CATAPULT;
    }

    // Probabilidad de atacar dos veces (específica de Catapult)
    @Override
    public int getChanceAttackAgain() {
        return CHANCE_ATTACK_AGAIN_CATAPULT;
    }
}
