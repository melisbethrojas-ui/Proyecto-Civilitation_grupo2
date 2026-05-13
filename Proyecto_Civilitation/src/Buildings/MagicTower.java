package Buildings;

import units.Variables;

public class MagicTower implements Variables {
    private int level;

    public MagicTower() {
        this.level = 0;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        this.level++;
    }
}