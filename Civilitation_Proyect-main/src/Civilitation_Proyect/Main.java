package Civilitation_Proyect;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        Civilization civ = new Civilization();

        // Uso un scheduler porque es más cómodo que Timer
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // Producción automática cada minuto
        scheduler.scheduleAtFixedRate(() -> {
            civ.produceResources();
            System.out.println("\n[+] Se han generado recursos automáticamente.");
        }, 0, 1, TimeUnit.MINUTES);

        // Ataque enemigo cada 3 minutos
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("\n⚠ ¡Un ejército enemigo se aproxima!");
            // Aquí luego se llamará a la batalla real
        }, 3, 3, TimeUnit.MINUTES);

        boolean salir = false;

        System.out.println("=========================================");
        System.out.println("   BIENVENIDO A CIVILIZATIONS EMPIRE     ");
        System.out.println("=========================================");

        while (!salir) {

            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Ver estado");
            System.out.println("2. Crear edificios / Tecnologías");
            System.out.println("3. Reclutar tropas");
            System.out.println("4. Ver ejército");
            System.out.println("5. Ver enemigo");
            System.out.println("6. Batalla");
            System.out.println("7. Salir");

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
                    System.out.println("\n[INFO] Aquí iría la función de ver enemigo.");
                    break;

                case 6:
                    System.out.println("\n[INFO] Aquí iría la batalla.");
                    break;

                case 7:
                    salir = true;
                    scheduler.shutdownNow();
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
        System.out.println("1. Granja");
        System.out.println("2. Carpintería");
        System.out.println("3. Herrería");
        System.out.println("4. Iglesia");
        System.out.println("5. Torre Mágica");
        System.out.println("6. Mejora Tecnología Ataque");
        System.out.println("7. Mejora Tecnología Defensa");
        System.out.print("Selecciona una opción: ");

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
// EJÉRCITO
// ============================================================
private static void mostrarEjercito(Civilization civ) {
    System.out.println("\n================ EJÉRCITO ================");

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

    System.out.println("==========================================");
}

    // ============================================================
    // RECLUTAMIENTO
    // ============================================================
    private static void menuReclutamiento(Civilization civ, Scanner teclado) {
        System.out.println("\n--- RECLUTAR UNIDADES ---");
        System.out.println("1. Swordsman     2. Spearman      3. Crossbow      4. Cannon");
        System.out.println("5. ArrowTower    6. Catapult      7. RocketTower");
        System.out.println("8. Magician      9. Priest");
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
}
