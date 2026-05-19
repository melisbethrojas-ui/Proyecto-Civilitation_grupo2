package Logic;

import java.util.ArrayList;

public class Civilization implements Variables {

    // Recursos civilizacion
    private int food;
    private int wood;
    private int iron;
    private int mana;

    // Almacenes maximos permitidos
    private int maxFood;
    private int maxWood;
    private int maxIron;
    private int maxMana;

    // Niveles de los edificios
    private int farmLevel;
    private int smithyLevel;
    private int carpentryLevel;
    private int churchLevel;
    private int magicTowerLevel;

    // Niveles de las tecnologias militares
    private int armorTechnologyLevel;
    private int attackTechnologyLevel;

    // El ejercito dividido en las 9 categorias
    private ArrayList<MilitaryUnit>[] army;

    // Constructor con los valores iniciales 
    public Civilization() {
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

        // Inicia el array de listas
        this.army = new ArrayList[9];
        for (int i = 0; i < 9; i++) {
            this.army[i] = new ArrayList<MilitaryUnit>();
        }
    }

    // Metodo para saber si nos alcanzan los recursos
    private boolean hasEnoughResources(int foodCost, int woodCost, int ironCost, int manaCost) {
        return this.food >= foodCost && this.wood >= woodCost && this.iron >= ironCost && this.mana >= manaCost;
    }

    // Metodo auxiliar para restar los costes de golpe
    private void subtractResources(int foodCost, int woodCost, int ironCost, int manaCost) {
        this.food -= foodCost;
        this.wood -= woodCost;
        this.iron -= ironCost;
        this.mana -= manaCost;
    }

    // Metodos para subir de nivel los edificios
    // Mejora de edificios (Granja)
    public void upgradeFarm() throws ResourceException {
        if (!hasEnoughResources(FOOD_COST_FARM, WOOD_COST_FARM, IRON_COST_FARM, 0)) {
            throw new ResourceException("No hay recursos suficientes para mejorar la Granja.");
        }
        subtractResources(FOOD_COST_FARM, WOOD_COST_FARM, IRON_COST_FARM, 0);
        this.farmLevel++;
        System.out.println("Granja mejorada al nivel: " + this.farmLevel);
    }
    // Mejora de edificios(Carpinteria)
    public void upgradeCarpentry() throws ResourceException {
        if (!hasEnoughResources(FOOD_COST_CARPENTRY, WOOD_COST_CARPENTRY, IRON_COST_CARPENTRY, 0)) {
            throw new ResourceException("No hay recursos suficientes para mejorar la Carpinteria.");
        }
        subtractResources(FOOD_COST_CARPENTRY, WOOD_COST_CARPENTRY, IRON_COST_CARPENTRY, 0);
        this.carpentryLevel++;
        System.out.println("Carpinteria mejorada al nivel: " + this.carpentryLevel);
    }
    // Mejora de edificios(Herreria)
    public void upgradeSmithy() throws ResourceException {
        // Añadimos los costes extras segun el nivel
        int extraFood = (this.smithyLevel - 1) * NEXT_LEVEL_FOOD_COST_SMITHY;
        int extraWood = (this.smithyLevel - 1) * NEXT_LEVEL_WOOD_COST_SMITHY;
        int extraIron = (this.smithyLevel - 1) * NEXT_LEVEL_IRON_COST_SMITHY;

        int totalFood = FOOD_COST_SMITHY + extraFood;
        int totalWood = WOOD_COST_SMITHY + extraWood;
        int totalIron = IRON_COST_SMITHY + extraIron;

        if (!hasEnoughResources(totalFood, totalWood, totalIron, 0)) {
            throw new ResourceException("No hay recursos suficientes para mejorar la Herreria.");
        }
        subtractResources(totalFood, totalWood, totalIron, 0);
        this.smithyLevel++;
        System.out.println("Herreria mejorada al nivel: " + this.smithyLevel);
    }

