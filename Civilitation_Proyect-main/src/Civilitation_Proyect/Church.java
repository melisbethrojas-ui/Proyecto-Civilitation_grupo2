package Civilitation_Proyect;

public class Church {
    private int level;

    public Church() {
        this.level = 0; // Empieza sin construir
    }

    public int getLevel() { return level; }
    public void upgrade() { this.level++; }
}