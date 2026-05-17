package Civilitation_Proyect;

public abstract class AttackUnit implements MilitaryUnit {    // Atributos protegidos para que las clases hijas (Swordsman, Spearman...) los hereden
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
    }

   // Implementación de métodos de la interfaz comunes a los atacantes
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

    // Getters y Setters específicos que pide el documento
    public boolean isSanctified() {
        return sanctified;
    }

    public void setSanctified(boolean sanctified) {
        this.sanctified = sanctified;
    }
}