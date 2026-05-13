package Proyecto_Civilitation.src.Units.Ataque;

public class Spearman extends AttackUnit {

    // Constructor: usa los valores de ARMOR y BASE_DAMAGE definidos en Variables
    public Spearman() {
        super(ARMOR_SPEARMAN, BASE_DAMAGE_SPEARMAN);
    }

    // Costes específicos de esta unidad
    @Override
    public int getFoodCost() {
        return FOOD_COST_SPEARMAN;
    }

    @Override
    public int getWoodCost() {
        return WOOD_COST_SPEARMAN;
    }

    @Override
    public int getGoldCost() {
        return 0; // No tiene coste en oro
    }

    @Override
    public int getIronCost() {
        return IRON_COST_SPEARMAN;
    }

    @Override
    public int getManaCost() {
        return MANA_COST_SPEARMAN;
    }

    // Probabilidad de generar residuos (específica de Spearman)
    @Override
    public int getChanceGeneratingWaste() {
        return CHANCE_GENERATNG_WASTE_SPEARMAN;
    }

    // Probabilidad de atacar dos veces (específica de Spearman)
    @Override
    public int getChanceAttackAgain() {
        return CHANCE_ATTACK_AGAIN_SPEARMAN;
    }
}
