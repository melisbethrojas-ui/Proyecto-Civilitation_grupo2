package Civilitation_Proyect;

public class ArrowTower extends DefenseUnit {

    public ArrowTower() {
        // Llamamos al constructor de DefenseUnit con los valores de la interfaz
        super(ARMOR_ARROWTOWER, BASE_DAMAGE_ARROWTOWER);
    }

    @Override
    public int attack() {
        // Daño real = daño base + (experiencia * plus por exp)
        // Las unidades defensivas también ganan experiencia al sobrevivir
        return getBaseDamage() + (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT);
    }

    @Override
    public boolean chanceAttackAgain() {
        // Probabilidad de ataque extra (5% según Variables)
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_ATTACK_AGAIN_ARROWTOWER;
    }

    @Override
    public boolean chanceGeneratngWaste() {
        // Probabilidad de generar chatarra al ser destruida (55% según Variables)
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_GENERATNG_WASTE_ARROWTOWER;
    }

    // Métodos de obtención de costes desde la interfaz Variables
    
    @Override
    public int getWoodCost() {
        return WOOD_COST_ARROWTOWER;
    }

    @Override
    public int getFoodCost() {
        return FOOD_COST_ARROWTOWER;
    }

    @Override
    public int getIronCost() {
        return IRON_COST_ARROWTOWER;
    }

    @Override
    public int getManaCost() {
        return MANA_COST_ARROWTOWER; // Normalmente 0 para esta unidad
    }
}