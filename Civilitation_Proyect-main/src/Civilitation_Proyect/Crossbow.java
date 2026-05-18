package Civilitation_Proyect;

public class Crossbow extends AttackUnit {

    // Constructor principal que calcula la tecnología directamente en el 'super'
    public Crossbow(int technologyArmorLevel, int technologyAttackLevel) {
        super(
            ARMOR_CROSSBOW + (ARMOR_CROSSBOW * technologyArmorLevel * PLUS_ARMOR_CROSSBOW_BY_TECHNOLOGY) / 100,
            BASE_DAMAGE_CROSSBOW + (BASE_DAMAGE_CROSSBOW * technologyAttackLevel * PLUS_ATTACK_CROSSBOW_BY_TECHNOLOGY) / 100
        );
    }

    // Constructor sin tecnología (para enemigos base o pruebas)
    public Crossbow() {
        super(ARMOR_CROSSBOW, BASE_DAMAGE_CROSSBOW);
    }

    // El método attack() YA NO HACE FALTA AQUÍ.
    // Se hereda automáticamente de AttackUnit con la fórmula correcta.

    // Métodos obligatorios que devuelven los costes y probabilidades específicos del Crossbow
    @Override
    public int getFoodCost() { 
        return FOOD_COST_CROSSBOW; 
    }
    
    @Override
    public int getWoodCost() { 
        return WOOD_COST_CROSSBOW; 
    }
    
    @Override
    public int getIronCost() { 
        return IRON_COST_CROSSBOW; 
    }
    
    @Override
    public int getManaCost() { 
        return MANA_COST_CROSSBOW; 
    }
    
    @Override
    public int getChanceGeneratinWaste() { 
        return CHANCE_GENERATING_WASTE_CROSSBOW; 
    }
    
    @Override
    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_CROSSBOW; 
    }

    // Método para los reportes escritos de las batallas (exigido en el PDF)
    @Override
    public String toString() {
        return "Crossbow";
    }
}