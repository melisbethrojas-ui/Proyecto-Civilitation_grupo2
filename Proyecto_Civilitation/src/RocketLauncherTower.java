public class RocketLauncherTower extends DefenseUnit {

    private static final int ARMOR = 100;
    private static final int DAMAGE = 45;

    public RocketLauncherTower() {
        super(ARMOR, DAMAGE);
    }

    @Override
    public int getWoodCost() { return 100; }

    @Override
    public int getFoodCost() { return 0; }

    @Override
    public int getIronCost() { return 50; }
}
