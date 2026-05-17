package Civilitation_Proyect;

public class RocketLauncherTower extends DefenseUnit {

    public RocketLauncherTower(int technologyArmorLevel, int technologyAttackLevel) {
        super(Variables.ARMOR_ROCKETLAUNCHERTOWER, Variables.BASE_DAMAGE_ROCKETLAUNCHERTOWER);
        this.armor += (Variables.ARMOR_ROCKETLAUNCHERTOWER * technologyArmorLevel * Variables.PLUS_ARMOR_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY) / 100;
        this.initialArmor = this.armor;
        this.baseDamage += (Variables.BASE_DAMAGE_ROCKETLAUNCHERTOWER * technologyAttackLevel * Variables.PLUS_ATTACK_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY) / 100;
    }

    public RocketLauncherTower() {
        super(Variables.ARMOR_ROCKETLAUNCHERTOWER, Variables.BASE_DAMAGE_ROCKETLAUNCHERTOWER);
    }

    @Override
    public int attack() {
        return this.baseDamage;
    }

    @Override
    public int getFoodCost() { return Variables.FOOD_COST_ROCKETLAUNCHERTOWER; }
    @Override
    public int getWoodCost() { return Variables.WOOD_COST_ROCKETLAUNCHERTOWER; }
    @Override
    public int getIronCost() { return Variables.IRON_COST_ROCKETLAUNCHERTOWER; }
    @Override
    public int getManaCost() { return Variables.MANA_COST_ROCKETLAUNCHERTOWER; }
    @Override
    public int getChanceGeneratinWaste() { return Variables.CHANCE_GENERATING_WASTE_ROCKETLAUNCHERTOWER; }
    @Override
    public int getChanceAttackAgain() { return Variables.CHANCE_ATTACK_AGAIN_ROCKETLAUNCHERTOWER; }
}