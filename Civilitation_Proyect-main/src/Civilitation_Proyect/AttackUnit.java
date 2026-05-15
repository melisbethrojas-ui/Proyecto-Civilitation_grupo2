package Civilitation_Proyect;

public abstract class AttackUnit extends MilitaryUnit {

    public AttackUnit(int armor, int damage) {
        super(armor, damage);
    }

    /**
     * Lógica de ataque: El daño depende del daño base más la experiencia acumulada.
     * Según el PDF, cada punto de experiencia suma un bonus fijo.
     */
    @Override
    public int attack() {
        return getBaseDamage() + (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT);
    }

    /**
     * Las unidades de ataque tienen una probabilidad de volver a atacar.
     * Este método será implementado en las clases finales (Swordsman, etc.)
     * usando las constantes de la interfaz Variables.
     */
    public abstract boolean chanceAttackAgain();

    /**
     * Las unidades de ataque tienen una probabilidad de generar residuos al morir.
     */
    public abstract boolean chanceGeneratngWaste();

    /**
     * Método para recibir daño.
     * Resta el daño recibido de la armadura actual.
     */
    @Override
    public void takeDamage(int damage) {
        int currentArmor = getActualArmor();
        setActualArmor(currentArmor - damage);
        if (getActualArmor() < 0) {
            setActualArmor(0);
        }
    }
}