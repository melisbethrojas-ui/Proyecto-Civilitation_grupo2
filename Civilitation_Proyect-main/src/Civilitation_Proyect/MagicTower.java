package Civilitation_Proyect;

public class MagicTower {
    private int level;

    public MagicTower() {
        this.level = 0; // Empieza sin construir
    }

    public int getLevel() { return level; }
    public void upgrade() { this.level++; }
}