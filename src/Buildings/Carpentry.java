package src.Buildings;

import src.Interfaces.Variables;

public class Carpentry implements Variables {
    private int level;

    public Carpentry() {
        this.level = 0;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        this.level++;
    }

    public int getWoodCost() {
        return WOOD_COST_CARPENTRY + (level * 300);
    }
}