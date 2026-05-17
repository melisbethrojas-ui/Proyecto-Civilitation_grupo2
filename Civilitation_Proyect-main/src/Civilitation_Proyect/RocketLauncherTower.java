package Civilitation_Proyect;

public class RocketLauncherTower extends DefenseUnit {

    public RocketLauncherTower(int technologyArmorLevel, int technologyAttackLevel) {
        super(ARMOR_ROCKETLAUNCHERTOWER, BASE_DAMAGE_ROCKETLAUNCHERTOWER);
        this.armor += (ARMOR_ROCKETLAUNCHERTOWER * technologyArmorLevel * PLUS_ARMOR_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY) / 100;
        this.initialArmor = this.armor;
        this.baseDamage += (BASE_DAMAGE_ROCKETLAUNCHERTOWER * technologyAttackLevel * PLUS_ATTACK_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY) / 100;
    }

    public RocketLauncherTower() {
        super(ARMOR_ROCKETLAUNCHERTOWER, BASE_DAMAGE_ROCKETLAUNCHERTOWER);
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
    public int getFoodCost() { return FOOD_COST_ROCKETLAUNCHERTOWER; }
    @Override
    public int getWoodCost() { return WOOD_COST_ROCKETLAUNCHERTOWER; }
    @Override
    public int getIronCost() { return IRON_COST_ROCKETLAUNCHERTOWER; }
    @Override
    public int getManaCost() { return MANA_COST_ROCKETLAUNCHERTOWER; }
    @Override
    public int getChanceGeneratinWaste() { return CHANCE_GENERATING_WASTE_ROCKETLAUNCHERTOWER; }
    @Override
    public int getChanceAttackAgain() { return CHANCE_ATTACK_AGAIN_ROCKETLAUNCHERTOWER; }
}