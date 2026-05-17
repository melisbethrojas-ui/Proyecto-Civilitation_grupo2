package Civilitation_Proyect;
import java.util.ArrayList;

public class Civilization {

    // Recursos de la civilización
    private int food;
    private int wood;
    private int iron;
    private int mana;

    // Almacenes máximos (según el enunciado)
    private int maxFood;
    private int maxWood;
    private int maxIron;
    private int maxMana;

    // Niveles de edificios
    private int farmLevel;
    private int smithyLevel;
    private int carpentryLevel; // Añadido para que coincida con el menú del Main
    private int churchLevel;
    private int magicTowerLevel;

    // Niveles de tecnologías
    private int armorTechnologyLevel;
    private int attackTechnologyLevel;

    // El ejército de nuestra civilización
    private ArrayList<MilitaryUnit> army;

    // Constructor básico (inicializa los valores por defecto del juego)
    public Civilization() {
        // Valores iniciales incrementados para poder empezar a construir y probar el juego
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

        this.army = new ArrayList<>();
    }

    // Método genérico para comprobar y restar recursos. Lanza ResourceException si no alcanza.
    public void consumResources(int foodCost, int woodCost, int ironCost, int manaCost) throws ResourceException {
        if (this.food < foodCost || this.wood < woodCost || this.iron < ironCost || this.mana < manaCost) {
            throw new ResourceException("No tienes suficientes recursos para realizar esta acción.");
        }
        // Si alcanza, los restamos
        this.food -= foodCost;
        this.wood -= woodCost;
        this.iron -= ironCost;
        this.mana -= manaCost;
    }

    // =====================================================================
    // MÉTODOS DE CONSTRUCCIÓN / MEJORA DE EDIFICIOS (CON CONTROL DE COSTES)
    // =====================================================================
    
    public void upgradeFarm() throws ResourceException {
        // Comprobamos y consumimos usando las constantes de la interfaz Variables
        consumResources(Variables.FOOD_COST_FARM, Variables.WOOD_COST_FARM, Variables.IRON_COST_FARM, 0);
        this.farmLevel++;
        System.out.println("¡Granja mejorada! Nivel actual: " + this.farmLevel);
    }

    public void upgradeCarpentry() throws ResourceException {
        consumResources(Variables.FOOD_COST_CARPENTRY, Variables.WOOD_COST_CARPENTRY, Variables.IRON_COST_CARPENTRY, 0);
        this.carpentryLevel++;
        System.out.println("¡Carpintería mejorada! Nivel actual: " + this.carpentryLevel);
    }

    public void upgradeSmithy() throws ResourceException {
        consumResources(Variables.FOOD_COST_SMITHY, Variables.WOOD_COST_SMITHY, Variables.IRON_COST_SMITHY, 0);
        this.smithyLevel++;
        System.out.println("¡Herrería mejorada! Nivel actual: " + this.smithyLevel);
    }

    public void upgradeMagicTower() throws ResourceException {
        consumResources(Variables.FOOD_COST_MAGICTOWER, Variables.WOOD_COST_MAGICTOWER, Variables.IRON_COST_MAGICTOWER, Variables.MANA_COST_MAGICTOWER);
        this.magicTowerLevel++;
        System.out.println("¡Torre Mágica mejorada! Nivel actual: " + this.magicTowerLevel);
    }

    public void upgradeChurch() throws ResourceException {
        consumResources(Variables.FOOD_COST_CHURCH, Variables.WOOD_COST_CHURCH, Variables.IRON_COST_CHURCH, 0);
        this.churchLevel++;
        System.out.println("¡Iglesia mejorada! Nivel actual: " + this.churchLevel);
    }

    // =====================================================================
    // CÁLCULO DE PRODUCCIÓN AUTOMÁTICA DE MATERIALES
    // =====================================================================
    public void produceResources() {
        // Cálculo exacto del PDF: Producción Base + (Nivel Edificio * Incremento por edificio)
        int comidaGenerada = Variables.CIVILIZATION_FOOD_GENERATED + (this.farmLevel * Variables.CIVILIZATION_FOOD_GENERATED_PER_FARM);
        int maderaGenerada = Variables.CIVILIZATION_WOOD_GENERATED + (this.carpentryLevel * Variables.CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY);
        int hierroGenerada = Variables.CIVILIZATION_IRON_GENERATED + (this.smithyLevel * Variables.CIVILIZATION_IRON_GENERATED_PER_SMITHY);
        int manaGenerado = (this.magicTowerLevel * Variables.CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER);

        // Sumamos aplicando los límites de almacenamiento máximos establecidos
        setFood(this.food + comidaGenerada);
        setWood(this.wood + maderaGenerada);
        setIron(this.iron + hierroGenerada);
        setMana(this.mana + manaGenerado);
    }

    // --- MÉTODOS PARA RECLUTAR TROPAS ---

