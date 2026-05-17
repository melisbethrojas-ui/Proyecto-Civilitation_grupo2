package Civilitation_Proyect;

public class ArrowTower extends DefenseUnit {

    // Constructor para tu civilización (aplica tecnologías)
    public ArrowTower(int technologyArmorLevel, int technologyAttackLevel) {
        super(Variables.ARMOR_ARROWTOWER, Variables.BASE_DAMAGE_ARROWTOWER);
        this.armor += (Variables.ARMOR_ARROWTOWER * technologyArmorLevel * Variables.PLUS_ARMOR_ARROWTOWER_BY_TECHNOLOGY) / 100;
        this.initialArmor = this.armor;
        this.baseDamage += (Variables.BASE_DAMAGE_ARROWTOWER * technologyAttackLevel * Variables.PLUS_ATTACK_ARROWTOWER_BY_TECHNOLOGY) / 100;
    }

    // Constructor para el enemigo (sin tecnologías)
    public ArrowTower() {
        super(Variables.ARMOR_ARROWTOWER, Variables.BASE_DAMAGE_ARROWTOWER);
    }

    @Override
    public int attack() {
        return this.baseDamage;
    }

    @Override
    public int getFoodCost() { return Variables.FOOD_COST_ARROWTOWER; }
    @Override
    public int getWoodCost() { return Variables.WOOD_COST_ARROWTOWER; }
    @Override
    public int getIronCost() { return Variables.IRON_COST_ARROWTOWER; }
    @Override
    public int getManaCost() { return Variables.MANA_COST_ARROWTOWER; }
    @Override
    public int getChanceGeneratinWaste() { return Variables.CHANCE_GENERATING_WASTE_ARROWTOWER; }
    @Override
    public int getChanceAttackAgain() { return Variables.CHANCE_ATTACK_AGAIN_ARROWTOWER; }
}