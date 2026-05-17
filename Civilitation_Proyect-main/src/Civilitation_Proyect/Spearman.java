package Civilitation_Proyect;

public class Spearman extends AttackUnit {

    public Spearman(int technologyArmorLevel, int technologyAttackLevel) {
        super(ARMOR_SPEARMAN, BASE_DAMAGE_SPEARMAN);
        this.armor += (ARMOR_SPEARMAN * technologyArmorLevel * PLUS_ARMOR_SPEARMAN_BY_TECHNOLOGY) / 100;
        this.initialArmor = this.armor;
        this.baseDamage += (BASE_DAMAGE_SPEARMAN * technologyAttackLevel * PLUS_ATTACK_SPEARMAN_BY_TECHNOLOGY) / 100;
    }

    public Spearman() {
        super(ARMOR_SPEARMAN, BASE_DAMAGE_SPEARMAN);
    }

    @Override
    public int attack() {
        int totalDamage = this.baseDamage;
        totalDamage += (this.baseDamage * this.experience * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT) / 100;
        if (this.isSanctified()) {
            totalDamage += (this.baseDamage * PLUS_ATTACK_UNIT_SANCTIFIED) / 100;
        }
        return totalDamage;
    }

    @Override
    public int getFoodCost() { return FOOD_COST_SPEARMAN; }
    @Override
    public int getWoodCost() { return WOOD_COST_SPEARMAN; }
    @Override
    public int getIronCost() { return IRON_COST_SPEARMAN; }
    @Override
    public int getManaCost() { return MANA_COST_SPEARMAN; }
    @Override
    public int getChanceGeneratinWaste() { return CHANCE_GENERATING_WASTE_SPEARMAN; }
    @Override
    public int getChanceAttackAgain() { return CHANCE_ATTACK_AGAIN_SPEARMAN; }
}