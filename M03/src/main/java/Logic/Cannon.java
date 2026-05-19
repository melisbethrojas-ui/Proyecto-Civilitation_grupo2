package Logic;

public class Cannon extends AttackUnit {

    // Constructor principal que calcula la tecnología directamente en el 'super'
    public Cannon(int technologyArmorLevel, int technologyAttackLevel) {
        super(
            ARMOR_CANNON + (ARMOR_CANNON * technologyArmorLevel * PLUS_ARMOR_CANNON_BY_TECHNOLOGY) / 100,
            BASE_DAMAGE_CANNON + (BASE_DAMAGE_CANNON * technologyAttackLevel * PLUS_ATTACK_CANNON_BY_TECHNOLOGY) / 100
        );
    }

    // Constructor sin tecnología 
    public Cannon() {
        super(ARMOR_CANNON, BASE_DAMAGE_CANNON);
    }

    // Getters de costos
    // Métodos que devuelven los costes y probabilidades específicos del Cannon
    @Override
    public int getFoodCost() { 
        return FOOD_COST_CANNON; 
    }
    
    @Override
    public int getWoodCost() { 
        return WOOD_COST_CANNON; 
    }
    
    @Override
    public int getIronCost() { 
        return IRON_COST_CANNON; 
    }
    
    @Override
    public int getManaCost() { 
        return MANA_COST_CANNON; 
    }
     // Getters de probabilidades
    @Override
    public int getChanceGeneratinWaste() { 
        return CHANCE_GENERATING_WASTE_CANNON; 
    }
    
    @Override
    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_CANNON; 
    }

    // Método para los reportes escritos de las batallas 
    @Override
    public String toString() {
        return "Cannon";
    }
}