package Proyecto_Civilitation.src.Units.Ataque;

public class Crossbow extends AttackUnit {

    // Constructor: usa los valores de ARMOR y BASE_DAMAGE definidos en Variables
    public Crossbow() {
        super(ARMOR_CROSSBOW, BASE_DAMAGE_CROSSBOW);
    }

    // Costes específicos de esta unidad
    @Override
    public int getFoodCost() {
        return FOOD_COST_CROSSBOW;
    }

    @Override
    public int getWoodCost() {
        return WOOD_COST_CROSSBOW;
    }

    @Override
    public int getGoldCost() {
        return 0; // No usa oro
    }

    @Override
    public int getIronCost() {
        return IRON_COST_CROSSBOW;
    }

    @Override
    public int getManaCost() {
        return MANA_COST_CROSSBOW;
    }

    // Probabilidad de generar residuos (específica de Crossbow)
    @Override
    public int getChanceGeneratingWaste() {
        return CHANCE_GENERATNG_WASTE_CROSSBOW;
    }

    // Probabilidad de atacar dos veces (específica de Crossbow)
    @Override
    public int getChanceAttackAgain() {
        return CHANCE_ATTACK_AGAIN_CROSSBOW;
    }
}
