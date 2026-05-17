package Civilitation_Proyect;

public class Cannon extends AttackUnit {

    public Cannon(int technologyArmorLevel, int technologyAttackLevel) {
        super(ARMOR_CANNON, BASE_DAMAGE_CANNON);
        this.armor += (ARMOR_CANNON * technologyArmorLevel * PLUS_ARMOR_CANNON_BY_TECHNOLOGY) / 100;
        this.initialArmor = this.armor;
        this.baseDamage += (BASE_DAMAGE_CANNON * technologyAttackLevel * PLUS_ATTACK_CANNON_BY_TECHNOLOGY) / 100;
    }

    public Cannon() {
        super(ARMOR_CANNON, BASE_DAMAGE_CANNON);
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
    public int getFoodCost() { return FOOD_COST_CANNON; }
    @Override
    public int getWoodCost() { return WOOD_COST_CANNON; }
    @Override
    public int getIronCost() { return IRON_COST_CANNON; }
    @Override
    public int getManaCost() { return MANA_COST_CANNON; }
    @Override
    public int getChanceGeneratinWaste() { return CHANCE_GENERATING_WASTE_CANNON; }
    @Override
    public int getChanceAttackAgain() { return CHANCE_ATTACK_AGAIN_CANNON; }
}