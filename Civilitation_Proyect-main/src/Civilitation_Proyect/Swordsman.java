package Civilitation_Proyect;

public class Swordsman extends AttackUnit {

    // Constructor para tu civilización (aplica tecnologías)
    public Swordsman(int technologyArmorLevel, int technologyAttackLevel) {
        super(Variables.ARMOR_SWORDSMAN, Variables.BASE_DAMAGE_SWORDSMAN);
        
        // Aplicamos el porcentaje de mejora tecnológica
        this.armor += (Variables.ARMOR_SWORDSMAN * technologyArmorLevel * Variables.PLUS_ARMOR_SWORDSMAN_BY_TECHNOLOGY) / 100;
        this.initialArmor = this.armor;
        this.baseDamage += (Variables.BASE_DAMAGE_SWORDSMAN * technologyAttackLevel * Variables.PLUS_ATTACK_SWORDSMAN_BY_TECHNOLOGY) / 100;
    }

    // Constructor para el enemigo (sin tecnologías)
    public Swordsman() {
        super(Variables.ARMOR_SWORDSMAN, Variables.BASE_DAMAGE_SWORDSMAN);
    }

    @Override
    public int attack() {
        return this.baseDamage;
    }

    // Métodos obligatorios de la interfaz (Valores fijos de Variables)
    @Override
    public int getFoodCost() { return Variables.FOOD_COST_SWORDSMAN; }
    @Override
    public int getWoodCost() { return Variables.WOOD_COST_SWORDSMAN; }
    @Override
    public int getIronCost() { return Variables.IRON_COST_SWORDSMAN; }
    @Override
    public int getManaCost() { return Variables.MANA_COST_SWORDSMAN; }
    @Override
    public int getChanceGeneratinWaste() { return Variables.CHANCE_GENERATING_WASTE_SWORDSMAN; }
    @Override
    public int getChanceAttackAgain() { return Variables.CHANCE_ATTACK_AGAIN_SWORDSMAN; }
}