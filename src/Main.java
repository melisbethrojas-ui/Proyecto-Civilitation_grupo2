package src;

import java.util.Scanner;

import src.Logic.Civilization;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("------------------------------------------");
        System.out.println("       BIENVENIDO A CIVILIZATIONS         ");
        System.out.println("------------------------------------------");
        
        // 1. Inicializar la Civilización del jugador
        Civilization miCivilizacion = new Civilization();
        
        boolean jugando = true;
        
        while (jugando) {
            System.out.println("\n--- ESTADO DE LA CIUDAD ---");
            System.out.println("Batallas ganadas: " + miCivilizacion.getBattles());
            // Aquí la Persona 3 (GUI) mostrará recursos, pero por consola:
            System.out.println("1. Ir a la Guerra (Battle)");
            System.out.println("2. Gestionar Edificios");
            System.out.println("3. Reclutar Unidades");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            
            int opcion = sc.nextInt();
            
            switch (opcion) {
                case 1:
                    // 2. Iniciar el motor de batalla que creaste
                    System.out.println("\n¡Generando ejército enemigo y marchando al campo de batalla!");
                    src.Logic.Battle batalla = new src.Logic.Battle(miCivilizacion);
                    boolean victoria = batalla.startBattle();
                    
                    if (victoria) {
                        System.out.println("¡Has vuelto victorioso! Recursos obtenidos.");
                    } else {
                        System.out.println("Tus tropas han sido diezmadas...");
                    }
                    break;
                    
                case 2:
                    System.out.println("Menu de edificios (En desarrollo por Persona 3)");
                    // Aquí llamarías a miCivilizacion.getSmithy().levelUp(), etc.
                    break;
                    
                case 3:
                    System.out.println("Menu de reclutamiento (En desarrollo por Persona 1/3)");
                    break;
                    
                case 4:
                    jugando = false;
                    System.out.println("Guardando partida... (Tarea de Persona 2)");
                    break;
                    
                default:
                    System.out.println("Opción no válida.");
            }
        }
        
        System.out.println("¡Gracias por jugar!");
        sc.close();
    }
}