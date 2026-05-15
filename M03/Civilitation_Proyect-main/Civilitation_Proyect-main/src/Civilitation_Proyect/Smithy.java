package Civilitation_Proyect;

public class Smithy implements Variables {
    private int level;

    public Smithy() {
        this.level = 1;
    }

    public int produceIron() {
        // Usando tu variable: CIVILIZATION_IRON_GENERATED_PER_SMITHY
        return CIVILIZATION_IRON_GENERATED_PER_SMITHY;
    }

    public int getLevel() {
        return level;
    }

    public void upgrade() {
        this.level++;
    }
}