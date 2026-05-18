package Civilitation_Proyect;

public class Swordsman extends AttackUnit {

    // Constructor principal que calcula la tecnología directamente en el 'super'
    public Swordsman(int technologyArmorLevel, int technologyAttackLevel) {
        super(
            ARMOR_SWORDSMAN + (ARMOR_SWORDSMAN * technologyArmorLevel * PLUS_ARMOR_SWORDSMAN_BY_TECHNOLOGY) / 100,
            BASE_DAMAGE_SWORDSMAN + (BASE_DAMAGE_SWORDSMAN * technologyAttackLevel * PLUS_ATTACK_SWORDSMAN_BY_TECHNOLOGY) / 100
        );
    }

    // Constructor sin tecnología (por si creas enemigos base o unidades de prueba)
    public Swordsman() {
        super(ARMOR_SWORDSMAN, BASE_DAMAGE_SWORDSMAN);
    }

    // El método attack() YA NO HACE FALTA AQUÍ. 
    // Automáticamente se hereda de AttackUnit con la misma fórmula exacta que tenías.

    // Métodos obligatorios de la interfaz que devuelven los costes y probabilidades
    @Override
    public int getFoodCost() { 
        return FOOD_COST_SWORDSMAN; 
    }
    
    @Override
    public int getWoodCost() { 
        return WOOD_COST_SWORDSMAN; 
    }
    
    @Override
    public int getIronCost() { 
        return IRON_COST_SWORDSMAN; 
    }
    
    @Override
    public int getManaCost() { 
        return MANA_COST_SWORDSMAN; 
    }
    
    @Override
    public int getChanceGeneratinWaste() { 
        return CHANCE_GENERATING_WASTE_SWORDSMAN; 
    }
    
    @Override
    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_SWORDSMAN; 
    }

    // Método para los reportes escritos de las batallas exigidos en el PDF
    @Override
    public String toString() {
        return "Swordsman";
    }
}