package Civilitation_Proyect;

public abstract class SpecialUnit implements MilitaryUnit {

    protected int baseDamage;
    protected int experience;

    public SpecialUnit(int baseDamage) {
        this.baseDamage = baseDamage;
        this.experience = 0;
    }

    // Al no tener armadura, estos métodos devuelven 0 o no hacen nada
    @Override
    public void takeDamage(int receivedDamage) {
        // No tienen armadura que restar
    }

    @Override
    public int getActualArmor() {
        return 0; 
    }

    @Override
    public void resetArmor() {
        // No hay armadura que resetear
    }

    @Override
    public void setExperience(int n) {
        this.experience = n;
    }

    @Override
    public int getExperience() {
        return this.experience;
    }
}