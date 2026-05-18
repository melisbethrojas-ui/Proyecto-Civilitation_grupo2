package Civilitation_Proyect;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Civilization miCivilizacion = new Civilization();
        
        boolean salir = false;

        // ============================================================
        // TIMER 1 → PRODUCCIÓN AUTOMÁTICA CADA 1 MINUTO
        // ============================================================
        Timer timerRecursos = new Timer();
        TimerTask tareaProduccion = new TimerTask() {
            @Override
            public void run() {
                miCivilizacion.produceResources();
                System.out.println("\n[+] Se han generado recursos automáticamente.");
            }
        };
        timerRecursos.scheduleAtFixedRate(tareaProduccion, 0, 60000); // 60.000 ms = 1 minuto

        // ============================================================
        // TIMER 2 → ATAQUE ENEMIGO CADA 3 MINUTOS
        // ============================================================
        Timer timerAtaque = new Timer();
        TimerTask tareaAtaque = new TimerTask() {
            @Override
            public void run() {
                System.out.println("\n⚠ ¡Un ejército enemigo se aproxima!");
                // Aquí luego podrás llamar a tu Battle real
            }
        };
        timerAtaque.scheduleAtFixedRate(tareaAtaque, 180000, 180000); // 180.000 ms = 3 minutos

        // ============================================================
        // MENÚ PRINCIPAL
        // ============================================================
        System.out.println("=========================================");
        System.out.println("   BIENVENIDO A CIVILIZATIONS EMPIRE     ");
        System.out.println("=========================================");

        while (!salir) {

            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Ver estado");
            System.out.println("2. Crear edificios / Tecnologías");
            System.out.println("3. Reclutar tropas");
            System.out.println("4. Ver enemigo");
            System.out.println("5. Batalla");
            System.out.println("6. Salir");
            System.out.print("Selecciona una opción: ");

            int opcion = teclado.nextInt();

            switch (opcion) {

                case 1:
                    mostrarEstado(miCivilizacion);
                    break;

                case 2:
                    menuEdificiosYTecnologias(miCivilizacion, teclado);
                    break;

                case 3:
                    menuReclutamiento(miCivilizacion, teclado);
                    break;

                case 4:
                    System.out.println("\n[INFO] Aquí iría la función de ver enemigo.");
                    break;

                case 5:
                    System.out.println("\n[INFO] Aquí iría la batalla.");
                    break;

                case 6:
                    salir = true;
                    timerRecursos.cancel();
                    timerAtaque.cancel();
                    System.out.println("\n¡Gracias por jugar a Civilizations! Saliendo...");
                    break;

                default:
                    System.out.println("\n[ERROR] Opción no válida.");
                    break;
            }
        }

        teclado.close();
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

        int op = teclado.nextInt();

        try {
            switch (op) {
                case 1: civ.upgradeFarm(); break;
                case 2: civ.upgradeCarpentry(); break;
                case 3: civ.upgradeSmithy(); break;
                case 4: civ.upgradeChurch(); break;
                case 5: civ.upgradeMagicTower(); break;
                case 6: civ.upgradeAttackTechnology(); break;
                case 7: civ.upgradeArmorTechnology(); break;
                default: System.out.println("Opción no válida.");
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
        System.out.println("1. Swordsman     2. Spearman      3. Crossbow      4. Cannon");
        System.out.println("5. ArrowTower    6. Catapult      7. RocketTower");
        System.out.println("8. Magician      9. Priest");
        System.out.print("Elige el tipo de unidad: ");

        int tipo = teclado.nextInt();

        System.out.print("¿Cuántas unidades quieres fabricar?: ");
        int cantidad = teclado.nextInt();

        try {
            switch (tipo) {
                case 1: civ.recruitSwordsman(cantidad); break;
                case 2: civ.recruitSpearman(cantidad); break;
                case 3: civ.recruitCrossbow(cantidad); break;
                case 4: civ.recruitCannon(cantidad); break;
                case 5: civ.recruitArrowTower(cantidad); break;
                case 6: civ.recruitCatapult(cantidad); break;
                case 7: civ.recruitRocketTower(cantidad); break;
                case 8: civ.recruitMagician(cantidad); break;
                case 9: civ.recruitPriest(cantidad); break;
                default: System.out.println("Tipo de unidad incorrecto.");
            }
            System.out.println("¡Reclutamiento completado!");
        } catch (ResourceException e) {
            System.out.println("[ERROR RECURSOS] " + e.getMessage());
        } catch (BuildingException e) {
            System.out.println("[ERROR EDIFICIO] " + e.getMessage());
        }
    }
}
