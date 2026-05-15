public class Cannon extends AttackUnit {

    private static final int ARMOR = 60;
    private static final int DAMAGE = 50;

    public Cannon() {
        super(ARMOR, DAMAGE);
    }

    @Override
    public int getWoodCost() { return 60; }

    @Override
    public int getFoodCost() { return 30; }

    @Override
    public int getIronCost() { return 40; }

    @Override
    public boolean canAttackAgain() {
        return false; // No dispara dos veces
    }

    @Override
    public boolean generatesWaste() {
        return Math.random() < 0.50;
    }
}
