package src.Logic;

import java.util.ArrayList;

import src.Buildings.Carpentry;
import src.Buildings.Church;
import src.Buildings.Farm;
import src.Buildings.MagicTower;
import src.Buildings.Smithy;
import src.Excepciones.BuildingException;
import src.Excepciones.ResourceException;
import src.Interfaces.MilitaryUnit;
import src.Interfaces.Variables;
import src.Units.Ataque.Cannon;
import src.Units.Ataque.Crossbow;
import src.Units.Ataque.Spearman;
import src.Units.Ataque.Swordsman;
import src.Units.Defensa.ArrowTower;
import src.Units.Defensa.Catapult;
import src.Units.Defensa.RocketLauncherTower;
import src.Units.Especial.Magician;
import src.Units.Especial.Priest;

public class Civilization implements Variables {

    // Recursos
    public int food = 0;
    public int wood = 0;
    public int iron = 0;
    public int mana = 0;

    // Tecnologías
    public int techDefense = 0;
    public int techAttack = 0;

    // Edificios
    public Farm farm = null;
    public Carpentry carpentry = null;
    public Smithy smithy = null;
    public MagicTower magicTower = null;
    public Church church = null;

    // Contador de batallas
    public int battles = 0;

    // Ejército: 9 listas (4 ofensivas, 3 defensivas, 2 especiales)
    public ArrayList<MilitaryUnit>[] army;

    @SuppressWarnings("unchecked")
    public Civilization() {
        army = new ArrayList[9];
        for (int i = 0; i < army.length; i++) {
            army[i] = new ArrayList<>();
        }
    }

    // ============================
    //   MÉTODOS DE EDIFICIOS
    // ============================

    public void newFarm() throws BuildingException, ResourceException {
        if (farm != null) throw new BuildingException("Farm ya construida");
        if (wood < WOOD_COST_FARM) throw new ResourceException("No hay madera");
        wood -= WOOD_COST_FARM;
        farm = new Farm();
    }

    public void newCarpentry() throws BuildingException, ResourceException {
        if (carpentry != null) throw new BuildingException("Carpentry ya construida");
        if (wood < WOOD_COST_CARPENTRY) throw new ResourceException("No hay madera");
        wood -= WOOD_COST_CARPENTRY;
        carpentry = new Carpentry();
    }

    public void newSmithy() throws BuildingException, ResourceException {
        if (smithy != null) throw new BuildingException("Smithy ya construida");
        if (wood < WOOD_COST_SMITHY || iron < IRON_COST_SMITHY)
            throw new ResourceException("No hay recursos");
        wood -= WOOD_COST_SMITHY;
        iron -= IRON_COST_SMITHY;
        smithy = new Smithy();
    }

    public void newMagicTower() throws BuildingException, ResourceException {
        if (magicTower != null) throw new BuildingException("MagicTower ya construida");
        if (wood < WOOD_COST_MAGICTOWER || mana < MANA_COST_MAGICTOWER)
            throw new ResourceException("No hay recursos");
        wood -= WOOD_COST_MAGICTOWER;
        mana -= MANA_COST_MAGICTOWER;
        magicTower = new MagicTower();
    }

    public void newChurch() throws BuildingException, ResourceException {
        if (church != null) throw new BuildingException("Church ya construida");
        if (wood < WOOD_COST_CHURCH) throw new ResourceException("No hay madera");
        wood -= WOOD_COST_CHURCH;
        church = new Church();
    }

    // ============================
    //   TECNOLOGÍAS
    // ============================

    public void upgradeTechnologyDefense() throws ResourceException {
        if (iron < IRON_COST_TECH_DEFENSE) throw new ResourceException("No hay hierro");
        iron -= IRON_COST_TECH_DEFENSE;
        techDefense++;
    }

    public void upgradeTechnologyAttack() throws ResourceException {
        if (iron < IRON_COST_TECH_ATTACK) throw new ResourceException("No hay hierro");
        iron -= IRON_COST_TECH_ATTACK;
        techAttack++;
    }

    // ============================
    //   CREACIÓN DE UNIDADES
    // ============================

