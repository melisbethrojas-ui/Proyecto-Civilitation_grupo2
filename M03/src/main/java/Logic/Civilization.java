package Logic;

import java.util.ArrayList;

public class Civilization implements Variables {

    // Tecnologías
    private int technologyDefense;
    private int technologyAttack;

    // Recursos
    public int wood;
    public int iron;
    public int food;
    public int mana;

    // Edificios (CANTIDAD)
    public int magicTower;
    public int church;
    public int farm;
    public int smithy;
    public int carpentry;

    // Batallas realizadas
    public int battles;

    // Ejército dividido en 9 categorías
    public ArrayList<MilitaryUnit>[] army;

    // Constructor
    public Civilization() {
        technologyDefense = 0;
        technologyAttack = 0;

        wood = 0;
        iron = 0;
        food = 0;
        mana = 0;

        magicTower = 0;
        church = 0;
        farm = 0;
        smithy = 0;
        carpentry = 0;

        battles = 0;

        army = new ArrayList[9];
        for (int i = 0; i < army.length; i++) {
            army[i] = new ArrayList<>();
        }
    }

    public int getTechnologyDefense() {
        return technologyDefense;
    }

    public int getTechnologyAttack() {
        return technologyAttack;
    }

    public void setArmorTechnologyLevel(int level) {
        this.technologyDefense = level;
    }

    public void setAttackTechnologyLevel(int level) {
        this.technologyAttack = level;
    }

    // ============================================================
    // NUEVOS MÉTODOS DE CONEXIÓN CON LA GUI (TECNOLOGÍAS)
    // ============================================================

    /**
     * Comprueba si la tecnología de ataque ha alcanzado su nivel máximo o si está investigada.
     * Si en tu juego no hay nivel máximo por encima de 0, devuelve si el nivel es mayor que 0.
     */
    public boolean isAttackTechResearched() {
        return this.technologyAttack > 0;
    }

    /**
     * Comprueba si la tecnología de defensa ha alcanzado su nivel máximo o si está investigada.
     */
    public boolean isDefenseTechResearched() {
        return this.technologyDefense > 0;
    }

    /**
     * Calcula dinámicamente el coste de hierro necesario para la siguiente mejora de ataque.
     */
    public int getAttackTechCost() {
        return UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST +
               (this.technologyAttack * UPGRADE_PLUS_ATTACK_TECHNOLOGY_IRON_COST);
    }

    /**
     * Calcula dinámicamente el coste de hierro necesario para la siguiente mejora de defensa.
     */
    public int getDefenseTechCost() {
        return UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST +
               (this.technologyDefense * UPGRADE_PLUS_DEFENSE_TECHNOLOGY_IRON_COST);
    }

    /**
     * Evalúa si la civilización tiene el hierro suficiente para pagar la mejora de ataque.
     */
    public boolean canResearchAttackTech() {
        return this.iron >= getAttackTechCost();
    }

    /**
     * Evalúa si la civilización tiene el hierro suficiente para pagar la mejora de defensa.
     */
    public boolean canResearchDefenseTech() {
        return this.iron >= getDefenseTechCost();
    }

    /**
     * Intenta investigar la tecnología de ataque. Reutiliza el método original 
     * arrojando excepciones si la GUI requiere controlar el error de recursos.
     */
    public void researchAttackTech() throws ResourceException {
        upgradeTechnologyAttack();
    }

    /**
     * Intenta investigar la tecnología de defensa. Reutiliza el método original 
     * arrojando excepciones si la GUI requiere controlar el error de recursos.
     */
    public void researchDefenseTech() throws ResourceException {
        upgradeTechnologyDefense();
    }

    // ============================================================
    // MÉTODOS PARA CREAR EDIFICIOS
    // ============================================================

    public void newFarm() throws ResourceException {
        if (food < FOOD_COST_FARM || wood < WOOD_COST_FARM || iron < IRON_COST_FARM) {
            throw new ResourceException("No hay recursos suficientes para construir una granja.");
        }
        food -= FOOD_COST_FARM;
        wood -= WOOD_COST_FARM;
        iron -= IRON_COST_FARM;
        farm++;
    }

    public void newCarpentry() throws ResourceException {
        if (food < FOOD_COST_CARPENTRY || wood < WOOD_COST_CARPENTRY || iron < IRON_COST_CARPENTRY) {
            throw new ResourceException("No hay recursos suficientes para construir una carpintería.");
        }
        food -= FOOD_COST_CARPENTRY;
        wood -= WOOD_COST_CARPENTRY;
        iron -= IRON_COST_CARPENTRY;
        carpentry++;
    }

