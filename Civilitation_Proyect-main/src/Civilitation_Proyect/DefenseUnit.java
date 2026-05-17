package Civilitation_Proyect;

public abstract class DefenseUnit implements MilitaryUnit, Variables { // Corregido: Implementa ambas interfaces

    protected int armor;
    protected int initialArmor;
    protected int baseDamage;
    protected int experience;
    protected boolean sanctified;

    public DefenseUnit(int armor, int baseDamage) {
        this.armor = armor;
        this.initialArmor = armor;
        this.baseDamage = baseDamage;
        this.experience = 0;
        this.sanctified = false;
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

    @Override
    public abstract int attack(); // Añadido para el contrato de interfaz
    
    // Añadidos getters/setters de santificación por si la clase Battle los evalúa globalmente
    public boolean isSanctified() {
        return sanctified;
    }

    public void setSanctified(boolean sanctified) {
        this.sanctified = sanctified;
    }
}