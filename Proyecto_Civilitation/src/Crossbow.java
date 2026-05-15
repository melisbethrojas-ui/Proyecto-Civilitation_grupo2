public class Crossbow extends AttackUnit {

    private static final int ARMOR = 25;
    private static final int DAMAGE = 35;

    public Crossbow() {
        super(ARMOR, DAMAGE);
    }

    @Override
    public int getWoodCost() { return 40; }

    @Override
    public int getFoodCost() { return 15; }

    @Override
    public int getIronCost() { return 20; }

    @Override
    public boolean canAttackAgain() {
        return Math.random() < 0.10;
    }

    @Override
    public boolean generatesWaste() {
        return Math.random() < 0.20;
    }
}
