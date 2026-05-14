import Proyecto_Civilitation.Proyecto_Civilitation.Buildings.Variables;

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