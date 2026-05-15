public abstract class MilitaryUnit {

    protected int armor;
    protected int damage;

    public MilitaryUnit(int armor, int damage) {
        this.armor = armor;
        this.damage = damage;
    }

    public boolean isAlive() {
        return armor > 0;
    }

    public void receiveDamage(int amount) {
        armor -= amount;
        if (armor < 0) armor = 0;
    }

    public int getDamage() {
        return damage;
    }

    public int getArmor() {
        return armor;
    }

    public abstract int getWoodCost();
    public abstract int getFoodCost();
    public abstract int getIronCost();
}
