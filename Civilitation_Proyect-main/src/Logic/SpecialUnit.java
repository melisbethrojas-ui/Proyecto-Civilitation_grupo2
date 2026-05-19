package Logic;

public abstract class SpecialUnit implements MilitaryUnit, Variables {

    protected int armor;        
    protected int initialArmor; 
    protected int baseDamage;
    protected int experience;

    // El constructor recibe los parámetros igual que AttackUnit y DefenseUnit
    public SpecialUnit(int armor, int baseDamage) {
        // Las unidades especiales NO tienen armadura (vida = 0)
        this.armor = 0;         
        this.initialArmor = 0;  
        this.baseDamage = baseDamage;
        this.experience = 0;
    }

    @Override
    public void takeDamage(int receivedDamage) { 
        // Al recibir daño, se resta directamente. Como empiezan en 0, pasará a ser negativo 
        // y el motor de batalla sabrá que han muerto inmediatamente.
        this.armor -= receivedDamage; 
    }

    @Override
    public int getActualArmor() {
        return this.armor; 
    }

    @Override
    public void resetArmor() {
        this.armor = this.initialArmor; // Vuelve a 0
    }

    @Override
    public void setExperience(int n) {
        this.experience = n;
    }

    @Override
    public int getExperience() {
        return this.experience;
    }

    // Obligamos a Magician y Priest a implementar attack() porque sus mecánicas son opuestas:
    // El mago devuelve daño destructivo y el sacerdote cura/santifica (daño = 0).
    @Override
    public abstract int attack(); 
}