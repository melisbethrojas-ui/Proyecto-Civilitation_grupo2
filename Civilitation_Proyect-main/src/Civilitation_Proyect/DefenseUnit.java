package Civilitation_Proyect;

public abstract class DefenseUnit implements MilitaryUnit, Variables {

    // Atributos principales
    protected int armor;
    protected int initialArmor;
    protected int baseDamage;
    protected int experience;
    protected boolean sanctified;

    // Constructor
    public DefenseUnit(int armor, int baseDamage) {
        this.armor = armor;
        this.initialArmor = armor;
        this.baseDamage = baseDamage;
        this.experience = 0;
        this.sanctified = false;
    }

    // Gestión del daño recibido
    @Override
    public void takeDamage(int receivedDamage) {
        this.armor -= receivedDamage;
    }

    // Armadura actual
    @Override
    public int getActualArmor() {
        return this.armor;
    }

    // Reinicio de armadura
    @Override
    public void resetArmor() {
        this.armor = this.initialArmor;
    }

    // Gestión de experiencia
    @Override
    public void setExperience(int n) {
        this.experience = n;
    }

    @Override
    public int getExperience() {
        return this.experience;
    }

    // Cálculo del ataque
    @Override
    public int attack() {
        int totalDamage = this.baseDamage;
        
        // Aumento por puntos de experiencia ganados en batallas
        totalDamage += (this.baseDamage * this.experience * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT) / 100;
        
        // Aumento por santificación (Sacerdote activo en el ejército)
        if (this.isSanctified()) {
            totalDamage += (this.baseDamage * PLUS_ATTACK_UNIT_SANCTIFIED) / 100;
        }
        
        return totalDamage;
    }
        // Estado de santificación
    public boolean isSanctified() {
        return sanctified;
    }

    // Aplicación o retirada de santificación
    public void setSanctified(boolean sanctified) {
        if (sanctified && !this.sanctified) {
            // Aplica el aumento del 7% a la armadura actual e inicial al ser santificada
            this.armor += (this.initialArmor * PLUS_ARMOR_UNIT_SANCTIFIED) / 100;
            this.initialArmor += (this.initialArmor * PLUS_ARMOR_UNIT_SANCTIFIED) / 100;
        } else if (!sanctified && this.sanctified) {
            // Revoca el aumento del 7% si los sacerdotes mueren en batalla
            this.armor -= (this.initialArmor * PLUS_ARMOR_UNIT_SANCTIFIED) / (100 + PLUS_ARMOR_UNIT_SANCTIFIED);
            this.initialArmor -= (this.initialArmor * PLUS_ARMOR_UNIT_SANCTIFIED) / (100 + PLUS_ARMOR_UNIT_SANCTIFIED);
        }
        this.sanctified = sanctified;
    }
}