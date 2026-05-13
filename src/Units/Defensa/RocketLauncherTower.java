package src.Units.Defensa;


public class RocketLauncherTower extends DefenseUnit {

    // Constructor: usa los valores definidos en Variables
    public RocketLauncherTower() {
        super(ARMOR_ROCKETLAUNCHERTOWER, BASE_DAMAGE_ROCKETLAUNCHERTOWER);
    }

    // Costes específicos de esta defensa
    @Override
    public int getFoodCost() {
        return FOOD_COST_ROCKETLAUNCHERTOWER;
    }

    @Override
    public int getWoodCost() {
        return WOOD_COST_ROCKETLAUNCHERTOWER;
    }

    @Override
    public int getGoldCost() {
        return 0; // No usa oro
    }

    @Override
    public int getIronCost() {
        return IRON_COST_ROCKETLAUNCHERTOWER;
    }

    @Override
    public int getManaCost() {
        return MANA_COST_ROCKETLAUNCHERTOWER;
    }

    // Probabilidad de generar residuos (específica de RocketLauncherTower)
    @Override
    public int getChanceGeneratingWaste() {
        return CHANCE_GENERATNG_WASTE_ROCKETLAUNCHERTOWER;
    }

    // Probabilidad de atacar dos veces (específica de RocketLauncherTower)
    @Override
    public int getChanceAttackAgain() {
        return CHANCE_ATTACK_AGAIN_ROCKETLAUNCHERTOWER;
    }
}
