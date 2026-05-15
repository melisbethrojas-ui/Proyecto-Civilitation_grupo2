public class Spearman extends AttackUnit {

    private static final int ARMOR = 35;
    private static final int DAMAGE = 25;

    public Spearman() {
        super(ARMOR, DAMAGE);
    }

    @Override
    public int getWoodCost() { return 25; }

    @Override
    public int getFoodCost() { return 25; }

    @Override
    public int getIronCost() { return 15; }

    @Override
    public boolean canAttackAgain() {
        return Math.random() < 0.15;
    }

    @Override
    public boolean generatesWaste() {
        return Math.random() < 0.25;
    }
}
