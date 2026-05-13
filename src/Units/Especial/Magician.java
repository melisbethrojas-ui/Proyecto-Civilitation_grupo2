package src.Units.Especial;

public class Magician extends SpecialUnit {

    public Magician(int techArmorLevel, int techAttackLevel) {
        // Cálculo de stats según nivel de tecnología
        this.armor = ARMOR_MAGICIAN + (techArmorLevel * PLUS_ARMOR_MAGICIAN_BY_TECHNOLOGY) * ARMOR_MAGICIAN / 100;
        this.baseDamage = BASE_DAMAGE_MAGICIAN + (techAttackLevel * PLUS_ATTACK_MAGICIAN_BY_TECHNOLOGY) * BASE_DAMAGE_MAGICIAN / 100;
        this.initialArmor = this.armor;
        this.experience = 0;
    }

    @Override
    public int attack() {
        return this.baseDamage;
    }

    // Métodos de coste usando la interfaz Variables
    @Override
    public int getFoodCost() { return FOOD_COST_MAGICIAN; }
    @Override
    public int getWoodCost() { return WOOD_COST_MAGICIAN; }
    @Override
    public int getIronCost() { return IRON_COST_MAGICIAN; }
    @Override
    public int getManaCost() { return MANA_COST_MAGICIAN; }

    @Override
    public int getGoldCost() {
        throw new UnsupportedOperationException("Unimplemented method 'getGoldCost'");
    }

    @Override
    public int getChanceGeneratingWaste() {
        throw new UnsupportedOperationException("Unimplemented method 'getChanceGeneratingWaste'");
    }

    @Override
    public int getChanceAttackAgain() {
        throw new UnsupportedOperationException("Unimplemented method 'getChanceAttackAgain'");
    }

    @Override
    public void addExperience(int amount) {
        throw new UnsupportedOperationException("Unimplemented method 'addExperience'");
    }

    @Override
    public boolean isAlive() {
        throw new UnsupportedOperationException("Unimplemented method 'isAlive'");
    }
}