    // Mejora de edificios(Torre de Magos)
    public void upgradeMagicTower() throws ResourceException {
        // Costes incrementales especificos para la Torre de Magos
        int extraFood = (this.magicTowerLevel * NEXT_LEVEL_FOOD_COST_MAGICTOWER);
        int extraWood = (this.magicTowerLevel * NEXT_LEVEL_WOOD_COST_MAGICTOWER);
        int extraIron = (this.magicTowerLevel * NEXT_LEVEL_IRON_COST_MAGICTOWER);
        int extraMana = (this.magicTowerLevel * NEXT_LEVEL_MANA_COST_MAGICTOWER);

        int totalFood = FOOD_COST_MAGICTOWER + extraFood;
        int totalWood = WOOD_COST_MAGICTOWER + extraWood;
        int totalIron = IRON_COST_MAGICTOWER + extraIron;
        int totalMana = MANA_COST_MAGICTOWER + extraMana;

        if (!hasEnoughResources(totalFood, totalWood, totalIron, totalMana)) {
            throw new ResourceException("No hay recursos suficientes para mejorar la Torre Magica.");
        }
        subtractResources(totalFood, totalWood, totalIron, totalMana);
        this.magicTowerLevel++;
        System.out.println("Torre Magica mejorada al nivel: " + this.magicTowerLevel);
    }
    // Mejora de edificios(Iglesia)
    public void upgradeChurch() throws ResourceException {
        if (!hasEnoughResources(FOOD_COST_CHURCH, WOOD_COST_CHURCH, IRON_COST_CHURCH, 0)) {
            throw new ResourceException("No hay recursos suficientes para mejorar la Iglesia.");
        }
        subtractResources(FOOD_COST_CHURCH, WOOD_COST_CHURCH, IRON_COST_CHURCH, 0);
        this.churchLevel++;
        System.out.println("Iglesia mejorada al nivel: " + this.churchLevel);
    }

    // Metodos para mejorar las tecnologias 
    // Mejora de edificios(Technologia de Armadura)
    public void upgradeArmorTechnology() throws ResourceException {
        int multiplier = 100 + (this.armorTechnologyLevel * PERCENTAGE_UPGRADE_TECH_COST);
        int currentIronCost = (UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST * multiplier) / 100;

        if (this.iron < currentIronCost) {
            throw new ResourceException("No tienes suficiente hierro para mejorar la Tecnologia de Defensa.");
        }
        this.iron -= currentIronCost;
        this.armorTechnologyLevel++;
        System.out.println("Tecnologia de Armadura mejorada al nivel " + this.armorTechnologyLevel);
    }
    // Mejora de edificios(Technologia de Ataque)
    public void upgradeAttackTechnology() throws ResourceException {
        int multiplier = 100 + (this.attackTechnologyLevel * PERCENTAGE_UPGRADE_TECH_COST);
        int currentIronCost = (UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST * multiplier) / 100;

        if (this.iron < currentIronCost) {
            throw new ResourceException("No tienes suficiente hierro para mejorar la Tecnologia de Ataque.");
        }
        this.iron -= currentIronCost;
        this.attackTechnologyLevel++;
        System.out.println("Tecnologia de Ataque mejorada al nivel " + this.attackTechnologyLevel);
    }

    // Produccion automatica por turno generada por los edificios
     // Producción automática por turno
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

