package Civilitation_Proyect;

public class Magician extends SpecialUnit {

    // Constructor para el jugador (Calcula el daño mejorado por tecnología)
    public Magician(int technologyArmorLevel, int technologyAttackLevel) {
        // Pasamos 0 de armadura y calculamos el daño base directamente con la tecnología de ataque
        super(
            0, 
            BASE_DAMAGE_MAGICIAN + (BASE_DAMAGE_MAGICIAN * technologyAttackLevel * PLUS_ATTACK_MAGICIAN_BY_TECHNOLOGY) / 100
        );
    }

    // Constructor para el enemigo (Sin tecnología añadida)
    public Magician() {
        super(0, BASE_DAMAGE_MAGICIAN);
    }

    // El Mago sí realiza daño destructivo a diferencia del Sacerdote
    @Override
    public int attack() {
        int totalDamage = this.baseDamage;
        
        // Incremento por experiencia acumulada en batallas (4% por punto)
        totalDamage += (this.baseDamage * this.experience * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT) / 100;
        
        return totalDamage;
    }

    // GETTERS DE LOS COSTOS Y PROBABILIDADES
    @Override
    public int getFoodCost() { 
        return FOOD_COST_MAGICIAN; 
    }
    
    @Override
    public int getWoodCost() { 
        return WOOD_COST_MAGICIAN; 
    }
    
    @Override
    public int getIronCost() { 
        return IRON_COST_MAGICIAN; 
    }
    
    @Override
    public int getManaCost() { 
        return MANA_COST_MAGICIAN; 
    }
    
    @Override
    public int getChanceGeneratinWaste() { 
        return CHANCE_GENERATING_WASTE_MAGICIAN; 
    }
    
    @Override
    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_MAGICIAN; 
    }

    // Método para los reportes escritos de las batallas 
    @Override
    public String toString() {
        return "Magician";
    }
}