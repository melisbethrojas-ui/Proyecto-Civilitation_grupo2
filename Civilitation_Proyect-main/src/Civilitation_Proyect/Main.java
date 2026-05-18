package Civilitation_Proyect;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Civilization miCivilizacion = new Civilization();
        
        // Contador para llevar el registro de las batallas jugadas
        int numeroBatallas = 0;
        boolean salir = false;

        System.out.println("=========================================");
        System.out.println("   BIENVENIDO A CIVILIZATIONS EMPIRE     ");
        System.out.println("=========================================");

        while (!salir) {
            // Imprimimos el estado actual de la civilización en cada ciclo
            mostrarEstado(miCivilizacion);
            
            // MENU PRINCIPAL
            System.out.println("\n--- MENU DE ACCIONES ---");
            System.out.println("1. Pasar turno (Producir Recursos)");
            System.out.println("2. Mejorar Edificio");
            System.out.println("3. Mejorar Tecnologia Militar");
            System.out.println("4. Reclutar Tropas / Construir Defensas");
            System.out.println("5. Simular Batalla (Siguiente Oleada)");
            System.out.println("6. Salir del Juego");
            System.out.print("Selecciona una opcion: ");
            
            int opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    // Al pasar turno sumamos los recursos que generan los edificios
                    miCivilizacion.produceResources();
                    System.out.println("\n[TURNO PASADO] Tus edificios han generado nuevos materiales.");
                    break;

                case 2:
                    menuEdificios(miCivilizacion, teclado);
                    break;

                case 3:
                    menuTecnologias(miCivilizacion, teclado);
                    break;

                case 4:
                    menuReclutamiento(miCivilizacion, teclado);
                    break;

                case 5:
                    // Aqui llamariamos al motor de batalla que programamos
                    System.out.println("\n--- PREPARANDO EJERCITOS PARA EL COMBATE ---");
                    // Nota: En la practica real aqui instanciariamos al enemigo y lanzariamos la Battle
                    System.out.println("Simulando asalto de batalla...");
                    numeroBatallas++;
                    System.out.println("Batalla Nº " + numeroBatallas + " finalizada de forma simulada.");
                    break;

                case 6:
                    salir = true;
                    System.out.println("\n¡Gracias por jugar a Civilizations! Saliendo...");
                    break;

                default:
                    System.out.println("\n[ERROR] Opcion no valida, introduce un numero del 1 al 6.");
                    break;
            }
        }
        teclado.close();
    }

    // Metodo auxiliar para pintar de forma sencilla el estado por consola
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

    // Submenu para la gestion de subidas de nivel de los edificios
    private static void menuEdificios(Civilization civ, Scanner teclado) {
        System.out.println("\n--- MEJORAR EDIFICIOS ---");
        System.out.println("1. Granja");
        System.out.println("2. Carpinteria");
        System.out.println("3. Herreria");
        System.out.println("4. Iglesia");
        System.out.println("5. Torre Magica");
        System.out.print("¿Que edificio deseas mejorar? ");
        int ed = teclado.nextInt();

        try {
            if (ed == 1) civ.upgradeFarm();
            else if (ed == 2) civ.upgradeCarpentry();
            else if (ed == 3) civ.upgradeSmithy();
            else if (ed == 4) civ.upgradeChurch();
            else if (ed == 5) civ.upgradeMagicTower();
            else System.out.println("Edificio no valido.");
        } catch (ResourceException e) {
            // Capturamos la excepcion si faltan materiales
            System.out.println("[ERROR RECURSOS] " + e.getMessage());
        }
    }

    // Submenu para mejorar las tecnologias usando try-catch
    private static void menuTecnologias(Civilization civ, Scanner teclado) {
        System.out.println("\n--- MEJORAR TECNOLOGIAS MILITARES ---");
        System.out.println("1. Tecnologia de Ataque (Incrementa el daño)");
        System.out.println("2. Tecnologia de Defensa / Armadura (Incrementa la salud)");
        System.out.print("Selecciona una opcion: ");
        int tech = teclado.nextInt();

        try {
            if (tech == 1) civ.upgradeAttackTechnology();
            else if (tech == 2) civ.upgradeArmorTechnology();
            else System.out.println("Tecnologia no valida.");
        } catch (ResourceException e) {
            System.out.println("[ERROR RECURSOS] " + e.getMessage());
        }
    }

    // Submenu interactivo para el reclutamiento por lotes aplicando las excepciones del PDF
    private static void menuReclutamiento(Civilization civ, Scanner teclado) {
        System.out.println("\n--- BARRACONES DE RECLUTAMIENTO ---");
        System.out.println("1. Swordsman     2. Spearman      3. Crossbow      4. Cannon");
        System.out.println("5. ArrowTower    6. Catapult      7. RocketTower");
        System.out.println("8. Magician (Requiere Torre)      9. Priest (Requiere Iglesia)");
        System.out.print("Elige el tipo de unidad: ");
        int tipo = teclado.nextInt();
        
        System.out.print("¿Cuantas unidades de este tipo quieres fabricar? ");
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
                default: System.out.println("Tipo de unidad incorrecto."); break;
            }
            System.out.println("¡Reclutamiento completado con exito!");
        } catch (ResourceException e) {
            System.out.println("[ERROR RECURSOS] " + e.getMessage());
        } catch (BuildingException e) {
            // Captura el error especifico si no se cumple el requisito del edificio magico/iglesia
            System.out.println("[ERROR REQUISITO EDIFICIO] " + e.getMessage());
        }
    }
}