package Civilitation_Proyect;

/**
 * Clase base para todas las unidades del juego.
 * Implementa Variables para tener acceso a los costes y stats de equilibrio.
 */
public abstract class MilitaryUnit implements Variables {

    private int totalArmor;
    private int actualArmor;
    private int baseDamage;
    private int experience;

    public MilitaryUnit(int armor, int damage) {
        this.totalArmor = armor;
        this.actualArmor = armor;
        this.baseDamage = damage;
        this.experience = 0; // Todas las unidades empiezan con 0 XP
    }

    // --- Métodos abstractos que deben implementar las clases finales ---
    
    public abstract int attack();
    public abstract void takeDamage(int damage);
    
    // Métodos de coste (obligatorios para que Civilization pueda restar recursos)
    public abstract int getWoodCost();
    public abstract int getFoodCost();
    public abstract int getIronCost();
    public abstract int getManaCost();

    // --- Lógica común ---

    public boolean isAlive() {
        return actualArmor > 0;
    }

    public void resetArmor() {
        this.actualArmor = this.totalArmor;
    }

    // --- Getters y Setters ---

    public int getTotalArmor() { return totalArmor; }
    public void setTotalArmor(int totalArmor) { this.totalArmor = totalArmor; }

    public int getActualArmor() { return actualArmor; }
    public void setActualArmor(int actualArmor) { this.actualArmor = actualArmor; }

    public int getBaseDamage() { return baseDamage; }
    public void setBaseDamage(int baseDamage) { this.baseDamage = baseDamage; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
}