    public void newSmithy() throws ResourceException {
        if (food < FOOD_COST_SMITHY || wood < WOOD_COST_SMITHY || iron < IRON_COST_SMITHY) {
            throw new ResourceException("No hay recursos suficientes para construir una herrería.");
        }
        food -= FOOD_COST_SMITHY;
        wood -= WOOD_COST_SMITHY;
        iron -= IRON_COST_SMITHY;
        smithy++;
    }

    public void newMagicTower() throws ResourceException {
        if (food < FOOD_COST_MAGICTOWER || wood < WOOD_COST_MAGICTOWER || iron < IRON_COST_MAGICTOWER) {
            throw new ResourceException("No hay recursos suficientes para construir una torre mágica.");
        }
        food -= FOOD_COST_MAGICTOWER;
        wood -= WOOD_COST_MAGICTOWER;
        iron -= IRON_COST_MAGICTOWER;
        magicTower++;
    }

    public void newChurch() throws ResourceException {
        if (food < FOOD_COST_CHURCH || wood < WOOD_COST_CHURCH || iron < IRON_COST_CHURCH) {
            throw new ResourceException("No hay recursos suficientes para construir una iglesia.");
        }
        food -= FOOD_COST_CHURCH;
        wood -= WOOD_COST_CHURCH;
        iron -= IRON_COST_CHURCH;
        church++;
    }

    // ============================================================
    // MEJORAS DE TECNOLOGÍA
    // ============================================================

    public void upgradeTechnologyDefense() throws ResourceException {
        int ironCost = getDefenseTechCost();

        if (iron < ironCost) {
            throw new ResourceException("No hay suficiente hierro para mejorar la tecnología de defensa.");
        }

        iron -= ironCost;
        technologyDefense++;
    }

    public void upgradeTechnologyAttack() throws ResourceException {
        int ironCost = getAttackTechCost();

        if (iron < ironCost) {
            throw new ResourceException("No hay suficiente hierro para mejorar la tecnología de ataque.");
        }

        iron -= ironCost;
        technologyAttack++;
    }

    // ============================================================
    // PRODUCCIÓN DE RECURSOS
    // ============================================================

    public void generateResources() {
        food += CIVILIZATION_FOOD_GENERATED + (farm * CIVILIZATION_FOOD_GENERATED_PER_FARM);
        wood += CIVILIZATION_WOOD_GENERATED + (carpentry * CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY);
        iron += CIVILIZATION_IRON_GENERATED + (smithy * CIVILIZATION_IRON_GENERATED_PER_SMITHY);
        mana += magicTower * CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER;
    }

    // =========================================================================
    // CREACIÓN DE UNIDADES
    // =========================================================================

    private boolean canCreate(int foodCost, int woodCost, int ironCost, int manaCost) {
        return food >= foodCost && wood >= woodCost && iron >= ironCost && mana >= manaCost;
    }

    private void pay(int foodCost, int woodCost, int ironCost, int manaCost) {
        food -= foodCost;
        wood -= woodCost;
        iron -= ironCost;
        mana -= manaCost;
    }

    // --- Swordsman ---
    public void newSwordsman(int n) throws ResourceException {
        int totalFood = FOOD_COST_SWORDSMAN * n;
        int totalWood = WOOD_COST_SWORDSMAN * n;
        int totalIron = IRON_COST_SWORDSMAN * n;

        if (!canCreate(totalFood, totalWood, totalIron, 0)) {
            throw new ResourceException("No hay recursos suficientes para crear " + n + " Swordsman.");
        }

        pay(totalFood, totalWood, totalIron, 0);
        for (int i = 0; i < n; i++) {
            army[0].add(new Swordsman(technologyDefense, technologyAttack));
        }
    }

    // --- Spearman ---
    public void newSpearman(int n) throws ResourceException {
        int totalFood = FOOD_COST_SPEARMAN * n;
        int totalWood = WOOD_COST_SPEARMAN * n;
        int totalIron = IRON_COST_SPEARMAN * n;

        if (!canCreate(totalFood, totalWood, totalIron, 0)) {
            throw new ResourceException("No hay recursos suficientes para crear " + n + " Spearman.");
        }

        pay(totalFood, totalWood, totalIron, 0);
        for (int i = 0; i < n; i++) {
            army[1].add(new Spearman(technologyDefense, technologyAttack));
        }
    }

    // --- Crossbow ---
    public void newCrossbow(int n) throws ResourceException {
        int totalWood = WOOD_COST_CROSSBOW * n;
        int totalIron = IRON_COST_CROSSBOW * n;

        if (!canCreate(0, totalWood, totalIron, 0)) {
            throw new ResourceException("No hay recursos suficientes para crear " + n + " Crossbow.");
        }

        pay(0, totalWood, totalIron, 0);
        for (int i = 0; i < n; i++) {
            army[2].add(new Crossbow(technologyDefense, technologyAttack));
        }
    }

