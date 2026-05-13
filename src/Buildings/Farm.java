package src.Buildings;

import src.Interfaces.Variables;

public class Farm implements Variables {
    private int level;

    public Farm() {
        this.level = 0;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        this.level++;
    }

    // El coste de subir de nivel suele escalar, aquí un ejemplo base:
    public int getFoodCost() {
        return FOOD_COST_FARM + (level * 500); 
    }
    
    public int getWoodCost() {
        return WOOD_COST_FARM + (level * 200);
    }
}