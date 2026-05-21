package Civilitation_Proyect;

public class Priest extends SpecialUnit {

    // Constructor para el jugador
    public Priest(int technologyArmorLevel, int technologyAttackLevel) {
        // Le pasamos 0 de armadura y su daño base (0) a la clase madre SpecialUnit
        super(0, BASE_DAMAGE_PRIEST); 
    }

    // Constructor para el enemigo
    public Priest() {
        super(0, BASE_DAMAGE_PRIEST);
    }

    // El sacerdote no genera daño físico directo
    @Override
    public int attack() {
        return 0; 
    }

    // GETTERS DE LOS COSTOS Y PROBABILIDADES
    @Override
    public int getFoodCost() { 
        return FOOD_COST_PRIEST; 
    }
    
    @Override
    public int getWoodCost() { 
        return WOOD_COST_PRIEST; 
    }
    
    @Override
    public int getIronCost() { 
        return IRON_COST_PRIEST; 
    }
    
    @Override
    public int getManaCost() { 
        return MANA_COST_PRIEST; 
    }
    
    @Override
    public int getChanceGeneratinWaste() { 
        return CHANCE_GENERATING_WASTE_PRIEST; 
    }
    
    @Override
    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_PRIEST; 
    }

    // Método para los reportes escritos de las batallas 
    @Override
    public String toString() {
        return "Priest";
    }
}