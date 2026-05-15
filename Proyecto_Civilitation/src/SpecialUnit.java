public abstract class SpecialUnit extends MilitaryUnit {

    public SpecialUnit(int armor, int damage) {
        super(armor, damage);
    }

    // Cada unidad especial tiene su habilidad única
    public abstract void specialAbility(MilitaryUnit target);
}
