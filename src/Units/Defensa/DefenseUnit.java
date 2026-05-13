package src.Units.Defensa;

import src.Interfaces.MilitaryUnit;
import src.Interfaces.Variables;

public abstract class DefenseUnit implements MilitaryUnit, Variables {

    protected int armor;          // blindaje actual
    protected int initialArmor;   // blindaje inicial
    protected int baseDamage;     // daño base

    public DefenseUnit(int armor, int baseDamage) {
        this.initialArmor = armor;
        this.armor = armor;
        this.baseDamage = baseDamage;
    }

    // Las defensas NO ganan experiencia → daño base fijo
    @Override
    public int attack() {
        return baseDamage;
    }

    // Recibir daño
    @Override
    public void takeDamage(int receivedDamage) {
        armor -= receivedDamage;
    }

    // Blindaje actual
    @Override
    public int getActualArmor() {
        return armor;
    }

    // Restaurar blindaje
    @Override
    public void resetArmor() {
        armor = initialArmor;
    }

    // Las defensas NO usan experiencia
    @Override
    public void setExperience(int n) {}

    @Override
    public void addExperience(int amount) {}

    @Override
    public int getExperience() {
        return 0;
    }

    // Una defensa está viva si su blindaje > 0
    @Override
    public boolean isAlive() {
        return armor > 0;
    }
}
