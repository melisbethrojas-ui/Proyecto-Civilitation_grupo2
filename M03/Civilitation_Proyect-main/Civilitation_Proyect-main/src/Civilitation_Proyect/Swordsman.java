package Civilitation_Proyect;

/**
 * Clase Swordsman: Unidad de infantería pesada.
 * Es la unidad básica con mayor resistencia gracias a su armadura de hierro.
 */
public class Swordsman extends AttackUnit {

    public Swordsman() {
        // Inicializamos con armadura y daño base desde la interfaz Variables
        super(ARMOR_SWORDSMAN, BASE_DAMAGE_SWORDSMAN);
    }

    @Override
    public int attack() {
        // Daño base + bonus por experiencia acumulada en combate
        return getBaseDamage() + (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT);
    }

    @Override
    public boolean chanceAttackAgain() {
        // Probabilidad de ataque extra (definida en Variables, ej. 10%)
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_ATTACK_AGAIN_SWORDSMAN;
    }

    @Override
    public boolean chanceGeneratngWaste() {
        // Probabilidad de generar chatarra/residuos al morir
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_GENERATNG_WASTE_SWORDSMAN;
    }

    // --- Métodos de coste vinculados a la interfaz Variables ---

    @Override
    public int getWoodCost() {
        return WOOD_COST_SWORDSMAN; // Generalmente 0 o muy bajo
    }

    @Override
    public int getFoodCost() {
        return FOOD_COST_SWORDSMAN;
    }

    @Override
    public int getIronCost() {
        return IRON_COST_SWORDSMAN; // Requiere hierro para su equipo
    }

    @Override
    public int getManaCost() {
        return MANA_COST_SWORDSMAN; // 0 (no es unidad mágica)
    }
}