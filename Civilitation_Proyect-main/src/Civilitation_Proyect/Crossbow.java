package Civilitation_Proyect;

public class Crossbow extends AttackUnit {

    public Crossbow(int technologyArmorLevel, int technologyAttackLevel) {
        super(ARMOR_CROSSBOW, BASE_DAMAGE_CROSSBOW);
        this.armor += (ARMOR_CROSSBOW * technologyArmorLevel * PLUS_ARMOR_CROSSBOW_BY_TECHNOLOGY) / 100;
        this.initialArmor = this.armor;
        this.baseDamage += (BASE_DAMAGE_CROSSBOW * technologyAttackLevel * PLUS_ATTACK_CROSSBOW_BY_TECHNOLOGY) / 100;
    }

    public Crossbow() {
        super(ARMOR_CROSSBOW, BASE_DAMAGE_CROSSBOW);
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
    public int getFoodCost() { return FOOD_COST_CROSSBOW; }
    @Override
    public int getWoodCost() { return WOOD_COST_CROSSBOW; }
    @Override
    public int getIronCost() { return IRON_COST_CROSSBOW; }
    @Override
    public int getManaCost() { return MANA_COST_CROSSBOW; }
    @Override
    public int getChanceGeneratinWaste() { return CHANCE_GENERATING_WASTE_CROSSBOW; }
    @Override
    public int getChanceAttackAgain() { return CHANCE_ATTACK_AGAIN_CROSSBOW; }
}