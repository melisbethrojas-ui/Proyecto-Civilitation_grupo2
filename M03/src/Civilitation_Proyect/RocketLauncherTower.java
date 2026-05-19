package Civilitation_Proyect;

public class RocketLauncherTower extends DefenseUnit {

    // Constructor para el jugador que calcula la tecnología 
    public RocketLauncherTower(int technologyArmorLevel, int technologyAttackLevel) {
        super(
            ARMOR_ROCKETLAUNCHERTOWER + (ARMOR_ROCKETLAUNCHERTOWER * technologyArmorLevel * PLUS_ARMOR_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY) / 100,
            BASE_DAMAGE_ROCKETLAUNCHERTOWER + (BASE_DAMAGE_ROCKETLAUNCHERTOWER * technologyAttackLevel * PLUS_ATTACK_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY) / 100
        );
    }

    // Constructor para el enemigo 
    public RocketLauncherTower() {
        super(ARMOR_ROCKETLAUNCHERTOWER, BASE_DAMAGE_ROCKETLAUNCHERTOWER);
    }
    // GETTERS DE LOS COSTOS Y PROBABILIDADES
    @Override
    public int getFoodCost() { 
        return FOOD_COST_ROCKETLAUNCHERTOWER; 
    }
    
    @Override
    public int getWoodCost() { 
        return WOOD_COST_ROCKETLAUNCHERTOWER; 
    }
    
    @Override
    public int getIronCost() { 
        return IRON_COST_ROCKETLAUNCHERTOWER; 
    }
    
    @Override
    public int getManaCost() { 
        return MANA_COST_ROCKETLAUNCHERTOWER; 
    }
    
    @Override
    public int getChanceGeneratinWaste() { 
        return CHANCE_GENERATING_WASTE_ROCKETLAUNCHERTOWER; 
    }
    
    @Override
    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_ROCKETLAUNCHERTOWER; 
    }

    // Método para los reportes escritos de las batallas 
    public String toString() {
        return "RocketLauncherTower";
    }
}