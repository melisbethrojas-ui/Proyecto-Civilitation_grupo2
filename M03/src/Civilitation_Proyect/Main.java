package Civilitation_Proyect;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    // Recursos del enemigo
    private static int enemyFood = 10000;
    private static int enemyWood = 15000;
    private static int enemyIron = 15000;

    // Ejército enemigo (solo 4 tipos: Swordsman, Spearman, Crossbow, Cannon)
    private static ArrayList<MilitaryUnit>[] enemyArmy = new ArrayList[4];

    // Reportes de batalla (últimas 5)
    private static ArrayList<String> battleReports = new ArrayList<>();

    // Civilización del jugador
    private static Civilization civ = new Civilization();

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        // Inicializar ejército enemigo
        for (int i = 0; i < enemyArmy.length; i++) {
            enemyArmy[i] = new ArrayList<>();
        }

        // Timer para recursos y enemigo/batalla
        Timer timer = new Timer();

        // Producción automática de recursos cada 1 minuto
        TimerTask produccionTask = new TimerTask() {
            @Override
            public void run() {
                civ.produceResources();
                System.out.println("\n[+] Se han generado recursos automáticamente.");
            }
        };
        timer.schedule(produccionTask, 0, 60000);

        // Crear ejército enemigo + batalla cada 3 minutos
        TimerTask enemyTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("\n⚠ ¡Un ejército enemigo se aproxima!");
                createEnemyArmy();
                viewThreat();
                startBattle();
            }
        };
        timer.schedule(enemyTask, 180000, 180000);

        boolean salir = false;

        System.out.println("=========================================");
        System.out.println("   BIENVENIDO A CIVILIZATIONS EMPIRE     ");
        System.out.println("=========================================");

        while (!salir) {

            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Ver estado");
            System.out.println("2. Crear edificios / Tecnologías");
            System.out.println("3. Reclutar tropas");
            System.out.println("4. Ver ejército propio");
            System.out.println("5. Ver ejército enemigo (si existe)");
            System.out.println("6. Ver reportes de batalla");
            System.out.println("7. Batalla manual ahora");
            System.out.println("8. Salir");
            System.out.print("Selecciona una opción: ");

            int opcion = leerNumero(teclado);

            switch (opcion) {

                case 1:
                    mostrarEstado(civ);
                    break;

                case 2:
                    menuEdificiosYTecnologias(civ, teclado);
                    break;

                case 3:
                    menuReclutamiento(civ, teclado);
                    break;

                case 4:
                    mostrarEjercito(civ);
                    break;

                case 5:
                    viewThreat();
                    break;

                case 6:
                    mostrarReportesBatalla();
                    break;

                case 7:
                    if (hayEnemigo()) {
                        startBattle();
                    } else {
                        System.out.println("[INFO] No hay ejército enemigo ahora mismo.");
                    }
                    break;

                case 8:
                    salir = true;
                    timer.cancel();
                    System.out.println("\n¡Gracias por jugar a Civilizations! Saliendo...");
                    break;

                default:
                    System.out.println("[ERROR] Opción no válida.");
            }
        }

        teclado.close();
    }

    // ============================================================
    // Lectura segura de números
    // ============================================================
    private static int leerNumero(Scanner teclado) {
        while (true) {
            try {
                return Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Introduce un número válido:");
            }
        }
    }

    // ============================================================
    // ESTADO
    // ============================================================
    private static void mostrarEstado(Civilization civ) {
        System.out.println("\n=======================================================================");
        System.out.println(" RECURSOS: Comida: " + civ.getFood() + " | Madera: " + civ.getWood() +
                " | Hierro: " + civ.getIron() + " | Mana: " + civ.getMana());
        System.out.println(" EDIFICIOS: Granja: Lvl " + civ.getFarmLevel() +
                " | Carpinteria: Lvl " + civ.getCarpentryLevel() +
                " | Herreria: Lvl " + civ.getSmithyLevel());
        System.out.println("            Iglesia: Lvl " + civ.getChurchLevel() +
                " | T. Magica: Lvl " + civ.getMagicTowerLevel());
        System.out.println(" TECNOLOGIAS: Ataque: Lvl " + civ.getAttackTechnologyLevel() +
                " | Defensa: Lvl " + civ.getArmorTechnologyLevel());
        System.out.println("=======================================================================");
    }

    // ============================================================
    // EDIFICIOS + TECNOLOGÍAS
    // ============================================================
    private static void menuEdificiosYTecnologias(Civilization civ, Scanner teclado) {
        System.out.println("\n--- EDIFICIOS Y TECNOLOGÍAS ---");

        System.out.println("1. Granja (Coste: " 
                + Variables.FOOD_COST_FARM + " comida, "
                + Variables.WOOD_COST_FARM + " madera, "
                + Variables.IRON_COST_FARM + " hierro)");

        System.out.println("2. Carpintería (Coste: "
                + Variables.FOOD_COST_CARPENTRY + " comida, "
                + Variables.WOOD_COST_CARPENTRY + " madera, "
                + Variables.IRON_COST_CARPENTRY + " hierro)");

        System.out.println("3. Herrería (Coste base: "
                + Variables.FOOD_COST_SMITHY + " comida, "
                + Variables.WOOD_COST_SMITHY + " madera, "
                + Variables.IRON_COST_SMITHY + " hierro"
                + " + coste extra por nivel)");

        System.out.println("4. Iglesia (Coste: "
                + Variables.FOOD_COST_CHURCH + " comida, "
                + Variables.WOOD_COST_CHURCH + " madera, "
                + Variables.IRON_COST_CHURCH + " hierro)");

        System.out.println("5. Torre Mágica (Coste base: "
                + Variables.FOOD_COST_MAGICTOWER + " comida, "
                + Variables.WOOD_COST_MAGICTOWER + " madera, "
                + Variables.IRON_COST_MAGICTOWER + " hierro, "
                + Variables.MANA_COST_MAGICTOWER + " mana"
                + " + coste extra por nivel)");

        System.out.println("6. Mejora Tecnología Ataque (Coste base: "
                + Variables.UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST + " hierro)");

        System.out.println("7. Mejora Tecnología Defensa (Coste base: "
                + Variables.UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST + " hierro)");


        int op = leerNumero(teclado);

        try {
            switch (op) {
                case 1 -> civ.upgradeFarm();
                case 2 -> civ.upgradeCarpentry();
                case 3 -> civ.upgradeSmithy();
                case 4 -> civ.upgradeChurch();
                case 5 -> civ.upgradeMagicTower();
                case 6 -> civ.upgradeAttackTechnology();
                case 7 -> civ.upgradeArmorTechnology();
                default -> System.out.println("Opción no válida.");
            }
        } catch (ResourceException e) {
            System.out.println("[ERROR RECURSOS] " + e.getMessage());
        }
    }

    // ============================================================
    // RECLUTAMIENTO
    // ============================================================
    private static void menuReclutamiento(Civilization civ, Scanner teclado) {
        System.out.println("\n--- RECLUTAR UNIDADES ---");
        System.out.println("1. Swordsman     (Coste: " + Variables.FOOD_COST_SWORDSMAN + " comida, " +
                                                Variables.WOOD_COST_SWORDSMAN + " madera, " +
                                                Variables.IRON_COST_SWORDSMAN + " hierro)");
        System.out.println("2. Spearman      (Coste: " + Variables.FOOD_COST_SPEARMAN + " comida, " +
                                                Variables.WOOD_COST_SPEARMAN + " madera, " +
                                                Variables.IRON_COST_SPEARMAN + " hierro)");
        System.out.println("3. Crossbow      (Coste: " + Variables.FOOD_COST_CROSSBOW + " comida, " +
                                                Variables.WOOD_COST_CROSSBOW + " madera, " +
                                                Variables.IRON_COST_CROSSBOW + " hierro)");
        System.out.println("4. Cannon        (Coste: " + Variables.FOOD_COST_CANNON + " comida, " +
                                                Variables.WOOD_COST_CANNON + " madera, " +
                                                Variables.IRON_COST_CANNON + " hierro)");
        System.out.println("5. ArrowTower    (Coste: " + Variables.FOOD_COST_ARROWTOWER + " comida, " +
                                                Variables.WOOD_COST_ARROWTOWER + " madera, " +
                                                Variables.IRON_COST_ARROWTOWER + " hierro)");
        System.out.println("6. Catapult      (Coste: " + Variables.FOOD_COST_CATAPULT + " comida, " +
                                                Variables.WOOD_COST_CATAPULT + " madera, " +
                                                Variables.IRON_COST_CATAPULT + " hierro)");
        System.out.println("7. RocketTower   (Coste: " + Variables.FOOD_COST_ROCKETLAUNCHERTOWER + " comida, " +
                                                Variables.WOOD_COST_ROCKETLAUNCHERTOWER + " madera, " +
                                                Variables.IRON_COST_ROCKETLAUNCHERTOWER + " hierro)");
        System.out.println("8. Magician      (Coste: " + Variables.FOOD_COST_MAGICIAN + " comida, " +
                                                Variables.WOOD_COST_MAGICIAN + " madera, " +
                                                Variables.IRON_COST_MAGICIAN + " hierro + " +
                                                Variables.MANA_COST_MAGICIAN + " mana)");
        System.out.println("9. Priest        (Coste: " + Variables.FOOD_COST_PRIEST + " comida, " +
                                                Variables.WOOD_COST_PRIEST + " madera, " +
                                                Variables.IRON_COST_PRIEST + " hierro + " +
                                                Variables.MANA_COST_PRIEST + " mana)");
                                    
        System.out.print("Elige el tipo de unidad: ");

        int tipo = leerNumero(teclado);

        System.out.print("¿Cuántas unidades quieres fabricar?: ");
        int cantidad = leerNumero(teclado);

        try {
            switch (tipo) {
                case 1 -> civ.recruitSwordsman(cantidad);
                case 2 -> civ.recruitSpearman(cantidad);
                case 3 -> civ.recruitCrossbow(cantidad);
                case 4 -> civ.recruitCannon(cantidad);
                case 5 -> civ.recruitArrowTower(cantidad);
                case 6 -> civ.recruitCatapult(cantidad);
                case 7 -> civ.recruitRocketTower(cantidad);
                case 8 -> civ.recruitMagician(cantidad);
                case 9 -> civ.recruitPriest(cantidad);
                default -> System.out.println("Tipo de unidad incorrecto.");
            }
            System.out.println("¡Reclutamiento completado!");
        } catch (ResourceException e) {
            System.out.println("[ERROR RECURSOS] " + e.getMessage());
        } catch (BuildingException e) {
            System.out.println("[ERROR EDIFICIO] " + e.getMessage());
        }
    }

    // ============================================================
    // EJÉRCITO PROPIO
    // ============================================================
    private static void mostrarEjercito(Civilization civ) {
        System.out.println("\n================ EJÉRCITO PROPIO ================");

        ArrayList<MilitaryUnit>[] army = civ.getArmy();

        System.out.println("1. Swordsman: " + army[0].size());
        System.out.println("2. Spearman: " + army[1].size());
        System.out.println("3. Crossbow: " + army[2].size());
        System.out.println("4. Cannon: " + army[3].size());
        System.out.println("5. ArrowTower: " + army[4].size());
        System.out.println("6. Catapult: " + army[5].size());
        System.out.println("7. RocketTower: " + army[6].size());
        System.out.println("8. Magician: " + army[7].size());
        System.out.println("9. Priest: " + army[8].size());

        System.out.println("=================================================");
    }

    // ============================================================
    // ENEMIGO: CREACIÓN Y VISUALIZACIÓN
    // ============================================================
    private static void createEnemyArmy() {
        // Limpiamos ejército anterior
        for (int i = 0; i < enemyArmy.length; i++) {
            enemyArmy[i].clear();
        }

        Random rand = new Random();

        // Costes aproximados (ajusta si tus constantes son distintas)
        int foodSword = 100;
        int woodSword = 50;
        int ironSword = 50;

        int foodSpear = 120;
        int woodSpear = 60;
        int ironSpear = 60;

        int foodCross = 150;
        int woodCross = 80;
        int ironCross = 80;

        int foodCannon = 200;
        int woodCannon = 120;
        int ironCannon = 150;

        while (true) {
            // Comprobamos si al menos podemos crear un Swordsman
            if (enemyFood < foodSword || enemyWood < woodSword || enemyIron < ironSword) {
                break;
            }

            int prob = rand.nextInt(100); // 0-99

            if (prob < 35) { // Swordsman 35%
                if (enemyFood >= foodSword && enemyWood >= woodSword && enemyIron >= ironSword) {
                    enemyFood -= foodSword;
                    enemyWood -= woodSword;
                    enemyIron -= ironSword;
                    enemyArmy[0].add(new Swordsman(0, 0));
                }
            } else if (prob < 60) { // Spearman 25%
                if (enemyFood >= foodSpear && enemyWood >= woodSpear && enemyIron >= ironSpear) {
                    enemyFood -= foodSpear;
                    enemyWood -= woodSpear;
                    enemyIron -= ironSpear;
                    enemyArmy[1].add(new Spearman(0, 0));
                }
            } else if (prob < 80) { // Crossbow 20%
                if (enemyFood >= foodCross && enemyWood >= woodCross && enemyIron >= ironCross) {
                    enemyFood -= foodCross;
                    enemyWood -= woodCross;
                    enemyIron -= ironCross;
                    enemyArmy[2].add(new Crossbow(0, 0));
                }
            } else { // Cannon 20%
                if (enemyFood >= foodCannon && enemyWood >= woodCannon && enemyIron >= ironCannon) {
                    enemyFood -= foodCannon;
                    enemyWood -= woodCannon;
                    enemyIron -= ironCannon;
                    enemyArmy[3].add(new Cannon(0, 0));
                }
            }
        }

        // Aumentamos recursos del enemigo para la próxima vez (más fuerte cada vez)
        enemyFood += 3000;
        enemyWood += 3000;
        enemyIron += 3000;
    }

    private static boolean hayEnemigo() {
        for (ArrayList<MilitaryUnit> list : enemyArmy) {
            if (!list.isEmpty()) return true;
        }
        return false;
    }

    private static void viewThreat() {
        if (!hayEnemigo()) {
            System.out.println("\n[INFO] No hay ejército enemigo ahora mismo.");
            return;
        }

        System.out.println("\n====== NEW THREAT COMMING ======");
        System.out.println("Swordsman: " + enemyArmy[0].size());
        System.out.println("Spearman: " + enemyArmy[1].size());
        System.out.println("Crossbow: " + enemyArmy[2].size());
        System.out.println("Cannon: " + enemyArmy[3].size());
        System.out.println("================================");
    }

    // ============================================================
    // BATALLA SIMPLE + REPORTES
    // ============================================================
    private static void startBattle() {
        if (!hayEnemigo()) {
            System.out.println("[INFO] No hay enemigo para luchar.");
            return;
        }

        ArrayList<MilitaryUnit>[] playerArmy = civ.getArmy();

        int playerUnits = 0;
        int enemyUnits = 0;

        for (ArrayList<MilitaryUnit> list : playerArmy) {
            playerUnits += list.size();
        }
        for (ArrayList<MilitaryUnit> list : enemyArmy) {
            enemyUnits += list.size();
        }

        if (playerUnits == 0) {
            System.out.println("\n[DERROTA] No tienes ejército. El enemigo arrasa tu civilización.");
            addBattleReport("Derrota: sin ejército. Enemigo tenía " + enemyUnits + " unidades.");
            // Limpiamos enemigo
            for (ArrayList<MilitaryUnit> list : enemyArmy) list.clear();
            return;
        }

        // Sistema de batalla muy simple: comparamos número de unidades
        System.out.println("\n*** COMIENZA LA BATALLA ***");
        System.out.println("Tus unidades: " + playerUnits + " | Unidades enemigas: " + enemyUnits);

        if (playerUnits >= enemyUnits) {
            System.out.println("[VICTORIA] Has ganado la batalla.");
            addBattleReport("Victoria: " + playerUnits + " vs " + enemyUnits + " unidades.");
            // Perdemos algunas unidades (simulación simple)
            int perdidas = enemyUnits / 2;
            eliminarUnidades(playerArmy, perdidas);
        } else {
            System.out.println("[DERROTA] Has perdido la batalla.");
            addBattleReport("Derrota: " + playerUnits + " vs " + enemyUnits + " unidades.");
            int perdidas = playerUnits;
            eliminarUnidades(playerArmy, perdidas);
        }

        // El enemigo siempre se limpia tras la batalla
        for (ArrayList<MilitaryUnit> list : enemyArmy) {
            list.clear();
        }

        System.out.println("*** FIN DE LA BATALLA ***");
    }

    private static void eliminarUnidades(ArrayList<MilitaryUnit>[] army, int toRemove) {
        int removed = 0;
        for (int i = 0; i < army.length && removed < toRemove; i++) {
            while (!army[i].isEmpty() && removed < toRemove) {
                army[i].remove(army[i].size() - 1);
                removed++;
            }
        }
    }

    private static void addBattleReport(String report) {
        if (battleReports.size() == 5) {
            battleReports.remove(0);
        }
        battleReports.add(report);
    }

    private static void mostrarReportesBatalla() {
        System.out.println("\n===== REPORTES DE BATALLA (últimas 5) =====");
        if (battleReports.isEmpty()) {
            System.out.println("No hay batallas registradas todavía.");
        } else {
            for (int i = 0; i < battleReports.size(); i++) {
                System.out.println((i + 1) + ". " + battleReports.get(i));
            }
        }
        System.out.println("===========================================");
    }
}
