package Civilitation_Proyect;

public class Catapult extends DefenseUnit {

    public Catapult(int technologyArmorLevel, int technologyAttackLevel) {
        super(Variables.ARMOR_CATAPULT, Variables.BASE_DAMAGE_CATAPULT);
        this.armor += (Variables.ARMOR_CATAPULT * technologyArmorLevel * Variables.PLUS_ARMOR_CATAPULT_BY_TECHNOLOGY) / 100;
        this.initialArmor = this.armor;
        this.baseDamage += (Variables.BASE_DAMAGE_CATAPULT * technologyAttackLevel * Variables.PLUS_ATTACK_CATAPULT_BY_TECHNOLOGY) / 100;
    }

    public Catapult() {
        super(Variables.ARMOR_CATAPULT, Variables.BASE_DAMAGE_CATAPULT);
    }

    @Override
    public int attack() {
        return this.baseDamage;
    }

    @Override
    public int getFoodCost() { return Variables.FOOD_COST_CATAPULT; }
    @Override
    public int getWoodCost() { return Variables.WOOD_COST_CATAPULT; }
    @Override
    public int getIronCost() { return Variables.IRON_COST_CATAPULT; }
    @Override
    public int getManaCost() { return Variables.MANA_COST_CATAPULT; }
    @Override
    public int getChanceGeneratinWaste() { return Variables.CHANCE_GENERATING_WASTE_CATAPULT; }
    @Override
    public int getChanceAttackAgain() { return Variables.CHANCE_ATTACK_AGAIN_CATAPULT; }
}