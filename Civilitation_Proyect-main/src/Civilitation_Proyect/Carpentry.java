package Civilitation_Proyect;

public class Carpentry {
    private int level;

    public Carpentry() {
        this.level = 1;
    }

    public int getLevel() { return level; }
    public void upgrade() { this.level++; }
}