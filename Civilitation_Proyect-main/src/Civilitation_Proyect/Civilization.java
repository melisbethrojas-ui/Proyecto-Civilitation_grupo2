package Civilitation_Proyect;

import java.util.ArrayList;

public class Civilization implements Variables {

    // Recursos de la civilización
    private int food;
    private int wood;
    private int iron;
    private int mana;

    // Almacenes máximos
    private int maxFood;
    private int maxWood;
    private int maxIron;
    private int maxMana;

    // Niveles de edificios
    private int farmLevel;
    private int smithyLevel;
    private int carpentryLevel;
    private int churchLevel;
    private int magicTowerLevel;

    // Niveles de tecnologías
    private int armorTechnologyLevel;
    private int attackTechnologyLevel;

    // CORRECCIÓN 1: El ejército DEBE SER un Array de ArrayList de tamaño 9 según el PDF
    private ArrayList<MilitaryUnit>[] army;

    @SuppressWarnings("unchecked")
    public Civilization() {
        // Valores iniciales reglamentarios
        this.food = 15000;  
        this.wood = 25000;
        this.iron = 25000;
        this.mana = 2000;

        this.maxFood = 100000;
        this.maxWood = 100000;
        this.maxIron = 100000;
        this.maxMana = 50000;

        this.farmLevel = 1;
        this.smithyLevel = 1;
        this.carpentryLevel = 1;
        this.churchLevel = 0;
        this.magicTowerLevel = 0;

        this.armorTechnologyLevel = 0;
        this.attackTechnologyLevel = 0;

        // Inicializamos las 9 categorías del ejército de forma segura
        this.army = new ArrayList[9];
        for (int i = 0; i < 9; i++) {
            this.army[i] = new ArrayList<>();
        }
    }

    // Comprobación interna de recursos individuales
    private boolean hasEnoughResources(int foodCost, int woodCost, int ironCost, int manaCost) {
        return this.food >= foodCost && this.wood >= woodCost && this.iron >= ironCost && this.mana >= manaCost;
    }

    // Método auxiliar para restar recursos de forma segura
    private void subtractResources(int foodCost, int woodCost, int ironCost, int manaCost) {
        this.food -= foodCost;
        this.wood -= woodCost;
        this.iron -= ironCost;
        this.mana -= manaCost;
    }

    // =====================================================================
    // GESTIÓN DE EDIFICIOS Y ESCALADO DE COSTES (Siguiente Nivel)
    // =====================================================================
    
    public void upgradeFarm() throws ResourceException {
        // Coste base plano según Variables
        if (!hasEnoughResources(FOOD_COST_FARM, WOOD_COST_FARM, IRON_COST_FARM, 0)) {
            throw new ResourceException("No hay recursos suficientes para mejorar la Granja.");
        }
        subtractResources(FOOD_COST_FARM, WOOD_COST_FARM, IRON_COST_FARM, 0);
        this.farmLevel++;
        System.out.println("¡Granja mejorada! Nivel actual: " + this.farmLevel);
    }

    public void upgradeCarpentry() throws ResourceException {
        if (!hasEnoughResources(FOOD_COST_CARPENTRY, WOOD_COST_CARPENTRY, IRON_COST_CARPENTRY, 0)) {
            throw new ResourceException("No hay recursos suficientes para mejorar la Carpintería.");
        }
        subtractResources(FOOD_COST_CARPENTRY, WOOD_COST_CARPENTRY, IRON_COST_CARPENTRY, 0);
        this.carpentryLevel++;
        System.out.println("¡Carpintería mejorada! Nivel actual: " + this.carpentryLevel);
    }

    public void upgradeSmithy() throws ResourceException {
        // CORRECCIÓN 3: El PDF añade costes extras acumulativos para subir de nivel la Herrería
        int extraFood = (this.smithyLevel - 1) * NEXT_LEVEL_FOOD_COST_SMITHY;
        int extraWood = (this.smithyLevel - 1) * NEXT_LEVEL_WOOD_COST_SMITHY;
        int extraIron = (this.smithyLevel - 1) * NEXT_LEVEL_IRON_COST_SMITHY;

        int totalFood = FOOD_COST_SMITHY + extraFood;
        int totalWood = WOOD_COST_SMITHY + extraWood;
        int totalIron = IRON_COST_SMITHY + extraIron;

        if (!hasEnoughResources(totalFood, totalWood, totalIron, 0)) {
            throw new ResourceException("No hay recursos suficientes para mejorar la Herrería.");
        }
        subtractResources(totalFood, totalWood, totalIron, 0);
        this.smithyLevel++;
        System.out.println("¡Herrería mejorada! Nivel actual: " + this.smithyLevel);
    }

