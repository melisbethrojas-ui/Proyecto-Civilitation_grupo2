package Civilitation_Proyect;

public class Crossbow extends AttackUnit {

    // Constructor con tecnología
    public Crossbow(int technologyArmorLevel, int technologyAttackLevel) {
        super(
            ARMOR_CROSSBOW + (ARMOR_CROSSBOW * technologyArmorLevel * PLUS_ARMOR_CROSSBOW_BY_TECHNOLOGY) / 100,
            BASE_DAMAGE_CROSSBOW + (BASE_DAMAGE_CROSSBOW * technologyAttackLevel * PLUS_ATTACK_CROSSBOW_BY_TECHNOLOGY) / 100
        );
    }

    // Constructor sin tecnología
    public Crossbow() {
        super(ARMOR_CROSSBOW, BASE_DAMAGE_CROSSBOW);
    }

    // Getters de costos
    @Override
    public int getFoodCost() { 
        return FOOD_COST_CROSSBOW; 
    }
    
    @Override
    public int getWoodCost() { 
        return WOOD_COST_CROSSBOW; 
    }
    
    @Override
    public int getIronCost() { 
        return IRON_COST_CROSSBOW; 
    }
    
    @Override
    public int getManaCost() { 
        return MANA_COST_CROSSBOW; 
    }

    // Getters de probabilidades
    @Override
    public int getChanceGeneratinWaste() { 
        return CHANCE_GENERATING_WASTE_CROSSBOW; 
    }
    
    @Override
    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_CROSSBOW; 
    }

    // Método toString
    @Override
    public String toString() {
        return "Crossbow";
    }
}