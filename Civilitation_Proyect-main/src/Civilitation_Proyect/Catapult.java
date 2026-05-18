package Civilitation_Proyect;

public class Catapult extends DefenseUnit {

    // Constructor para el jugador que calcula la tecnología directamente en el 'super'
    public Catapult(int technologyArmorLevel, int technologyAttackLevel) {
        super(
            ARMOR_CATAPULT + (ARMOR_CATAPULT * technologyArmorLevel * PLUS_ARMOR_CATAPULT_BY_TECHNOLOGY) / 100,
            BASE_DAMAGE_CATAPULT + (BASE_DAMAGE_CATAPULT * technologyAttackLevel * PLUS_ATTACK_CATAPULT_BY_TECHNOLOGY) / 100
        );
    }

    // Constructor para el enemigo (sin tecnología añadida)
    public Catapult() {
        super(ARMOR_CATAPULT, BASE_DAMAGE_CATAPULT);
    }

    // El método attack() YA NO HACE FALTA AQUÍ.
    // Se hereda automáticamente de DefenseUnit con la fórmula matemática correcta.

    // GETTERS DE LOS COSTOS Y PROBABILIDADES
    @Override
    public int getFoodCost() { 
        return FOOD_COST_CATAPULT; 
    }
    
    @Override
    public int getWoodCost() { 
        return WOOD_COST_CATAPULT; 
    }
    
    @Override
    public int getIronCost() { 
        return IRON_COST_CATAPULT; 
    }
    
    @Override
    public int getManaCost() { 
        return MANA_COST_CATAPULT; 
    }
    
    @Override
    public int getChanceGeneratinWaste() { 
        return CHANCE_GENERATING_WASTE_CATAPULT; 
    }
    
    @Override
    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_CATAPULT; 
    }

    // Método para los reportes escritos de las batallas exigidos en el PDF
    @Override
    public String toString() {
        return "Catapult";
    }
}