    public void upgradeMagicTower() throws ResourceException {
        // CORRECCIÓN 3: Costes incrementales específicos del PDF para la Torre de Magos
        int extraFood = (this.magicTowerLevel * NEXT_LEVEL_FOOD_COST_MAGICTOWER);
        int extraWood = (this.magicTowerLevel * NEXT_LEVEL_WOOD_COST_MAGICTOWER);
        int extraIron = (this.magicTowerLevel * NEXT_LEVEL_IRON_COST_MAGICTOWER);
        int extraMana = (this.magicTowerLevel * NEXT_LEVEL_MANA_COST_MAGICTOWER);

        int totalFood = FOOD_COST_MAGICTOWER + extraFood;
        int totalWood = WOOD_COST_MAGICTOWER + extraWood;
        int totalIron = IRON_COST_MAGICTOWER + extraIron;
        int totalMana = MANA_COST_MAGICTOWER + extraMana;

        if (!hasEnoughResources(totalFood, totalWood, totalIron, totalMana)) {
            throw new ResourceException("No hay recursos suficientes para mejorar la Torre Mágica.");
        }
        subtractResources(totalFood, totalWood, totalIron, totalMana);
        this.magicTowerLevel++;
        System.out.println("¡Torre Mágica mejorada! Nivel actual: " + this.magicTowerLevel);
    }

    public void upgradeChurch() throws ResourceException {
        if (!hasEnoughResources(FOOD_COST_CHURCH, WOOD_COST_CHURCH, IRON_COST_CHURCH, 0)) {
            throw new ResourceException("No hay recursos suficientes para mejorar la Iglesia.");
        }
        subtractResources(FOOD_COST_CHURCH, WOOD_COST_CHURCH, IRON_COST_CHURCH, 0);
        this.churchLevel++;
        System.out.println("¡Iglesia mejorada! Nivel actual: " + this.churchLevel);
    }

    // =====================================================================
    // TECNOLOGÍAS CON ESCALADO ACUMULATIVO PORCENTUAL
    // =====================================================================
    
    public void upgradeArmorTechnology() throws ResourceException {
        // Sube un 10% (PERCENTAGE_UPGRADE_TECH_COST) el coste base por cada nivel que ya poseas
        int multiplier = 100 + (this.armorTechnologyLevel * PERCENTAGE_UPGRADE_TECH_COST);
        int currentIronCost = (UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST * multiplier) / 100;

        if (this.iron < currentIronCost) {
            throw new ResourceException("No tienes suficiente hierro para mejorar la Tecnología de Defensa.");
        }
        this.iron -= currentIronCost;
        this.armorTechnologyLevel++;
        System.out.println("¡Tecnología de Armadura mejorada al nivel " + this.armorTechnologyLevel + "!");
    }

    public void upgradeAttackTechnology() throws ResourceException {
        int multiplier = 100 + (this.attackTechnologyLevel * PERCENTAGE_UPGRADE_TECH_COST);
        int currentIronCost = (UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST * multiplier) / 100;

        if (this.iron < currentIronCost) {
            throw new ResourceException("No tienes suficiente hierro para mejorar la Tecnología de Ataque.");
        }
        this.iron -= currentIronCost;
        this.attackTechnologyLevel++;
        System.out.println("¡Tecnología de Ataque mejorada al nivel " + this.attackTechnologyLevel + "!");
    }

    // =====================================================================
    // PRODUCCIÓN AUTOMÁTICA DE MATERIALES
    // =====================================================================
    public void produceResources() {
        int comidaGenerada = CIVILIZATION_FOOD_GENERATED + (this.farmLevel * CIVILIZATION_FOOD_GENERATED_PER_FARM);
        int maderaGenerada = CIVILIZATION_WOOD_GENERATED + (this.carpentryLevel * CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY);
        int hierroGenerada = CIVILIZATION_IRON_GENERATED + (this.smithyLevel * CIVILIZATION_IRON_GENERATED_PER_SMITHY);
        int manaGenerado = (this.magicTowerLevel * CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER);

        setFood(this.food + comidaGenerada);
        setWood(this.wood + maderaGenerada);
        setIron(this.iron + hierroGenerada);
        setMana(this.mana + manaGenerado);
    }

    // =====================================================================
    // CORRECCIÓN 2: RECLUTAMIENTO EN LOTES CON CREACIÓN PARCIAL EXIGIDA BY PDF
    // =====================================================================
    
    public void recruitSwordsman(int amount) throws ResourceException {
        int created = 0;
        for (int i = 0; i < amount; i++) {
            if (hasEnoughResources(FOOD_COST_SWORDSMAN, WOOD_COST_SWORDSMAN, IRON_COST_SWORDSMAN, MANA_COST_SWORDSMAN)) {
                subtractResources(FOOD_COST_SWORDSMAN, WOOD_COST_SWORDSMAN, IRON_COST_SWORDSMAN, MANA_COST_SWORDSMAN);
                this.army[0].add(new Swordsman(this.armorTechnologyLevel, this.attackTechnologyLevel));
                created++;
            } else {
                break;
            }
        }
        if (created < amount) {
            System.out.println("[AVISO] Solo se pudieron reclutar " + created + " de los " + amount + " Swordsman solicitados.");
            throw new ResourceException("Recursos insuficientes para completar el reclutamiento total de Swordsman.");
        }
    }

