package Civilitation_Proyect;

public class Catapult extends DefenseUnit {

    public Catapult(int technologyArmorLevel, int technologyAttackLevel) {
        super(ARMOR_CATAPULT, BASE_DAMAGE_CATAPULT);
        this.armor += (ARMOR_CATAPULT * technologyArmorLevel * PLUS_ARMOR_CATAPULT_BY_TECHNOLOGY) / 100;
        this.initialArmor = this.armor;
        this.baseDamage += (BASE_DAMAGE_CATAPULT * technologyAttackLevel * PLUS_ATTACK_CATAPULT_BY_TECHNOLOGY) / 100;
    }

    public Catapult() {
        super(ARMOR_CATAPULT, BASE_DAMAGE_CATAPULT);
    }

    @Override
    public int attack() {
        int totalDamage = this.baseDamage;
        // Incremento por experiencia (4% por punto)
        totalDamage += (this.baseDamage * this.experience * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT) / 100;
        // Incremento por santificación (7%)
        if (this.isSanctified()) {
            totalDamage += (this.baseDamage * PLUS_ATTACK_UNIT_SANCTIFIED) / 100;
        }
        return totalDamage;
    }

    @Override
    public int getFoodCost() { return FOOD_COST_CATAPULT; }
    @Override
    public int getWoodCost() { return WOOD_COST_CATAPULT; }
    @Override
    public int getIronCost() { return IRON_COST_CATAPULT; }
    @Override
    public int getManaCost() { return MANA_COST_CATAPULT; }
    @Override
    public int getChanceGeneratinWaste() { return CHANCE_GENERATING_WASTE_CATAPULT; }
    @Override
    public int getChanceAttackAgain() { return CHANCE_ATTACK_AGAIN_CATAPULT; }
}