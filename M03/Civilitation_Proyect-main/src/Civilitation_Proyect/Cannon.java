package Civilitation_Proyect;


public class Cannon extends AttackUnit {

    public Cannon() {
        // Usamos las constantes de la interfaz Variables para armadura y daño base
        super(ARMOR_CANNON, BASE_DAMAGE_CANNON);
    }

    @Override
    public int attack() {
        // El daño escala con la experiencia acumulada
        return getBaseDamage() + (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT);
    }

    @Override
    public boolean chanceAttackAgain() {
        // El Cañón tiene un 70% de probabilidad de atacar de nuevo (CHANCE_ATTACK_AGAIN_CANNON = 70)
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_ATTACK_AGAIN_CANNON;
    }

    @Override
    public boolean chanceGeneratngWaste() {
        // Probabilidad de generar escombros al ser destruido (70% según Variables)
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_GENERATNG_WASTE_CANNON;
    }

    // Métodos de costo obligatorios vinculados a la interfaz Variables

    @Override
    public int getWoodCost() {
        return WOOD_COST_CANNON;
    }

    @Override
    public int getFoodCost() {
        return FOOD_COST_CANNON;
    }

    @Override
    public int getIronCost() {
        return IRON_COST_CANNON;
    }

    @Override
    public int getManaCost() {
        return MANA_COST_CANNON; // El cañón no consume maná (0)
    }
}