    public void recruitSpearman(int amount) throws ResourceException {
        int created = 0;
        for (int i = 0; i < amount; i++) {
            if (hasEnoughResources(FOOD_COST_SPEARMAN, WOOD_COST_SPEARMAN, IRON_COST_SPEARMAN, MANA_COST_SPEARMAN)) {
                subtractResources(FOOD_COST_SPEARMAN, WOOD_COST_SPEARMAN, IRON_COST_SPEARMAN, MANA_COST_SPEARMAN);
                this.army[1].add(new Spearman(this.armorTechnologyLevel, this.attackTechnologyLevel));
                created++;
            } else {
                break;
            }
        }
        if (created < amount) {
            System.out.println("[AVISO] Solo se pudieron reclutar " + created + " de los " + amount + " Spearman solicitados.");
            throw new ResourceException("Recursos insuficientes para completar el reclutamiento total de Spearman.");
        }
    }

    public void recruitCrossbow(int amount) throws ResourceException {
        int created = 0;
        for (int i = 0; i < amount; i++) {
            if (hasEnoughResources(FOOD_COST_CROSSBOW, WOOD_COST_CROSSBOW, IRON_COST_CROSSBOW, MANA_COST_CROSSBOW)) {
                subtractResources(FOOD_COST_CROSSBOW, WOOD_COST_CROSSBOW, IRON_COST_CROSSBOW, MANA_COST_CROSSBOW);
                this.army[2].add(new Crossbow(this.armorTechnologyLevel, this.attackTechnologyLevel));
                created++;
            } else {
                break;
            }
        }
        if (created < amount) {
            System.out.println("[AVISO] Solo se pudieron reclutar " + created + " de los " + amount + " Crossbow solicitados.");
            throw new ResourceException("Recursos insuficientes para completar el reclutamiento total de Crossbow.");
        }
    }

    public void recruitCannon(int amount) throws ResourceException {
        int created = 0;
        for (int i = 0; i < amount; i++) {
            if (hasEnoughResources(FOOD_COST_CANNON, WOOD_COST_CANNON, IRON_COST_CANNON, MANA_COST_CANNON)) {
                subtractResources(FOOD_COST_CANNON, WOOD_COST_CANNON, IRON_COST_CANNON, MANA_COST_CANNON);
                this.army[3].add(new Cannon(this.armorTechnologyLevel, this.attackTechnologyLevel));
                created++;
            } else {
                break;
            }
        }
        if (created < amount) {
            System.out.println("[AVISO] Solo se pudieron reclutar " + created + " de los " + amount + " Cannon solicitados.");
            throw new ResourceException("Recursos insuficientes para completar el reclutamiento total de Cannon.");
        }
    }

    public void recruitArrowTower(int amount) throws ResourceException {
        int created = 0;
        for (int i = 0; i < amount; i++) {
            if (hasEnoughResources(FOOD_COST_ARROWTOWER, WOOD_COST_ARROWTOWER, IRON_COST_ARROWTOWER, MANA_COST_ARROWTOWER)) {
                subtractResources(FOOD_COST_ARROWTOWER, WOOD_COST_ARROWTOWER, IRON_COST_ARROWTOWER, MANA_COST_ARROWTOWER);
                this.army[4].add(new ArrowTower(this.armorTechnologyLevel, this.attackTechnologyLevel));
                created++;
            } else {
                break;
            }
        }
        if (created < amount) {
            System.out.println("[AVISO] Solo se pudieron crear " + created + " de las " + amount + " ArrowTower solicitadas.");
            throw new ResourceException("Recursos insuficientes para completar la construcción total de ArrowTower.");
        }
    }

    public void recruitCatapult(int amount) throws ResourceException {
        int created = 0;
        for (int i = 0; i < amount; i++) {
            if (hasEnoughResources(FOOD_COST_CATAPULT, WOOD_COST_CATAPULT, IRON_COST_CATAPULT, MANA_COST_CATAPULT)) {
                subtractResources(FOOD_COST_CATAPULT, WOOD_COST_CATAPULT, IRON_COST_CATAPULT, MANA_COST_CATAPULT);
                this.army[5].add(new Catapult(this.armorTechnologyLevel, this.attackTechnologyLevel));
                created++;
            } else {
                break;
            }
        }
        if (created < amount) {
            System.out.println("[AVISO] Solo se pudieron crear " + created + " de las " + amount + " Catapult solicitadas.");
            throw new ResourceException("Recursos insuficientes para completar la construcción total de Catapult.");
        }
    }

