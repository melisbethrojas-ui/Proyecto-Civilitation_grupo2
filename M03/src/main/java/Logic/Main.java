package Logic;

import Save_Load.PersistenciaMySQL;
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

    // Ejército enemigo
    private static ArrayList<MilitaryUnit>[] enemyArmy = new ArrayList[4];

    // Reportes de batalla
    private static ArrayList<String> battleReports = new ArrayList<>();

    private static PersistenciaMySQL db = new PersistenciaMySQL();
    public static Civilization civ = null;


    public static void main(String[] args) {
        System.out.println("[SISTEMA] Iniciando Civilizations...");
        
        Scanner teclado = new Scanner(System.in);

        // Inicializar ejército enemigo
        for (int i = 0; i < enemyArmy.length; i++) {
            enemyArmy[i] = new ArrayList<>();
        }

        // =========================================================================
        // CAMBIO DE ORDEN TÉCNICO: PRIMERO SE CARGAN LOS DATOS DE MYSQL
        // =========================================================================
        civ = db.load();

        if (civ == null) {
            System.out.println("[INFO] No hay partida guardada. Creando una nueva...");
            civ = new Civilization();
        }

        // =========================================================================
        // TIMERS (ENCENDIDOS DE FORMA SEGURA DESPUÉS DE LA CARGA)
        // =========================================================================
        Timer timer = new Timer();

        // Producción automática de recursos cada 1 minuto
        TimerTask produccionTask = new TimerTask() {
            @Override
            public void run() {
                // Validación preventiva añadida para asegurar la comunicación asíncrona
                if (civ != null) {
                    civ.generateResources();
                    System.out.println("\n[+] Se han generado recursos automáticamente.");
                }
            }
        };
        timer.schedule(produccionTask, 0, 60000);

        // Crear ejército enemigo + batalla cada 3 minutos
        TimerTask enemyTask = new TimerTask() {
            @Override
            public void run() {
                // Validación preventiva para que no batalle si la memoria del hilo principal está ocupada
                if (civ != null) {
                    System.out.println("\n⚠ ¡Un ejército enemigo se aproxima!");
                    createEnemyArmy();
                    viewThreat();
                    startBattle();
                }
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
            System.out.println("8. Guardar y Salir");
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
                    mostrarReportesBatballa();
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

                    System.out.println("\n[GUARDANDO PARTIDA]...");
                    db.save(civ);

                    System.out.println("¡Gracias por jugar a Civilizations! Saliendo...");
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
        System.out.println(" RECURSOS: Comida: " + civ.food + " | Madera: " + civ.wood +
                " | Hierro: " + civ.iron + " | Mana: " + civ.mana);
        System.out.println(" EDIFICIOS: Granja: " + civ.farm +
                " | Carpinteria: " + civ.carpentry +
                " | Herreria: " + civ.smithy);
        System.out.println("            Iglesia: " + civ.church +
                " | T. Magica: " + civ.magicTower);
        System.out.println(" TECNOLOGIAS: Ataque: " + civ.getTechnologyAttack() +
                " | Defensa: " + civ.getTechnologyDefense());
        System.out.println("=======================================================================");
    }

    // ============================================================
    // EDIFICIOS + TECNOLOGÍAS
    // ============================================================
    private static void menuEdificiosYTecnologias(Civilization civ, Scanner teclado) {
        System.out.println("\n--- EDIFICIOS Y TECNOLOGÍAS ---");

        System.out.println("1. Construir Granja");
        System.out.println("2. Construir Carpintería");
        System.out.println("3. Construir Herrería");
        System.out.println("4. Construir Iglesia");
        System.out.println("5. Construir Torre Mágica");
        System.out.println("6. Mejorar Tecnología Ataque");
        System.out.println("7. Mejorar Tecnología Defensa");

        int op = leerNumero(teclado);

        try {
            switch (op) {
                case 1 -> civ.newFarm();
                case 2 -> civ.newCarpentry();
                case 3 -> civ.newSmithy();
                case 4 -> civ.newChurch();
                case 5 -> civ.newMagicTower();
                case 6 -> civ.upgradeTechnologyAttack();
                case 7 -> civ.upgradeTechnologyDefense();
                default -> System.out.println("Opción no válida.");
            }
            System.out.println("[ÉXITO] ¡Operación realizada correctamente!");
        } catch (ResourceException e) {
            System.out.println("[ERROR RECURSOS] " + e.getMessage());
        }
    }

    // ============================================================
    // RECLUTAMIENTO
    // ============================================================
    private static void menuReclutamiento(Civilization civ, Scanner teclado) {
        System.out.println("\n--- RECLUTAR UNIDADES ---");
        System.out.println("1. Swordsman");
        System.out.println("2. Spearman");
        System.out.println("3. Crossbow");
        System.out.println("4. Cannon");
        System.out.println("5. ArrowTower");
        System.out.println("6. Catapult");
        System.out.println("7. RocketTower");
        System.out.println("8. Magician");
        System.out.println("9. Priest");

        System.out.print("Elige el tipo de unidad: ");
        int tipo = leerNumero(teclado);

        System.out.print("¿Cuántas unidades quieres fabricar?: ");
        int cantidad = leerNumero(teclado);

        try {
            switch (tipo) {
                case 1 -> civ.newSwordsman(cantidad);
                case 2 -> civ.newSpearman(cantidad);
                case 3 -> civ.newCrossbow(cantidad);
                case 4 -> civ.newCannon(cantidad);
                case 5 -> civ.newArrowTower(cantidad);
                case 6 -> civ.newCatapult(cantidad);
                case 7 -> civ.newRocketLauncher(cantidad);
                case 8 -> civ.newMagician(cantidad);
                case 9 -> civ.newPriest(cantidad);
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

        ArrayList<MilitaryUnit>[] army = civ.army;

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
        for (int i = 0; i < enemyArmy.length; i++) {
            enemyArmy[i].clear();
        }

        Random rand = new Random();

        while (true) {
            if (enemyFood < 100 || enemyWood < 50 || enemyIron < 50) break;

            int prob = rand.nextInt(100);

            if (prob < 35) {
                enemyFood -= 100;
                enemyWood -= 50;
                enemyIron -= 50;
                enemyArmy[0].add(new Swordsman(0, 0));
            } else if (prob < 60) {
                enemyFood -= 120;
                enemyWood -= 60;
                enemyIron -= 60;
                enemyArmy[1].add(new Spearman(0, 0));
            } else if (prob < 80) {
                enemyFood -= 150;
                enemyWood -= 80;
                enemyIron -= 80;
                enemyArmy[2].add(new Crossbow(0, 0));
            } else {
                enemyFood -= 200;
                enemyWood -= 120;
                enemyIron -= 150;
                enemyArmy[3].add(new Cannon(0, 0));
            }
        }

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

        ArrayList<MilitaryUnit>[] playerArmy = civ.army;

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
            for (ArrayList<MilitaryUnit> list : enemyArmy) list.clear();
            return;
        }

        System.out.println("\n*** COMIENZA LA BATALLA ***");
        System.out.println("Tus unidades: " + playerUnits + " | Unidades enemigas: " + enemyUnits);

        if (playerUnits >= enemyUnits) {
            System.out.println("[VICTORIA] Has ganado la batalla.");
            addBattleReport("Victoria: " + playerUnits + " vs " + enemyUnits + " unidades.");
            int perdidas = enemyUnits / 2;
            eliminarUnidades(playerArmy, perdidas);
        } else {
            System.out.println("[DERROTA] Has perdido la batalla.");
            addBattleReport("Derrota: " + playerUnits + " vs " + enemyUnits + " unidades.");
            int perdidas = playerUnits;
            eliminarUnidades(playerArmy, perdidas);
        }

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

    private static void mostrarReportesBatballa() {
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