    // ---- OFENSIVAS ----

    public void newSwordsman(int n) throws ResourceException {
        for (int i = 0; i < n; i++) {
            Swordsman s = new Swordsman();
            if (!canPay(s)) throw new ResourceException("No hay recursos");
            pay(s);
            army[0].add(s);
        }
    }

    public void newSpearman(int n) throws ResourceException {
        for (int i = 0; i < n; i++) {
            Spearman s = new Spearman();
            if (!canPay(s)) throw new ResourceException("No hay recursos");
            pay(s);
            army[1].add(s);
        }
    }

    public void newCrossbow(int n) throws ResourceException {
        for (int i = 0; i < n; i++) {
            Crossbow c = new Crossbow();
            if (!canPay(c)) throw new ResourceException("No hay recursos");
            pay(c);
            army[2].add(c);
        }
    }

    public void newCannon(int n) throws ResourceException {
        for (int i = 0; i < n; i++) {
            Cannon c = new Cannon();
            if (!canPay(c)) throw new ResourceException("No hay recursos");
            pay(c);
            army[3].add(c);
        }
    }

    // ---- DEFENSIVAS ----

    public void newArrowTower(int n) throws ResourceException {
        for (int i = 0; i < n; i++) {
            ArrowTower a = new ArrowTower();
            if (!canPay(a)) throw new ResourceException("No hay recursos");
            pay(a);
            army[4].add(a);
        }
    }

    public void newCatapult(int n) throws ResourceException {
        for (int i = 0; i < n; i++) {
            Catapult c = new Catapult();
            if (!canPay(c)) throw new ResourceException("No hay recursos");
            pay(c);
            army[5].add(c);
        }
    }

    public void newRocketLauncher(int n) throws ResourceException {
        for (int i = 0; i < n; i++) {
            RocketLauncherTower r = new RocketLauncherTower();
            if (!canPay(r)) throw new ResourceException("No hay recursos");
            pay(r);
            army[6].add(r);
        }
    }

    // ---- ESPECIALES ----

    public void newMagician(int n) throws ResourceException {
        for (int i = 0; i < n; i++) {
            Magician m = new Magician(techDefense, techAttack);
            if (!canPay(m)) throw new ResourceException("No hay recursos");
            pay(m);
            army[7].add(m);
        }
    }

    public void newPriest(int n) throws ResourceException {
        for (int i = 0; i < n; i++) {
            Priest p = new Priest(i, i);
            if (!canPay(p)) throw new ResourceException("No hay recursos");
            pay(p);
            army[8].add(p);
        }
    }

    // ============================
    //   MÉTODOS AUXILIARES
    // ============================

    private boolean canPay(MilitaryUnit u) {
        return food >= u.getFoodCost() &&
               wood >= u.getWoodCost() &&
               iron >= u.getIronCost() &&
               mana >= u.getManaCost();
    }

    private void pay(MilitaryUnit u) {
        food -= u.getFoodCost();
        wood -= u.getWoodCost();
        iron -= u.getIronCost();
        mana -= u.getManaCost();
    }

    // ============================
    //   STATS
    // ============================

    public void printStats() {
        System.out.println("Food: " + food);
        System.out.println("Wood: " + wood);
        System.out.println("Iron: " + iron);
        System.out.println("Mana: " + mana);
        System.out.println("Tech Attack: " + techAttack);
        System.out.println("Tech Defense: " + techDefense);
        System.out.println("Batallas: " + battles);

        System.out.println("\n=== Ejército ===");
        System.out.println("Swordsman: " + army[0].size());
        System.out.println("Spearman: " + army[1].size());
        System.out.println("Crossbow: " + army[2].size());
        System.out.println("Cannon: " + army[3].size());
        System.out.println("ArrowTower: " + army[4].size());
        System.out.println("Catapult: " + army[5].size());
        System.out.println("RocketLauncher: " + army[6].size());
        System.out.println("Magician: " + army[7].size());
        System.out.println("Priest: " + army[8].size());
    }

    public String getBattles() {
        return String.valueOf(battles);
    }
}