    public void recruitRocketTower(int amount) throws ResourceException {
        int created = 0;
        for (int i = 0; i < amount; i++) {
            if (hasEnoughResources(FOOD_COST_ROCKETLAUNCHERTOWER, WOOD_COST_ROCKETLAUNCHERTOWER, IRON_COST_ROCKETLAUNCHERTOWER, MANA_COST_ROCKETLAUNCHERTOWER)) {
                subtractResources(FOOD_COST_ROCKETLAUNCHERTOWER, WOOD_COST_ROCKETLAUNCHERTOWER, IRON_COST_ROCKETLAUNCHERTOWER, MANA_COST_ROCKETLAUNCHERTOWER);
                this.army[6].add(new RocketLauncherTower(this.armorTechnologyLevel, this.attackTechnologyLevel));
                created++;
            } else {
                break;
            }
        }
        if (created < amount) {
            System.out.println("[AVISO] Solo se pudieron crear " + created + " de las " + amount + " RocketLauncherTower solicitadas.");
            throw new ResourceException("Recursos insuficientes para completar la construcción total de RocketLauncherTower.");
        }
    }

    public void recruitMagician(int amount) throws ResourceException, BuildingException {
        if (this.magicTowerLevel < 1) {
            throw new BuildingException("Necesitas construir la Torre de Magos para reclutar un Mago.");
        }
        int created = 0;
        for (int i = 0; i < amount; i++) {
            if (hasEnoughResources(FOOD_COST_MAGICIAN, WOOD_COST_MAGICIAN, IRON_COST_MAGICIAN, MANA_COST_MAGICIAN)) {
                subtractResources(FOOD_COST_MAGICIAN, WOOD_COST_MAGICIAN, IRON_COST_MAGICIAN, MANA_COST_MAGICIAN);
                // CORRECCIÓN 4: Pasamos los niveles tecnológicos al constructor del mago
                this.army[7].add(new Magician(this.armorTechnologyLevel, this.attackTechnologyLevel));
                created++;
            } else {
                break;
            }
        }
        if (created < amount) {
            System.out.println("[AVISO] Solo se pudieron reclutar " + created + " de los " + amount + " Magician solicitados.");
            throw new ResourceException("Recursos insuficientes para completar el reclutamiento total de Magician.");
        }
    }

    public void recruitPriest(int amount) throws ResourceException, BuildingException {
        if (this.churchLevel < 1) {
            throw new BuildingException("Necesitas construir la Iglesia para reclutar un Sacerdote.");
        }
        int created = 0;
        for (int i = 0; i < amount; i++) {
            if (hasEnoughResources(FOOD_COST_PRIEST, WOOD_COST_PRIEST, IRON_COST_PRIEST, MANA_COST_PRIEST)) {
                subtractResources(FOOD_COST_PRIEST, WOOD_COST_PRIEST, IRON_COST_PRIEST, MANA_COST_PRIEST);
                // CORRECCIÓN 4: Pasamos los niveles tecnológicos al constructor del sacerdote
                this.army[8].add(new Priest(this.armorTechnologyLevel, this.attackTechnologyLevel));
                created++;
            } else {
                break;
            }
        }
        if (created < amount) {
            System.out.println("[AVISO] Solo se pudieron reclutar " + created + " de los " + amount + " Priest solicitados.");
            throw new ResourceException("Recursos insuficientes para completar el reclutamiento total de Priest.");
        }
    }

    // --- GETTERS Y SETTERS COMPATIBLES ---

    public int getFood() { return food; }
    public void setFood(int food) { this.food = Math.min(food, maxFood); }

    public int getWood() { return wood; }
    public void setWood(int wood) { this.wood = Math.min(wood, maxWood); }

    public int getIron() { return iron; }
    public void setIron(int iron) { this.iron = Math.min(iron, maxIron); }

    public int getMana() { return mana; }
    public void setMana(int mana) { this.mana = Math.min(mana, maxMana); }

    public int getArmorTechnologyLevel() { return armorTechnologyLevel; }
    public int getAttackTechnologyLevel() { return attackTechnologyLevel; }

    public int getFarmLevel() { return farmLevel; }
    public int getSmithyLevel() { return smithyLevel; }
    public int getCarpentryLevel() { return carpentryLevel; }
    public int getChurchLevel() { return churchLevel; }
    public int getMagicTowerLevel() { return magicTowerLevel; }

    // Getter y Setter ajustados para el Array de ArrayLists
    public ArrayList<MilitaryUnit>[] getArmy() { return army; }
    public void setArmy(ArrayList<MilitaryUnit>[] army) { this.army = army; }
}