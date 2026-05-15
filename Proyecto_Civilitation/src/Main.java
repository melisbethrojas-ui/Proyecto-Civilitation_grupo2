import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Civilization c1 = new Civilization();
        Civilization c2 = new Civilization();

        int option;

        do {
            System.out.println("\n=== MENÚ ===");
            System.out.println("1. Construir Farm");
            System.out.println("2. Crear Swordsman");
            System.out.println("3. Producir recursos");
            System.out.println("4. Stats");
            System.out.println("5. Batalla");
            System.out.println("0. Salir");

            option = sc.nextInt();

            try {
                switch (option) {
                case 1:
                    c1.newFarm();
                    break;
                case 2:
                    c1.newSwordsman(0);
                    break;
                case 3:
                    c1.produceResources();
                    break;
                case 4:
                    c1.printStats();
                    break;
                case 5:
                    Battle b = new Battle(c1, c2);
                    b.startBattle();
                    break;
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }

        } while (option != 0);

        sc.close();
    }
}
