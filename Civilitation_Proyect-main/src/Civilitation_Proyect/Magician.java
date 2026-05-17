package Civilitation_Proyect;

public class Magician extends SpecialUnit {

    public Magician() {
        super(Variables.BASE_DAMAGE_MAGICIAN);
    }

    @Override
    public int attack() {
        return this.baseDamage;
    }

    @Override
    public int getFoodCost() { return Variables.FOOD_COST_MAGICIAN; }
    @Override
    public int getWoodCost() { return Variables.WOOD_COST_MAGICIAN; }
    @Override
    public int getIronCost() { return Variables.IRON_COST_MAGICIAN; }
    @Override
    public int getManaCost() { return Variables.MANA_COST_MAGICIAN; }
    @Override
    public int getChanceGeneratinWaste() { return Variables.CHANCE_GENERATING_WASTE_MAGICIAN; }
    @Override
    public int getChanceAttackAgain() { return Variables.CHANCE_ATTACK_AGAIN_MAGICIAN; }
}