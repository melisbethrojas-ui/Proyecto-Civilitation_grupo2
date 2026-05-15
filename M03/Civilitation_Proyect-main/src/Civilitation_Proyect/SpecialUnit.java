package Civilitation_Proyect;

/**
 * Clase abstracta SpecialUnit.
 * Define el comportamiento base para unidades que requieren edificios 
 * específicos y consumo de maná (Magos y Sacerdotes).
 */
public abstract class SpecialUnit extends MilitaryUnit {

    public SpecialUnit(int armor, int damage) {
        super(armor, damage);
    }

    /**
     * Implementación del daño recibido.
     * Al ser unidades de "apoyo" o "mágicas", suelen ser más frágiles.
     */
    @Override
    public void takeDamage(int damage) {
        int currentArmor = getActualArmor();
        setActualArmor(currentArmor - damage);
        
        // Evitamos que la armadura sea negativa
        if (getActualArmor() < 0) {
            setActualArmor(0);
        }
    }

    /**
     * Las unidades especiales también tienen probabilidad de ataque extra.
     */
    public abstract boolean chanceAttackAgain();

    /**
     * Las unidades especiales también generan residuos al ser destruidas.
     */
    public abstract boolean chanceGeneratngWaste();
    
    // Los métodos attack() y los de costes se heredarán 
    // y se implementarán en las clases finales (Magician/Priest).
}