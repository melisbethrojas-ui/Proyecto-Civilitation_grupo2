

import units.Variables;

public class Smithy implements Variables {
    private int level;

    public Smithy() {
        this.level = 0;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        this.level++;
    }
}