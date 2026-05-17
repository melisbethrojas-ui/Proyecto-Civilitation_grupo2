package Civilitation_Proyect;

public class Cannon extends AttackUnit {

    public Cannon(int technologyArmorLevel, int technologyAttackLevel) {
        super(Variables.ARMOR_CANNON, Variables.BASE_DAMAGE_CANNON);
        this.armor += (Variables.ARMOR_CANNON * technologyArmorLevel * Variables.PLUS_ARMOR_CANNON_BY_TECHNOLOGY) / 100;
        this.initialArmor = this.armor;
        this.baseDamage += (Variables.BASE_DAMAGE_CANNON * technologyAttackLevel * Variables.PLUS_ATTACK_CANNON_BY_TECHNOLOGY) / 100;
    }

    public Cannon() {
        super(Variables.ARMOR_CANNON, Variables.BASE_DAMAGE_CANNON);
    }

    @Override
    public int attack() {
        return this.baseDamage;
    }

    @Override
    public int getFoodCost() { return Variables.FOOD_COST_CANNON; }
    @Override
    public int getWoodCost() { return Variables.WOOD_COST_CANNON; }
    @Override
    public int getIronCost() { return Variables.IRON_COST_CANNON; }
    @Override
    public int getManaCost() { return Variables.MANA_COST_CANNON; }
    @Override
    public int getChanceGeneratinWaste() { return Variables.CHANCE_GENERATING_WASTE_CANNON; }
    @Override
    public int getChanceAttackAgain() { return Variables.CHANCE_ATTACK_AGAIN_CANNON; }
}