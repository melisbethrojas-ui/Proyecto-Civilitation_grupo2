public class Priest extends SpecialUnit {

    private static final int ARMOR = 20;
    private static final int DAMAGE = 10;

    public Priest() {
        super(ARMOR, DAMAGE);
    }

    @Override
    public int getWoodCost() { return 10; }

    @Override
    public int getFoodCost() { return 50; }

    @Override
    public int getIronCost() { return 0; }

    @Override
    public void specialAbility(MilitaryUnit target) {
        target.receiveDamage(-20); // cura
    }
}
