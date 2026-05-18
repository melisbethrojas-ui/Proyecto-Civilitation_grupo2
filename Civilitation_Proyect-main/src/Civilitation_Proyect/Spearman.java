package Civilitation_Proyect;

public class Spearman extends AttackUnit {

    // Constructor principal que calcula la tecnología directamente en el 'super'
    public Spearman(int technologyArmorLevel, int technologyAttackLevel) {
        super(
            ARMOR_SPEARMAN + (ARMOR_SPEARMAN * technologyArmorLevel * PLUS_ARMOR_SPEARMAN_BY_TECHNOLOGY) / 100,
            BASE_DAMAGE_SPEARMAN + (BASE_DAMAGE_SPEARMAN * technologyAttackLevel * PLUS_ATTACK_SPEARMAN_BY_TECHNOLOGY) / 100
        );
    }

    // Constructor sin tecnología (para enemigos base o pruebas)
    public Spearman() {
        super(ARMOR_SPEARMAN, BASE_DAMAGE_SPEARMAN);
    }

    // El método attack() YA NO HACE FALTA AQUÍ.
    // Se hereda automáticamente de AttackUnit con la fórmula correcta.

    // Métodos obligatorios que devuelven los costes y probabilidades específicos del Spearman
    @Override
    public int getFoodCost() { 
        return FOOD_COST_SPEARMAN; 
    }
    
    @Override
    public int getWoodCost() { 
        return WOOD_COST_SPEARMAN; 
    }
    
    @Override
    public int getIronCost() { 
        return IRON_COST_SPEARMAN; 
    }
    
    @Override
    public int getManaCost() { 
        return MANA_COST_SPEARMAN; 
    }
    
    @Override
    public int getChanceGeneratinWaste() { 
        return CHANCE_GENERATING_WASTE_SPEARMAN; 
    }
    
    @Override
    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_SPEARMAN; 
    }

    // Método para los reportes escritos de las batallas (exigido en el PDF)
    @Override
    public String toString() {
        return "Spearman";
    }
}