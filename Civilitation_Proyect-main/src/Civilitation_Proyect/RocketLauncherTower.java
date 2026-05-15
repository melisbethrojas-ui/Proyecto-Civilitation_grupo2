package Civilitation_Proyect;


public class RocketLauncherTower extends DefenseUnit {

    public RocketLauncherTower() {
        // Inicializamos con armadura y daño base desde la interfaz Variables
        super(ARMOR_ROCKETLAUNCHERTOWER, BASE_DAMAGE_ROCKETLAUNCHERTOWER);
    }

    @Override
    public int attack() {
        // El daño escala con la experiencia acumulada en batallas anteriores
        return getBaseDamage() + (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT);
    }

    @Override
    public boolean chanceAttackAgain() {
        // Probabilidad de ataque extra (suele ser muy alta, ej. 60% o 70%)
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_ATTACK_AGAIN_ROCKETLAUNCHERTOWER;
    }

    @Override
    public boolean chanceGeneratngWaste() {
        // Probabilidad de generar residuos al ser destruida
        int operacion = (int) (Math.random() * 100) + 1;
        return operacion <= CHANCE_GENERATNG_WASTE_ROCKETLAUNCHERTOWER;
    }

    // --- Métodos de coste obligatorios (M03) ---

    @Override
    public int getWoodCost() {
        return WOOD_COST_ROCKETLAUNCHERTOWER;
    }

    @Override
    public int getFoodCost() {
        return FOOD_COST_ROCKETLAUNCHERTOWER;
    }

    @Override
    public int getIronCost() {
        return IRON_COST_ROCKETLAUNCHERTOWER;
    }

    @Override
    public int getManaCost() {
        return MANA_COST_ROCKETLAUNCHERTOWER; // Generalmente 0
    }
}