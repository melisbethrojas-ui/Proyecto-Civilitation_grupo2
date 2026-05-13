package Proyecto_Civilitation.src.Units.Especial;

import Proyecto_Civilitation.src.Interfaces.MilitaryUnit;
import Proyecto_Civilitation.src.Interfaces.Variables;

public abstract class SpecialUnit implements MilitaryUnit, Variables {
    protected int armor;
    protected int initialArmor;
    protected int baseDamage;
    protected int experience;

    @Override
    public int getActualArmor() {
        return this.armor;
    }

    @Override
    public void takeDamage(int receivedDamage) {
        this.armor -= receivedDamage;
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
}