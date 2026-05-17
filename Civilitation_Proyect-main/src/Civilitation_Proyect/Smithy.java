package Civilitation_Proyect;

public class Smithy {
    private int level;

    public Smithy() {
        this.level = 1;
    }

    public int getLevel() { return level; }
    public void upgrade() { this.level++; }
}