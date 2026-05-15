public class Catapult extends DefenseUnit {

    private static final int ARMOR = 70;
    private static final int DAMAGE = 30;

    public Catapult() {
        super(ARMOR, DAMAGE);
    }

    @Override
    public int getWoodCost() { return 80; }

    @Override
    public int getFoodCost() { return 20; }

    @Override
    public int getIronCost() { return 30; }
}
