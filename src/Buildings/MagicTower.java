package src.Buildings;

import src.Excepciones.BuildingException;
import src.Interfaces.Variables;

public class MagicTower implements Variables {
    private int level;

    public MagicTower() {
        this.level = 0;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() throws BuildingException {
        this.level++;
    }

    // Costes basados en la interfaz Variables
    public int getFoodCost() {
        return FOOD_COST_MAGICTOWER + (level * NEXT_LEVEL_FOOD_COST_MAGICTOWER);
    }

    public int getWoodCost() {
        return WOOD_COST_MAGICTOWER + (level * NEXT_LEVEL_WOOD_COST_MAGICTOWER);
    }

    public int getIronCost() {
        return IRON_COST_MAGICTOWER + (level * NEXT_LEVEL_IRON_COST_MAGICTOWER);
    }
    
    public int getManaCost() {
        return MANA_COST_MAGICTOWER + (level * NEXT_LEVEL_MANA_COST_MAGICTOWER);
    }
}