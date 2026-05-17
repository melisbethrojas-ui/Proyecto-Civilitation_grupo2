package Civilitation_Proyect;

public class Priest extends SpecialUnit {

    public Priest() {
        super(Variables.BASE_DAMAGE_PRIEST);
    }

    @Override
    public int attack() {
        return this.baseDamage; // Devolverá 0 ya que no hace daño directo
    }

    @Override
    public int getFoodCost() { return Variables.FOOD_COST_PRIEST; }
    @Override
    public int getWoodCost() { return Variables.WOOD_COST_PRIEST; }
    @Override
    public int getIronCost() { return Variables.IRON_COST_PRIEST; }
    @Override
    public int getManaCost() { return Variables.MANA_COST_PRIEST; }
    @Override
    public int getChanceGeneratinWaste() { return Variables.CHANCE_GENERATING_WASTE_PRIEST; }
    @Override
    public int getChanceAttackAgain() { return Variables.CHANCE_ATTACK_AGAIN_PRIEST; }
}