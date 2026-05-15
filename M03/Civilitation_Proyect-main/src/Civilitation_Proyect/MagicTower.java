package Civilitation_Proyect;

public class MagicTower implements Variables {
    private int level;

    public MagicTower() {
        this.level = 1;
    }

    public int produceMana() {
        // Usando tu variable: CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER
        return CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER;
    }

    public int getLevel() {
        return level;
    }

    public void upgrade() {
        this.level++;
    }
}