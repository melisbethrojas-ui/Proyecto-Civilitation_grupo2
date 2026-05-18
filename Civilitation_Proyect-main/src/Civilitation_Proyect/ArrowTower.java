package Civilitation_Proyect;

// Clase que representa la torre de arqueros, una unidad defensiva que ataca a distancia
public class ArrowTower extends DefenseUnit {

    // Constructor para el jugador que calcula la tecnología directamente en el 'super'
    public ArrowTower(int technologyArmorLevel, int technologyAttackLevel) {
        super(
            ARMOR_ARROWTOWER + (ARMOR_ARROWTOWER * technologyArmorLevel * PLUS_ARMOR_ARROWTOWER_BY_TECHNOLOGY) / 100,
            BASE_DAMAGE_ARROWTOWER + (BASE_DAMAGE_ARROWTOWER * technologyAttackLevel * PLUS_ATTACK_ARROWTOWER_BY_TECHNOLOGY) / 100
        );
    }

    // Constructor para el enemigo (sin tecnología añadida)
    public ArrowTower() {
        super(ARMOR_ARROWTOWER, BASE_DAMAGE_ARROWTOWER);
    }

    // El método attack() YA NO HACE FALTA AQUÍ.
    // Se hereda automáticamente de DefenseUnit con la fórmula matemática correcta.

    // GETTERS DE LOS COSTOS Y PROBABILIDADES
    @Override
    public int getFoodCost() { 
        return FOOD_COST_ARROWTOWER; 
    }
    
    @Override
    public int getWoodCost() { 
        return WOOD_COST_ARROWTOWER; 
    }
    
    @Override
    public int getIronCost() { 
        return IRON_COST_ARROWTOWER; 
    }
    
    @Override
    public int getManaCost() { 
        return MANA_COST_ARROWTOWER; 
    }
    
    @Override
    public int getChanceGeneratinWaste() { 
        return CHANCE_GENERATING_WASTE_ARROWTOWER; 
    }
    
    @Override
    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_ARROWTOWER; 
    }

    // Método para los reportes escritos de las batallas exigidos en el PDF
    @Override
    public String toString() {
        return "ArrowTower";
    }
}