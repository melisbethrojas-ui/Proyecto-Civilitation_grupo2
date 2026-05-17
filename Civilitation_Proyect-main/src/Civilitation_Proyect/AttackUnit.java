package Civilitation_Proyect;

public abstract class AttackUnit implements MilitaryUnit, Variables { // Corregido: Implementa ambas interfaces
    protected int armor;
    protected int initialArmor;
    protected int baseDamage;
    protected int experience;
    protected boolean sanctified;
    
    public AttackUnit(int armor, int damage) {
        this.armor = armor;
        this.initialArmor = armor;
        this.baseDamage = damage;
        this.experience = 0;
        this.sanctified = false; // Inicialización recomendada
    }

    @Override
    public void takeDamage(int receivedDamage) {
        this.armor -= receivedDamage;
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