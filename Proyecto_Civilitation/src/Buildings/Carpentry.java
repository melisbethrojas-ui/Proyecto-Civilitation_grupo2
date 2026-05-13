package Buildings;

import units.Variables;

public class Carpentry implements Variables {
    private int level;

    public Carpentry() {
        this.level = 0;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        this.level++;
    }
}