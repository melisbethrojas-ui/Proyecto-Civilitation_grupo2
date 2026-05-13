package Buildings;

import units.Variables;

public class Farm implements Variables {
    private int level;

    public Farm() {
        this.level = 0;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        this.level++;
    }
}