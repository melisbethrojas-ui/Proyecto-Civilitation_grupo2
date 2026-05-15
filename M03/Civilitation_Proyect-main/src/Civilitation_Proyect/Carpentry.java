package Civilitation_Proyect;

public class Carpentry implements Variables {
    private int level;

    public Carpentry() {
        this.level = 1;
    }

    public int produceWood() {
        // Usando tu variable: CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY
        return CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY;
    }

    public int getLevel() {
        return level;
    }

    public void upgrade() {
        this.level++;
    }
}