package Proyecto_Civilitation.src.Units.Ataque;

public class Cannon extends AttackUnit {

    // Constructor: usa los valores de ARMOR y BASE_DAMAGE definidos en Variables
    public Cannon() {
        super(ARMOR_CANNON, BASE_DAMAGE_CANNON);
    }

    // Costes específicos de esta unidad
    @Override
    public int getFoodCost() {
        return FOOD_COST_CANNON;
    }

    @Override
    public int getWoodCost() {
        return WOOD_COST_CANNON;
    }

    @Override
    public int getGoldCost() {
        return 0; // No usa oro
    }

    @Override
    public int getIronCost() {
        return IRON_COST_CANNON;
    }

    @Override
    public int getManaCost() {
        return MANA_COST_CANNON;
    }

    // Probabilidad de generar residuos (específica de Cannon)
    @Override
    public int getChanceGeneratingWaste() {
        return CHANCE_GENERATNG_WASTE_CANNON;
    }

    // Probabilidad de atacar dos veces (específica de Cannon)
    @Override
    public int getChanceAttackAgain() {
        return CHANCE_ATTACK_AGAIN_CANNON;
    }
}
