package Civilitation_Proyect;

public class Spearman extends AttackUnit {

    public Spearman(int technologyArmorLevel, int technologyAttackLevel) {
        super(Variables.ARMOR_SPEARMAN, Variables.BASE_DAMAGE_SPEARMAN);
        this.armor += (Variables.ARMOR_SPEARMAN * technologyArmorLevel * Variables.PLUS_ARMOR_SPEARMAN_BY_TECHNOLOGY) / 100;
        this.initialArmor = this.armor;
        this.baseDamage += (Variables.BASE_DAMAGE_SPEARMAN * technologyAttackLevel * Variables.PLUS_ATTACK_SPEARMAN_BY_TECHNOLOGY) / 100;
    }

    public Spearman() {
        super(Variables.ARMOR_SPEARMAN, Variables.BASE_DAMAGE_SPEARMAN);
    }

    @Override
    public int attack() {
        return this.baseDamage;
    }

    @Override
    public int getFoodCost() { return Variables.FOOD_COST_SPEARMAN; }
    @Override
    public int getWoodCost() { return Variables.WOOD_COST_SPEARMAN; }
    @Override
    public int getIronCost() { return Variables.IRON_COST_SPEARMAN; }
    @Override
    public int getManaCost() { return Variables.MANA_COST_SPEARMAN; }
    @Override
    public int getChanceGeneratinWaste() { return Variables.CHANCE_GENERATING_WASTE_SPEARMAN; }
    @Override
    public int getChanceAttackAgain() { return Variables.CHANCE_ATTACK_AGAIN_SPEARMAN; }
}