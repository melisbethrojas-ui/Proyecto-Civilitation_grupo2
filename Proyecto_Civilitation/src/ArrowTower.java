public class ArrowTower extends DefenseUnit {

    private static final int ARMOR = 80;
    private static final int DAMAGE = 15;

    public ArrowTower() {
        super(ARMOR, DAMAGE);
    }

    @Override
    public int getWoodCost() { return 70; }

    @Override
    public int getFoodCost() { return 0; }

    @Override
    public int getIronCost() { return 20; }
}
