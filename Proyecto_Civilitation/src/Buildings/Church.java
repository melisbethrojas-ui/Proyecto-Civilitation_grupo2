package Proyecto_Civilitation.src.Buildings;

import units.Variables;

public class Church implements Variables {
    private int level;

    public Church() {
        this.level = 0;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        this.level++;
    }
}