package Proyecto_Civilitation.src.Units.Ataque;

import Proyecto_Civilitation.src.Interfaces.MilitaryUnit;
import Proyecto_Civilitation.src.Interfaces.Variables;

public abstract class AttackUnit implements MilitaryUnit, Variables {

    protected int armor;          
    protected int initialArmor;   
    protected int baseDamage;     
    protected int experience;     
    protected boolean sanctified; 

    public AttackUnit(int armor, int baseDamage) {
        this.initialArmor = armor;
        this.armor = armor;
        this.baseDamage = baseDamage;
        this.experience = 0;
        this.sanctified = false;
    }

    @Override
    public int attack() {
        int dmg = baseDamage + experience;

        if (sanctified) {
            dmg += (baseDamage * PLUS_ATTACK_UNIT_SANCTIFIED) / 100;
        }
        return dmg;
    }

    @Override
    public void takeDamage(int receivedDamage) {
        armor -= receivedDamage;
    }

    @Override
    public int getActualArmor() {
        return armor;
    }

    @Override
    public void resetArmor() {
        armor = initialArmor;
    }

    @Override
    public void setExperience(int n) {
        experience = n;
    }

    @Override
    public void addExperience(int amount) {
        experience += amount;
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public boolean isAlive() {
        return armor > 0;
    }

    public void sanctify() {
        if (!sanctified) {
            sanctified = true;
            armor += (initialArmor * PLUS_ARMOR_UNIT_SANCTIFIED) / 100;
        }
    }
}
