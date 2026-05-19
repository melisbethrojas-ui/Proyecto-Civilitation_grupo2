package Civilitation_Proyect;

// Clase abstracta que implementa la interfaz MilitaryUnit 
// y define los atributos y métodos comunes para las unidades de ataque

public abstract class AttackUnit implements MilitaryUnit, Variables { 
    protected int armor;
    protected int initialArmor;
    protected int baseDamage;
    protected int experience;
    protected boolean sanctified;

    // Constructor
    public AttackUnit(int armor, int damage) {
        this.armor = armor;
        this.initialArmor = armor;
        this.baseDamage = damage;
        this.experience = 0;
        this.sanctified = false; 
    }

    // Cálculo del ataque
    @Override
    public int attack() {
        int damage = this.baseDamage;
        
        // Bono por experiencia
        damage = damage + (this.experience * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT * this.baseDamage / 100);
        
        // Bono por estar santificado
        if (this.sanctified) {
            damage = damage + (PLUS_ATTACK_UNIT_SANCTIFIED * this.baseDamage / 100);
        }
        
        return damage;
    }

    // Procesar daño recibido
    @Override
    public void takeDamage(int receivedDamage) {
        this.armor -= receivedDamage;
    }
    //Gestion de armadura
    @Override
    public int getActualArmor() {
        return this.armor;
    }

    @Override
    public void resetArmor() {
        // Al resetear la armadura, si está santificado mantiene el bono, si no, vuelve a la inicial
        if (this.sanctified) {
            this.armor = this.initialArmor + (PLUS_ARMOR_UNIT_SANCTIFIED * this.initialArmor / 100);
        } else {
            this.armor = this.initialArmor;
        }
    }
     //Gestion de experiencia
    @Override
    public void setExperience(int n) {
        this.experience = n;
    }

    @Override
    public int getExperience() {
        return this.experience;
    }

    //Estado santificado
    public boolean isSanctified() {
        return this.sanctified;
    }

    // Modifica el estado de santificación y altera la armadura actual en consecuencia
    public void setSanctified(boolean sanctified) {
        this.sanctified = sanctified;
        if (sanctified) {
            this.armor = this.initialArmor + (PLUS_ARMOR_UNIT_SANCTIFIED * this.initialArmor / 100);
        } else {
            this.armor = this.initialArmor;
        }
    }

        // Getters públicos para Battle
    public int getInitialArmor() {
        return this.initialArmor;
    }

    public int getBaseDamage() {
        return this.baseDamage;
    }
}