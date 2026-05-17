package Civilitation_Proyect;

public abstract class SpecialUnit implements MilitaryUnit, Variables {

    protected int armor;        
    protected int initialArmor; 
    protected int baseDamage;
    protected int experience;

    // Cambiado: Ahora recibe dos parámetros para coincidir con las clases hijas
    public SpecialUnit(int armor, int baseDamage) {
        this.armor = 0;         // Sigue siendo 0 por regla del PDF
        this.initialArmor = 0;  // Sigue siendo 0 por regla del PDF
        this.baseDamage = baseDamage;
        this.experience = 0;
    }

    @Override
    public void takeDamage(int receivedDamage) { }

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
    public abstract int attack(); 
}