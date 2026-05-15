package Civilitation_Proyect;

/**
 * Clase abstracta DefenseUnit.
 * Base para todas las unidades defensivas (torres y maquinaria de asedio).
 */
public abstract class DefenseUnit extends MilitaryUnit {

    public DefenseUnit(int armor, int damage) {
        super(armor, damage);
    }

    /**
     * Lógica de ataque para defensas:
     * El daño aumenta según la experiencia acumulada por la unidad.
     */
    @Override
    public int attack() {
        return getBaseDamage() + (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT);
    }

    /**
     * Recibe daño y reduce la armadura actual.
     */
    @Override
    public void takeDamage(int damage) {
        int currentArmor = getActualArmor();
        setActualArmor(currentArmor - damage);
        if (getActualArmor() < 0) {
            setActualArmor(0);
        }
    }

    /**
     * Método abstracto para la probabilidad de volver a atacar.
     * Se implementará en ArrowTower, Catapult, etc.
     */
    public abstract boolean chanceAttackAgain();

    /**
     * Método abstracto para la probabilidad de generar residuos al ser destruida.
     */
    public abstract boolean chanceGeneratngWaste();
}