package Civilitation_Proyect;

public class Farm {
    private int level;

    public Farm() {
        this.level = 1; // Empieza a nivel 1
    }

    public int getLevel() { return level; }
    public void upgrade() { this.level++; }
}