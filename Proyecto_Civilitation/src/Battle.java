public class Battle {

    private Civilization attacker;
    private Civilization defender;

    public Battle(Civilization attacker, Civilization defender) {
        this.attacker = attacker;
        this.defender = defender;
    }

    public void startBattle() {

        System.out.println("=== COMIENZA LA BATALLA ===");

        for (int i = 0; i < 9; i++) {

            MilitaryUnit atk = attacker.getUnit(i);
            MilitaryUnit def = defender.getUnit(i);

            if (atk == null && def == null) continue;

            System.out.println("\n--- Ronda " + (i + 1) + " ---");

            if (atk != null && def != null) {
                def.receiveDamage(atk.getDamage());
                if (def.isAlive()) atk.receiveDamage(def.getDamage());
            }
        }

        System.out.println("\n=== FIN DE LA BATALLA ===");
    }
}
