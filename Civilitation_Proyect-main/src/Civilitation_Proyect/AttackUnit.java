package Civilitation_Proyect;

// Clase abstracta que implementa la interfaz MilitaryUnit y define los atributos y métodos comunes para las unidades de ataque
public abstract class AttackUnit implements MilitaryUnit, Variables { 
    protected int armor;
    protected int initialArmor;
    protected int baseDamage;
    protected int experience;
    protected boolean sanctified;

    // Constructor que recibe las estadísticas ya mejoradas por la tecnología de la civilización
    public AttackUnit(int armor, int damage) {
        this.armor = armor;
        this.initialArmor = armor;
        this.baseDamage = damage;
        this.experience = 0;
        this.sanctified = false; 
    }

    // Calcula el daño total de la unidad aplicando los bonos de experiencia y santificación
    @Override
    public int attack() {
        int damage = this.baseDamage;
        
        // Bono por experiencia: (experiencia * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT * baseDamage / 100)
        damage = damage + (this.experience * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT * this.baseDamage / 100);
        
        // Bono por estar santificado: (PLUS_ATTACK_UNIT_SANCTIFIED * baseDamage / 100)
        if (this.sanctified) {
            damage = damage + (PLUS_ATTACK_UNIT_SANCTIFIED * this.baseDamage / 100);
        }
        
        return damage;
    }

    // Procesa el daño que recibe la unidad, reduciendo su armadura (permite números negativos según el PDF)
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
        // Al resetear la armadura, si está santificado mantiene el bono, si no, vuelve a la inicial
        if (this.sanctified) {
            this.armor = this.initialArmor + (PLUS_ARMOR_UNIT_SANCTIFIED * this.initialArmor / 100);
        } else {
            this.armor = this.initialArmor;
        }
    }

    @Override
    public void setExperience(int n) {
        this.experience = n;
    }

    @Override
    public int getExperience() {
        return this.experience;
    }

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

    // Getters públicos necesarios para que la clase Battle y el motor hagan cálculos
    public int getInitialArmor() {
        return this.initialArmor;
    }

    public int getBaseDamage() {
        return this.baseDamage;
    }
}