    public void recruitSwordsman() throws ResourceException {
        consumResources(Variables.FOOD_COST_SWORDSMAN, Variables.WOOD_COST_SWORDSMAN, Variables.IRON_COST_SWORDSMAN, Variables.MANA_COST_SWORDSMAN);
        army.add(new Swordsman(this.armorTechnologyLevel, this.attackTechnologyLevel));
    }

    public void recruitSpearman() throws ResourceException {
        consumResources(Variables.FOOD_COST_SPEARMAN, Variables.WOOD_COST_SPEARMAN, Variables.IRON_COST_SPEARMAN, Variables.MANA_COST_SPEARMAN);
        army.add(new Spearman(this.armorTechnologyLevel, this.attackTechnologyLevel));
    }

    public void recruitCrossbow() throws ResourceException {
        consumResources(Variables.FOOD_COST_CROSSBOW, Variables.WOOD_COST_CROSSBOW, Variables.IRON_COST_CROSSBOW, Variables.MANA_COST_CROSSBOW);
        army.add(new Crossbow(this.armorTechnologyLevel, this.attackTechnologyLevel));
    }

    public void recruitCannon() throws ResourceException {
        consumResources(Variables.FOOD_COST_CANNON, Variables.WOOD_COST_CANNON, Variables.IRON_COST_CANNON, Variables.MANA_COST_CANNON);
        army.add(new Cannon(this.armorTechnologyLevel, this.attackTechnologyLevel));
    }

    public void recruitArrowTower() throws ResourceException {
        consumResources(Variables.FOOD_COST_ARROWTOWER, Variables.WOOD_COST_ARROWTOWER, Variables.IRON_COST_ARROWTOWER, Variables.MANA_COST_ARROWTOWER);
        army.add(new ArrowTower(this.armorTechnologyLevel, this.attackTechnologyLevel));
    }

    public void recruitCatapult() throws ResourceException {
        consumResources(Variables.FOOD_COST_CATAPULT, Variables.WOOD_COST_CATAPULT, Variables.IRON_COST_CATAPULT, Variables.MANA_COST_CATAPULT);
        army.add(new Catapult(this.armorTechnologyLevel, this.attackTechnologyLevel));
    }

    public void recruitRocketTower() throws ResourceException {
        consumResources(Variables.FOOD_COST_ROCKETLAUNCHERTOWER, Variables.WOOD_COST_ROCKETLAUNCHERTOWER, Variables.IRON_COST_ROCKETLAUNCHERTOWER, Variables.MANA_COST_ROCKETLAUNCHERTOWER);
        army.add(new RocketLauncherTower(this.armorTechnologyLevel, this.attackTechnologyLevel));
    }

    public void recruitMagician() throws ResourceException, BuildingException {
        if (this.magicTowerLevel < 1) {
            throw new BuildingException("Necesitas construir la Torre de Magos para reclutar un Mago.");
        }
        consumResources(Variables.FOOD_COST_MAGICIAN, Variables.WOOD_COST_MAGICIAN, Variables.IRON_COST_MAGICIAN, Variables.MANA_COST_MAGICIAN);
        army.add(new Magician());
    }

    public void recruitPriest() throws ResourceException, BuildingException {
        if (this.churchLevel < 1) {
            throw new BuildingException("Necesitas construir la Iglesia para reclutar un Sacerdote.");
        }
        consumResources(Variables.FOOD_COST_PRIEST, Variables.WOOD_COST_PRIEST, Variables.IRON_COST_PRIEST, Variables.MANA_COST_PRIEST);
        army.add(new Priest());
    }

    // --- GETTERS Y SETTERS BÁSICOS ---

    public int getFood() { return food; }
    public void setFood(int food) { this.food = Math.min(food, maxFood); }

    public int getWood() { return wood; }
    public void setWood(int wood) { this.wood = Math.min(wood, maxWood); }

    public int getIron() { return iron; }
    public void setIron(int iron) { this.iron = Math.min(iron, maxIron); }

    public int getMana() { return mana; }
    public void setMana(int mana) { this.mana = Math.min(mana, maxMana); }

    public int getArmorTechnologyLevel() { return armorTechnologyLevel; }
    public void upgradeArmorTechnology() { this.armorTechnologyLevel++; }

    public int getAttackTechnologyLevel() { return attackTechnologyLevel; }
    public void upgradeAttackTechnology() { this.attackTechnologyLevel++; }

    public int getFarmLevel() { return farmLevel; }
    public int getSmithyLevel() { return smithyLevel; }
    public int getCarpentryLevel() { return carpentryLevel; }
    public int getChurchLevel() { return churchLevel; }
    public int getMagicTowerLevel() { return magicTowerLevel; }

    public ArrayList<MilitaryUnit> getArmy() { return army; }
    public void setArmy(ArrayList<MilitaryUnit> army) { this.army = army; }
}