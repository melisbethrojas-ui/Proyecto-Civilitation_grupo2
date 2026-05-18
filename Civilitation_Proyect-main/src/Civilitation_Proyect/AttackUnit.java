package Civilitation_Proyect;
// Clase abstracta que implementa la interfaz MilitaryUnit y define los atributos y métodos comunes para las unidades de ataque
public abstract class AttackUnit implements MilitaryUnit, Variables { 
    protected int armor;
    protected int initialArmor;
    protected int baseDamage;
    protected int experience;
    protected boolean sanctified;

    //Contructor
    public AttackUnit(int armor, int damage) {
        this.armor = armor;
        this.initialArmor = armor;
        this.baseDamage = damage;
        this.experience = 0;
        this.sanctified = false; // Inicialización recomendada
    }
    // Procesa el daño que recibe la unidad, reduciendo su armadura.
    @Override
    public void takeDamage(int receivedDamage) {
        this.armor -= receivedDamage;
        if (this.armor < 0) {
            this.armor = 0;
        }
    }

    @Override
    public int getActualArmor() {
        return this.armor;
    }

    @Override
    public void resetArmor() {
        this.armor = this.initialArmor;
    }

    @Override
    public void setExperience(int n) {
        this.experience = n;
    }

    @Override
    public int getExperience() {
        return this.experience;
    }

    // El motor requiere saber si está abstract para delegar a las clases hijas
    @Override
    public abstract int attack(); 

    // Getters y Setters específicos del enunciado
    public boolean isSanctified() {
        return sanctified;
    }

    public void setSanctified(boolean sanctified) {
        this.sanctified = sanctified;
    }
}