package Civilitation_Proyect;

/**
 * Clase Catapult: Unidad de asedio pesada.
 * Tiene un daño base muy elevado, ideal para romper defensas.
 */
public class Catapult extends AttackUnit {

    public Catapult() {
        // Usamos las constantes de Variables para inicializar
        super(ARMOR_CATAPULT, BASE_DAMAGE_CATAPULT);
    }

    @Override
    public int attack() {
        // Daño base + bonus por experiencia
        return getBaseDamage() + (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT);
    }

    @Override
    public boolean chanceAttackAgain() {
        // La catapulta suele tener un 50% de ataque extra (CHANCE_ATTACK_AGAIN_CATAPULT = 50)
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_ATTACK_AGAIN_CATAPULT;
    }

    @Override
    public boolean chanceGeneratngWaste() {
        // Probabilidad de generar escombros al morir (65% según Variables)
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_GENERATNG_WASTE_CATAPULT;
    }

    // Métodos de costes obligatorios
    
    @Override
    public int getWoodCost() {
        return WOOD_COST_CATAPULT;
    }

    @Override
    public int getFoodCost() {
        return FOOD_COST_CATAPULT;
    }

    @Override
    public int getIronCost() {
        return IRON_COST_CATAPULT;
    }

    @Override
    public int getManaCost() {
        return MANA_COST_CATAPULT; // Coste 0
    }
}