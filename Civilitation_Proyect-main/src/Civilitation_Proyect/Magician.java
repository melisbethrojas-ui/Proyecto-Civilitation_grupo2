package Civilitation_Proyect;

public class Magician extends SpecialUnit {

    // Tu constructor con niveles de tecnología
    public Magician(int technologyArmorLevel, int technologyAttackLevel) {
        super(0, BASE_DAMAGE_MAGICIAN); // Corregido: ya no lleva Variables.
        
        this.armor = 0;
        this.initialArmor = 0;
        
        // Aplicamos el incremento de daño tecnológico limpio
        this.baseDamage += (BASE_DAMAGE_MAGICIAN * technologyAttackLevel * PLUS_ATTACK_MAGICIAN_BY_TECHNOLOGY) / 100;
    }

    // Tu constructor para el enemigo
    public Magician() {
        super(0, BASE_DAMAGE_MAGICIAN);
        this.armor = 0;
        this.initialArmor = 0;
    }

    @Override
    public int attack() {
        int totalDamage = this.baseDamage;
        // Incremento por experiencia en batalla (4%)
        totalDamage += (this.baseDamage * this.experience * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT) / 100;
        return totalDamage;
    }

    @Override
    public int getFoodCost() { return FOOD_COST_MAGICIAN; }
    @Override
    public int getWoodCost() { return WOOD_COST_MAGICIAN; }
    @Override
    public int getIronCost() { return IRON_COST_MAGICIAN; }
    @Override
    public int getManaCost() { return MANA_COST_MAGICIAN; }
    @Override
    public int getChanceGeneratinWaste() { return CHANCE_GENERATING_WASTE_MAGICIAN; }
    @Override
    public int getChanceAttackAgain() { return CHANCE_ATTACK_AGAIN_MAGICIAN; }
}