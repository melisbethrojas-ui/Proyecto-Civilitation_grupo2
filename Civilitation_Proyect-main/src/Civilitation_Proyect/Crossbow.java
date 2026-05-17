package Civilitation_Proyect;
public class Crossbow extends AttackUnit {

    public Crossbow(int technologyArmorLevel, int technologyAttackLevel) {
        super(Variables.ARMOR_CROSSBOW, Variables.BASE_DAMAGE_CROSSBOW);
        this.armor += (Variables.ARMOR_CROSSBOW * technologyArmorLevel * Variables.PLUS_ARMOR_CROSSBOW_BY_TECHNOLOGY) / 100;
        this.initialArmor = this.armor;
        this.baseDamage += (Variables.BASE_DAMAGE_CROSSBOW * technologyAttackLevel * Variables.PLUS_ATTACK_CROSSBOW_BY_TECHNOLOGY) / 100;
    }

    public Crossbow() {
        super(Variables.ARMOR_CROSSBOW, Variables.BASE_DAMAGE_CROSSBOW);
    }

    @Override
    public int attack() {
        return this.baseDamage;
    }

    @Override
    public int getFoodCost() { return Variables.FOOD_COST_CROSSBOW; }
    @Override
    public int getWoodCost() { return Variables.WOOD_COST_CROSSBOW; }
    @Override
    public int getIronCost() { return Variables.IRON_COST_CROSSBOW; }
    @Override
    public int getManaCost() { return Variables.MANA_COST_CROSSBOW; }
    @Override
    public int getChanceGeneratinWaste() { return Variables.CHANCE_GENERATING_WASTE_CROSSBOW; }
    @Override
    public int getChanceAttackAgain() { return Variables.CHANCE_ATTACK_AGAIN_CROSSBOW; }
}