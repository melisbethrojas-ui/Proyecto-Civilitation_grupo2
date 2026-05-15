package Civilitation_Proyect;

public class Farm implements Variables {
    private int level;

    public Farm() {
        this.level = 1;
    }

    public int produceFood() {
        // Usando tu variable: CIVILIZATION_FOOD_GENERATED_PER_FARM
        return CIVILIZATION_FOOD_GENERATED_PER_FARM;
    }

    public int getLevel() {
        return level;
    }

    public void upgrade() {
        this.level++;
    }
}