public abstract class DefenseUnit extends MilitaryUnit {

    public DefenseUnit(int armor, int damage) {
        super(armor, damage);
    }

    // Las unidades defensivas no atacan directamente
    public void defend(MilitaryUnit attacker) {
        attacker.receiveDamage(damage);
    }
}
