package Civilitation_Proyect;

import java.io.Serializable;
import java.util.ArrayList; // Importante para guardar la partida

public class Civilization implements Variables, Serializable {

    private static final long serialVersionUID = 1L;

    // Recursos
    private int wood;
    private int iron;
    private int food;
    private int mana;

    // Edificios
    private Carpentry carpentry;
    private Smithy smithy;
    private Farm farm;
    private MagicTower magicTower;
    private Church church;

    // Ejército
    private final ArrayList<MilitaryUnit>[] army;

    // Recuros
    public Civilization() {
        this.wood = 50000;  
        this.iron = 50000;
        this.food = 50000;
        this.mana = 10000;
        
        this.army = new ArrayList[9];
        for (int i = 0; i < 9; i++) {
            army[i] = new ArrayList<>();
        }
    }

    // --- GESTIÓN DE EDIFICIOS ---
    // (Tus métodos newCarpentry, newSmithy, etc. se mantienen igual)
    public void newCarpentry() throws ResourceException {
        if (wood >= WOOD_COST_CARPENTRY && iron >= IRON_COST_CARPENTRY && food >= FOOD_COST_CARPENTRY) {
            wood -= WOOD_COST_CARPENTRY;
            iron -= IRON_COST_CARPENTRY;
            food -= FOOD_COST_CARPENTRY;
            this.carpentry = new Carpentry();
        } else { throw new ResourceException("Falta madera, hierro o comida para Carpintería"); }
    }

    public void newSmithy() throws ResourceException {
        if (wood >= WOOD_COST_SMITHY && iron >= IRON_COST_SMITHY && food >= FOOD_COST_SMITHY) {
            wood -= WOOD_COST_SMITHY;
            iron -= IRON_COST_SMITHY;
            food -= FOOD_COST_SMITHY;
            this.smithy = new Smithy();
        } else { throw new ResourceException("Recursos insuficientes para la Herrería"); }
    }

    public void newMagicTower() throws ResourceException {
        if (wood >= WOOD_COST_MAGICTOWER && iron >= IRON_COST_MAGICTOWER && food >= FOOD_COST_MAGICTOWER && mana >= MANA_COST_MAGICTOWER) {
            wood -= WOOD_COST_MAGICTOWER;
            iron -= IRON_COST_MAGICTOWER;
            food -= FOOD_COST_MAGICTOWER;
            mana -= MANA_COST_MAGICTOWER;
            this.magicTower = new MagicTower();
        } else { throw new ResourceException("Recursos insuficientes para la Torre Mágica"); }
    }
    
    public void newFarm() throws ResourceException {
        if (wood >= WOOD_COST_FARM && iron >= IRON_COST_FARM && food >= FOOD_COST_FARM) {
            wood -= WOOD_COST_FARM;
            iron -= IRON_COST_FARM;
            food -= FOOD_COST_FARM;
            this.farm = new Farm();
        } else { throw new ResourceException("Recursos insuficientes para la Granja"); }
    }

    public void newChurch() throws ResourceException {
        if (wood >= WOOD_COST_CHURCH && iron >= IRON_COST_CHURCH && food >= FOOD_COST_CHURCH) {
            wood -= WOOD_COST_CHURCH;
            iron -= IRON_COST_CHURCH;
            food -= FOOD_COST_CHURCH;
            this.church = new Church();
        } else { throw new ResourceException("Recursos insuficientes para la Iglesia"); }
    }

    // --- CREACIÓN DE UNIDADES ---
    public void newSwordsman(int n) throws ResourceException {
        for (int i = 0; i < n; i++) {
            if (food >= FOOD_COST_SWORDSMAN && iron >= IRON_COST_SWORDSMAN) {
                food -= FOOD_COST_SWORDSMAN;
                iron -= IRON_COST_SWORDSMAN;
                army[0].add(new Swordsman());
            } else { throw new ResourceException("No puedes crear más Espadachines"); }
        }
    }
    
 // --- NUEVAS UNIDADES ---

