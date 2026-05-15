public abstract class AttackUnit extends MilitaryUnit {

    public AttackUnit(int armor, int damage) {
        super(armor, damage);
    }

    // Método común para atacar
    public void attack(MilitaryUnit target) {
        target.receiveDamage(damage);
    }

    // Algunas unidades de ataque pueden tener habilidades especiales
    public boolean canAttackAgain() {
        return false;
    }

    public boolean generatesWaste() {
        return false;
    }
}
