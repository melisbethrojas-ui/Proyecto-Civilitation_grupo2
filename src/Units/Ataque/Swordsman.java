package src.Units.Ataque;

public class Swordsman extends AttackUnit {

    // Constructor: usa los valores de ARMOR y BASE_DAMAGE definidos en Variables
    public Swordsman() {
        super(ARMOR_SWORDSMAN, BASE_DAMAGE_SWORDSMAN);
    }

    // Costes específicos de esta unidad
    @Override
    public int getFoodCost() {
        return FOOD_COST_SWORDSMAN;
    }

    @Override
    public int getWoodCost() {
        return WOOD_COST_SWORDSMAN;
    }

    @Override
    public int getGoldCost() {
        return 0; // No tiene coste en oro
    }

    @Override
    public int getIronCost() {
        return IRON_COST_SWORDSMAN;
    }

    @Override
    public int getManaCost() {
        return MANA_COST_SWORDSMAN;
    }

    // Probabilidad de generar residuos (específica de Swordsman)
    @Override
    public int getChanceGeneratingWaste() {
        return CHANCE_GENERATNG_WASTE_SWORDSMAN;
    }

    // Probabilidad de atacar dos veces (específica de Swordsman)
    @Override
    public int getChanceAttackAgain() {
        return CHANCE_ATTACK_AGAIN_SWORDSMAN;
    }
}