    // --- Cannon ---
    public void newCannon(int n) throws ResourceException {
        int totalWood = WOOD_COST_CANNON * n;
        int totalIron = IRON_COST_CANNON * n;

        if (!canCreate(0, totalWood, totalIron, 0)) {
            throw new ResourceException("No hay recursos suficientes para crear " + n + " Cannon.");
        }

        pay(0, totalWood, totalIron, 0);
        for (int i = 0; i < n; i++) {
            army[3].add(new Cannon(technologyDefense, technologyAttack));
        }
    }

    // --- Arrow Tower ---
    public void newArrowTower(int n) throws ResourceException {
        int totalWood = WOOD_COST_ARROWTOWER * n;

        if (!canCreate(0, totalWood, 0, 0)) {
            throw new ResourceException("No hay recursos suficientes para crear " + n + " Arrow Tower.");
        }

        pay(0, totalWood, 0, 0);
        for (int i = 0; i < n; i++) {
            army[4].add(new ArrowTower(technologyDefense, technologyAttack));
        }
    }

    // --- Catapult ---
    public void newCatapult(int n) throws ResourceException {
        int totalWood = WOOD_COST_CATAPULT * n;
        int totalIron = IRON_COST_CATAPULT * n;

        if (!canCreate(0, totalWood, totalIron, 0)) {
            throw new ResourceException("No hay recursos suficientes para crear " + n + " Catapult.");
        }

        pay(0, totalWood, totalIron, 0);
        for (int i = 0; i < n; i++) {
            army[5].add(new Catapult(technologyDefense, technologyAttack));
        }
    }

    // --- Rocket Launcher ---
    public void newRocketLauncher(int n) throws ResourceException {
        int totalWood = WOOD_COST_ROCKETLAUNCHERTOWER * n;
        int totalIron = IRON_COST_ROCKETLAUNCHERTOWER * n;

        if (!canCreate(0, totalWood, totalIron, 0)) {
            throw new ResourceException("No hay recursos suficientes para crear " + n + " Rocket Launcher.");
        }

        pay(0, totalWood, totalIron, 0);
        for (int i = 0; i < n; i++) {
            army[6].add(new RocketLauncherTower(technologyDefense, technologyAttack));
        }
    }

    // --- Magician ---
    public void newMagician(int n) throws ResourceException, BuildingException {
        if (magicTower < 1) {
            throw new BuildingException("Necesitas una torre mágica para crear magos.");
        }

        int totalFood = FOOD_COST_MAGICIAN * n;
        int totalWood = WOOD_COST_MAGICIAN * n;
        int totalIron = IRON_COST_MAGICIAN * n;
        int totalMana = MANA_COST_MAGICIAN * n;

        if (!canCreate(totalFood, totalWood, totalIron, totalMana)) {
            throw new ResourceException("No hay recursos suficientes para crear " + n + " Magician.");
        }

        pay(totalFood, totalWood, totalIron, totalMana);
        for (int i = 0; i < n; i++) {
            army[7].add(new Magician(technologyDefense, technologyAttack));
        }
    }

    // --- Priest ---
    public void newPriest(int n) throws ResourceException, BuildingException {
        if (church < 1) {
            throw new BuildingException("Necesitas una iglesia para crear sacerdotes.");
        }

        // REGLA DEL ENUNCIADO: Límite de población de sacerdotes según las iglesias
        int sacerdotesActuales = army[8].size();
        if (sacerdotesActuales + n > church) {
            throw new BuildingException("No puedes tener más Sacerdotes que Iglesias. Iglesias: " 
                    + church + ", Sacerdotes actuales: " + sacerdotesActuales + ", Intentas crear: " + n);
        }

        int totalFood = FOOD_COST_PRIEST * n;
        int totalMana = MANA_COST_PRIEST * n;

        if (!canCreate(totalFood, 0, 0, totalMana)) {
            throw new ResourceException("No hay recursos suficientes para crear " + n + " Priest.");
        }

        pay(totalFood, 0, 0, totalMana);
        for (int i = 0; i < n; i++) {
            army[8].add(new Priest(technologyDefense, technologyAttack));
        }
    }
    // ============================================================
    // PRINT STATS
    // ============================================================

    public void printStats() {
        System.out.println("\n=== CIVILIZATION STATS ===");
        System.out.println("TECHNOLOGY");
        System.out.println("Attack: " + technologyAttack);
        System.out.println("Defense: " + technologyDefense);

        System.out.println("\nBUILDINGS");
        System.out.println("Farm: " + farm);
        System.out.println("Smithy: " + smithy);
        System.out.println("Carpentry: " + carpentry);
        System.out.println("Magic Tower: " + magicTower);
        System.out.println("Church: " + church);

        System.out.println("\nRESOURCES");
        System.out.println("Food: " + food);
        System.out.println("Wood: " + wood);
        System.out.println("Iron: " + iron);
        System.out.println("Mana: " + mana);
    }
}