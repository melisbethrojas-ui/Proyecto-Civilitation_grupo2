public class Magician extends SpecialUnit {

    private static final int ARMOR = 30;
    private static final int DAMAGE = 40;

    public Magician() {
        super(ARMOR, DAMAGE);
    }

    @Override
    public int getWoodCost() { return 20; }

    @Override
    public int getFoodCost() { return 40; }

    @Override
    public int getIronCost() { return 10; }

    @Override
    public void specialAbility(MilitaryUnit target) {
        target.receiveDamage(20); // magia adicional
    }
}
