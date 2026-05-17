package Civilitation_Proyect;

public class Priest extends SpecialUnit {

    // Tu constructor con niveles de tecnología
    public Priest(int technologyArmorLevel, int technologyAttackLevel) {
        super(0, BASE_DAMAGE_PRIEST); // Corregido: ya no lleva Variables.
        this.armor = 0;
        this.initialArmor = 0;
    }

    // Tu constructor para el enemigo
    public Priest() {
        super(0, BASE_DAMAGE_PRIEST);
        this.armor = 0;
        this.initialArmor = 0;
    }

    @Override
    public int attack() {
        // El sacerdote no genera daño físico directo
        return 0; 
    }

    @Override
    public int getFoodCost() { return FOOD_COST_PRIEST; }
    @Override
    public int getWoodCost() { return WOOD_COST_PRIEST; }
    @Override
    public int getIronCost() { return IRON_COST_PRIEST; }
    @Override
    public int getManaCost() { return MANA_COST_PRIEST; }
    @Override
    public int getChanceGeneratinWaste() { return CHANCE_GENERATING_WASTE_PRIEST; }
    @Override
    public int getChanceAttackAgain() { return CHANCE_ATTACK_AGAIN_PRIEST; }
}