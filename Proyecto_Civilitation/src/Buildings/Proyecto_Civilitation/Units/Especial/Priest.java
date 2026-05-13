package Proyecto_Civilitation.src.Units.Especial;

public class Priest extends SpecialUnit {

    public Priest(int techArmorLevel, int techAttackLevel) {
        this.armor = ARMOR_PRIEST + (techArmorLevel * PLUS_ARMOR_PRIEST_BY_TECHNOLOGY) * ARMOR_PRIEST / 100;
        this.baseDamage = BASE_DAMAGE_PRIEST + (techAttackLevel * PLUS_ATTACK_PRIEST_BY_TECHNOLOGY) * BASE_DAMAGE_PRIEST / 100;
        this.initialArmor = this.armor;
        this.experience = 0;
    }

    @Override
    public int attack() {
        return this.baseDamage;
    }

    @Override
    public int getFoodCost() { return FOOD_COST_PRIEST; }
    @Override
    public int getWoodCost() { return WOOD_COST_PRIEST; }
    @Override
    public int getIronCost() { return IRON_COST_PRIEST; }
    @Override
    public int getManaCost() { return MANA_COST_PRIEST; }

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