    public void newCrossbowman(int n) throws ResourceException {
        for (int i = 0; i < n; i++) {
            if (wood >= WOOD_COST_CROSSBOW && iron >= IRON_COST_CROSSBOW) {
                wood -= WOOD_COST_CROSSBOW;
                iron -= IRON_COST_CROSSBOW;
                army[2].add(new Crossbow()); // Índice 2: Ballesteros
            } else {
                throw new ResourceException("Recursos insuficientes para Ballesteros (Madera/Hierro)");
            }
        }
    }

    public void newCannon(int n) throws ResourceException {
        for (int i = 0; i < n; i++) {
            if (wood >= WOOD_COST_CANNON && iron >= IRON_COST_CANNON) {
                wood -= WOOD_COST_CANNON;
                iron -= IRON_COST_CANNON;
                army[3].add(new Cannon()); // Índice 3: Cañones
            } else {
                throw new ResourceException("Recursos insuficientes para Cañones (Madera/Hierro)");
            }
        }
    }

    public void newMagician(int n) throws BuildingException, ResourceException {
        if (magicTower == null) throw new BuildingException("Necesitas la Torre Mágica");
        for (int i = 0; i < n; i++) {
            if (mana >= MANA_COST_MAGICIAN && food >= FOOD_COST_MAGICIAN) {
                mana -= MANA_COST_MAGICIAN;
                food -= FOOD_COST_MAGICIAN;
                army[7].add(new Magician());
            } else { throw new ResourceException("Falta maná o comida"); }
        }
    }

    public void newPriest(int n) throws BuildingException, ResourceException {
        if (church == null) throw new BuildingException("Necesitas la Iglesia");
        for (int i = 0; i < n; i++) {
            if (mana >= MANA_COST_PRIEST && food >= FOOD_COST_PRIEST) {
                mana -= MANA_COST_PRIEST;
                food -= FOOD_COST_PRIEST;
                army[8].add(new Priest());
            } else { throw new ResourceException("Falta maná o comida"); }
        }
    }

    // --- PRODUCCIÓN ---
    public void produceResources() {
        this.wood += CIVILIZATION_WOOD_GENERATED;
        this.iron += CIVILIZATION_IRON_GENERATED;
        this.food += CIVILIZATION_FOOD_GENERATED;

        if (carpentry != null) wood += carpentry.produceWood();
        if (smithy != null) iron += smithy.produceIron();
        if (farm != null) food += farm.produceFood();
        if (magicTower != null) mana += magicTower.produceMana();
    }

    // --- ESTADO DETALLADO ---
    public void printStats() {
        System.out.println("\n============================================");
        System.out.println("       ESTADO DE LA CIVILIZACIÓN");
        System.out.println("============================================");
        System.out.println(" MADERA: " + wood + " | HIERRO: " + iron);
        System.out.println(" COMIDA: " + food + " | MANÁ:   " + mana);
        System.out.println("--------------------------------------------");
        
        System.out.println(" EDIFICIOS:");
        System.out.println((carpentry != null ? " [V] " : " [ ] ") + "Carpintería");
        System.out.println((smithy != null ? " [V] " : " [ ] ") + "Herrería");
        System.out.println((farm != null ? " [V] " : " [ ] ") + "Granja");
        System.out.println((magicTower != null ? " [V] " : " [ ] ") + "Torre Mágica");
        System.out.println((church != null ? " [V] " : " [ ] ") + "Iglesia");
        System.out.println("--------------------------------------------");

        System.out.println(" TROPAS:");
        String[] nombres = {"Espadachines", "Lanceros", "Ballesteros", "Cañones", "Torres Flecha", 
                            "Catapultas", "Torres Cohete", "Magos", "Sacerdotes"};
        
        int total = 0;
        for (int i = 0; i < army.length; i++) {
            if (!army[i].isEmpty()) {
                System.out.println(" - " + nombres[i] + ": " + army[i].size());
                total += army[i].size();
            }
        }
        if (total == 0) System.out.println(" No tienes tropas.");
        System.out.println("============================================\n");
    }

    public ArrayList<MilitaryUnit>[] getArmy() { return army; }
    public void addWood(int amount) { this.wood += amount; }
    public void addIron(int amount) { this.iron += amount; }
}