    // Metodos de reclutamiento por lotes
    // Reclutamiento de unidades(Swordsman)
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
            System.out.println("Aviso: Solo se pudieron reclutar " + created + " de los " + amount + " Swordsman pedidos.");
            throw new ResourceException("Recursos insuficientes para completar todo el reclutamiento de Swordsman.");
        }
    }
    // Reclutamiento de unidades(Spearman)
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
            System.out.println("Aviso: Solo se pudieron reclutar " + created + " de los " + amount + " Spearman pedidos.");
            throw new ResourceException("Recursos insuficientes para completar todo el reclutamiento de Spearman.");
        }
    }
    // Reclutamiento de unidades(Crossbow)
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
            System.out.println("Aviso: Solo se pudieron reclutar " + created + " de los " + amount + " Crossbow pedidos.");
            throw new ResourceException("Recursos insuficientes para completar todo el reclutamiento de Crossbow.");
        }
    }
    // Reclutamiento de unidades(Cannon)
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
            System.out.println("Aviso: Solo se pudieron reclutar " + created + " de los " + amount + " Cannon pedidos.");
            throw new ResourceException("Recursos insuficientes para completar todo el reclutamiento de Cannon.");
        }
    }
    // Reclutamiento de unidades(ArrowTower)
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
            System.out.println("Aviso: Solo se pudieron crear " + created + " de las " + amount + " ArrowTower pedidas.");
            throw new ResourceException("Recursos insuficientes para completar la construccion de ArrowTower.");
        }
    }
    // Reclutamiento de unidades(Catapult)
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
            System.out.println("Aviso: Solo se pudieron crear " + created + " de las " + amount + " Catapult pedidas.");
            throw new ResourceException("Recursos insuficientes para completar la construccion de Catapult.");
        }
    }
    // Reclutamiento de unidades(RocketLauncherTower)
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
            System.out.println("Aviso: Solo se pudieron crear " + created + " de las " + amount + " RocketLauncherTower pedidas.");
            throw new ResourceException("Recursos insuficientes para completar la construccion de RocketLauncherTower.");
        }
    }
    // Reclutamiento de unidades(Magician)
    public void recruitMagician(int amount) throws ResourceException, BuildingException {
        if (this.magicTowerLevel < 1) {
            throw new BuildingException("Necesitas construir la Torre de Magos para reclutar un Mago.");
        }
        int created = 0;
        for (int i = 0; i < amount; i++) {
            if (hasEnoughResources(FOOD_COST_MAGICIAN, WOOD_COST_MAGICIAN, IRON_COST_MAGICIAN, MANA_COST_MAGICIAN)) {
                subtractResources(FOOD_COST_MAGICIAN, WOOD_COST_MAGICIAN, IRON_COST_MAGICIAN, MANA_COST_MAGICIAN);
                this.army[7].add(new Magician(this.armorTechnologyLevel, this.attackTechnologyLevel));
                created++;
            } else {
                break;
            }
        }
        if (created < amount) {
            System.out.println("Aviso: Solo se pudieron reclutar " + created + " de los " + amount + " Magician pedidos.");
            throw new ResourceException("Recursos insuficientes para completar todo el reclutamiento de Magician.");
        }
    }
    // Reclutamiento de unidades(Priest)
    public void recruitPriest(int amount) throws ResourceException, BuildingException {
        if (this.churchLevel < 1) {
            throw new BuildingException("Necesitas construir la Iglesia para reclutar un Sacerdote.");
        }
        int created = 0;
        for (int i = 0; i < amount; i++) {
            if (hasEnoughResources(FOOD_COST_PRIEST, WOOD_COST_PRIEST, IRON_COST_PRIEST, MANA_COST_PRIEST)) {
                subtractResources(FOOD_COST_PRIEST, WOOD_COST_PRIEST, IRON_COST_PRIEST, MANA_COST_PRIEST);
                this.army[8].add(new Priest(this.armorTechnologyLevel, this.attackTechnologyLevel));
                created++;
            } else {
                break;
            }
        }
        if (created < amount) {
            System.out.println("Aviso: Solo se pudieron reclutar " + created + " de los " + amount + " Priest pedidos.");
            throw new ResourceException("Recursos insuficientes para completar todo el reclutamiento de Priest.");
        }
    }

    // Getters y Setters tradicionales controlando topes maximos
    
    public int getFood() { 
        return food; 
    }
    
    public void setFood(int food) { 
        if (food > maxFood) {
            this.food = maxFood;
        } else {
            this.food = food;
        }
    }

    public int getWood() { 
        return wood; 
    }
    
    public void setWood(int wood) { 
        if (wood > maxWood) {
            this.wood = maxWood;
        } else {
            this.wood = wood;
        }
    }

    public int getIron() { 
        return iron; 
    }
    
    public void setIron(int iron) { 
        if (iron > maxIron) {
            this.iron = maxIron;
        } else {
            this.iron = iron;
        }
    }

    public int getMana() { 
        return mana; 
    }
    
    public void setMana(int mana) { 
        if (mana > maxMana) {
            this.mana = maxMana;
        } else {
            this.mana = mana;
        }
    }

    public int getArmorTechnologyLevel() { 
        return armorTechnologyLevel; }
    public int getAttackTechnologyLevel() { 
        return attackTechnologyLevel; }

    public int getFarmLevel() { 
        return farmLevel; }
    public int getSmithyLevel() { 
        return smithyLevel; }
    public int getCarpentryLevel() { 
        return carpentryLevel; }
    public int getChurchLevel() { 
        return churchLevel; }
    public int getMagicTowerLevel() { 
        return magicTowerLevel; }

    public ArrayList<MilitaryUnit>[] getArmy() { 
        return army; }
    public void setArmy(ArrayList<MilitaryUnit>[] army) { 
        this.army = army; }


        // =========================================================================
    // SETTERS REQUERIDOS PARA LA PERSISTENCIA 
    // =========================================================================
    public void setFarmLevel(int farmLevel) { 
        this.farmLevel = farmLevel; 
    }
    public void setSmithyLevel(int smithyLevel) { 
        this.smithyLevel = smithyLevel; 
    }
    public void setCarpentryLevel(int carpentryLevel) { 
        this.carpentryLevel = carpentryLevel; 
    }
    public void setChurchLevel(int churchLevel) { 
        this.churchLevel = churchLevel; 
    }
    public void setMagicTowerLevel(int magicTowerLevel) { 
        this.magicTowerLevel = magicTowerLevel; 
    }
    public void setArmorTechnologyLevel(int armorTechnologyLevel) { 
        this.armorTechnologyLevel = armorTechnologyLevel; 
    }
    public void setAttackTechnologyLevel(int attackTechnologyLevel) { 
        this.attackTechnologyLevel = attackTechnologyLevel; 
    }
}