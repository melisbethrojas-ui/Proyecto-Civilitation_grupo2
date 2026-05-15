public class Swordsman extends AttackUnit implements Variables {

    private static final int ARMOR = 40;
    private static final int DAMAGE = 20;

    public Swordsman() {
        super(ARMOR, DAMAGE);
    }

    @Override
    public int getWoodCost() { return 30; }

    @Override
    public int getFoodCost() { return 20; }

    @Override
    public int getIronCost() { return 10; }

    @Override
    public boolean canAttackAgain() {
        return Math.random() < 0.20; // 20%
    }

    @Override
    public boolean generatesWaste() {
        return Math.random() < 0.30; // 30%
    }
}
