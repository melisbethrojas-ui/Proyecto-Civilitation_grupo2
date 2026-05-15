public class Civilization {

    private int wood = 200;
    private int food = 200;
    private int iron = 200;
    private int mana = 0;

    private Farm farm;
    private Carpentry carpentry;
    private Smithy smithy;
    private MagicTower magicTower;
    private Church church;

    private MilitaryUnit[] army = new MilitaryUnit[9];

    public Civilization() {}

    public void produceResources() {
        if (farm != null) food += farm.produceFood();
        if (carpentry != null) wood += carpentry.produceWood();
        if (smithy != null) iron += smithy.produceIron();
        if (magicTower != null) mana += magicTower.produceMana();
    }

    public void newFarm() throws BuildingException, ResourceException {
        if (farm != null) throw new BuildingException("Farm ya construida");
        if (wood < 50) throw new ResourceException("No hay madera suficiente");
        wood -= 50;
        farm = new Farm();
    }

    public void newCarpentry() throws BuildingException, ResourceException {
        if (carpentry != null) throw new BuildingException("Carpentry ya construida");
        if (wood < 60) throw new ResourceException("No hay madera suficiente");
        wood -= 60;
        carpentry = new Carpentry();
    }

    public void newSmithy() throws BuildingException, ResourceException {
        if (smithy != null) throw new BuildingException("Smithy ya construida");
        if (wood < 80 || iron < 20) throw new ResourceException("Recursos insuficientes");
        wood -= 80;
        iron -= 20;
        smithy = new Smithy();
    }

    public void newMagicTower() throws BuildingException, ResourceException {
        if (magicTower != null) throw new BuildingException("MagicTower ya construida");
        if (wood < 70 || iron < 30) throw new ResourceException("Recursos insuficientes");
        wood -= 70;
        iron -= 30;
        magicTower = new MagicTower();
    }

    public void newChurch() throws BuildingException, ResourceException {
        if (church != null) throw new BuildingException("Church ya construida");
        if (wood < 40) throw new ResourceException("No hay madera suficiente");
        wood -= 40;
        church = new Church();
    }

    private void payUnit(MilitaryUnit u) throws ResourceException {
        if (wood < u.getWoodCost() || food < u.getFoodCost() || iron < u.getIronCost())
            throw new ResourceException("Recursos insuficientes");
        wood -= u.getWoodCost();
        food -= u.getFoodCost();
        iron -= u.getIronCost();
    }

    public void newSwordsman(int pos) throws ResourceException {
        Swordsman u = new Swordsman();
        payUnit(u);
        army[pos] = u;
    }

    public void printStats() {
        System.out.println("=== Civilization Stats ===");
        System.out.println("Wood: " + wood);
        System.out.println("Food: " + food);
        System.out.println("Iron: " + iron);
        System.out.println("Mana: " + mana);
    }

    public MilitaryUnit getUnit(int pos) {
        return army[pos];
    }
}
