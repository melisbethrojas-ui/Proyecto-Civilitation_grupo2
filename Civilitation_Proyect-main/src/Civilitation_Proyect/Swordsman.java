package Civilitation_Proyect;

public class Swordsman extends AttackUnit {

    public Swordsman(int technologyArmorLevel, int technologyAttackLevel) {
        super(ARMOR_SWORDSMAN, BASE_DAMAGE_SWORDSMAN);
        this.armor += (ARMOR_SWORDSMAN * technologyArmorLevel * PLUS_ARMOR_SWORDSMAN_BY_TECHNOLOGY) / 100;
        this.initialArmor = this.armor;
        this.baseDamage += (BASE_DAMAGE_SWORDSMAN * technologyAttackLevel * PLUS_ATTACK_SWORDSMAN_BY_TECHNOLOGY) / 100;
    }

    public Swordsman() {
        super(ARMOR_SWORDSMAN, BASE_DAMAGE_SWORDSMAN);
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
    public int getFoodCost() { return FOOD_COST_SWORDSMAN; }
    @Override
    public int getWoodCost() { return WOOD_COST_SWORDSMAN; }
    @Override
    public int getIronCost() { return IRON_COST_SWORDSMAN; }
    @Override
    public int getManaCost() { return MANA_COST_SWORDSMAN; }
    @Override
    public int getChanceGeneratinWaste() { return CHANCE_GENERATING_WASTE_SWORDSMAN; }
    @Override
    public int getChanceAttackAgain() { return CHANCE_ATTACK_AGAIN_SWORDSMAN; }
}