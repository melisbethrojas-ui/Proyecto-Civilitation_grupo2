package Civilitation_Proyect;

import java.util.Scanner;

public class Main implements Variables {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Civilization myCiv = new Civilization();
        int battleCount = 0;
        boolean gameOver = false;
        
        // --- CONTROL DE TIEMPO (Ataque cada 3 min) ---
        long startTime = System.currentTimeMillis(); 

        System.out.println("--- BIENVENIDO A CIVILIZATIONS PROJECT ---");

        while (!gameOver) {
            System.out.println("\n¿Qué deseas hacer?");
            System.out.println("1. Construir Edificio  2. Reclutar Unidades  3. Ver Estado  4. ¡A LA BATALLA!  5. Salir");
            
            try {
                int option = sc.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("\n--- MENÚ DE CONSTRUCCIÓN ---");
                        System.out.println("1. Carpintería (M:" + WOOD_COST_CARPENTRY + " H:" + IRON_COST_CARPENTRY + " C:" + FOOD_COST_CARPENTRY + ")");
                        System.out.println("2. Herrería    (M:" + WOOD_COST_SMITHY + " H:" + IRON_COST_SMITHY + " C:" + FOOD_COST_SMITHY + ")");
                        System.out.println("3. Granja      (M:" + WOOD_COST_FARM + " H:" + IRON_COST_FARM + " C:" + FOOD_COST_FARM + ")");
                        System.out.println("4. Torre Mágica(M:" + WOOD_COST_MAGICTOWER + " H:" + IRON_COST_MAGICTOWER + " C:" + FOOD_COST_MAGICTOWER + " Mana:" + MANA_COST_MAGICTOWER + ")");
                        System.out.println("5. Iglesia     (M:" + WOOD_COST_CHURCH + " H:" + IRON_COST_CHURCH + " C:" + FOOD_COST_CHURCH + ")");
                        System.out.println("0. <-- VOLVER ATRÁS");
                        System.out.print("\n¿Qué edificio deseas levantar para tu imperio? (Elige un número): ");
                        
                        int b = sc.nextInt();
                        if (b != 0) {
                            if (b == 1) myCiv.newCarpentry();
                            else if (b == 2) myCiv.newSmithy();
                            else if (b == 3) myCiv.newFarm(); 
                            else if (b == 4) myCiv.newMagicTower();
                            else if (b == 5) myCiv.newChurch();
                            else System.out.println("Opción no válida.");
                            System.out.println("Acción procesada.");
                        }
                        break;

                    case 2:
                        System.out.println("\n--- RECLUTAR TROPAS ---");
                        System.out.println("1. Espadachín (C:" + FOOD_COST_SWORDSMAN + " M:" + WOOD_COST_SWORDSMAN + " H:" + IRON_COST_SWORDSMAN + ")");
                        System.out.println("2. Ballestero (M:" + WOOD_COST_CROSSBOW + " H:" + IRON_COST_CROSSBOW + ")");
                        System.out.println("3. Cañón      (M:" + WOOD_COST_CANNON + " H:" + IRON_COST_CANNON + ")");
                        System.out.println("4. Mago       (C:" + FOOD_COST_MAGICIAN + " Mana:" + MANA_COST_MAGICIAN + ")");
                        System.out.println("5. Sacerdote  (C:" + FOOD_COST_PRIEST + " Mana:" + MANA_COST_PRIEST + ")");
                        System.out.println("0. <-- VOLVER ATRÁS");
                        System.out.print("\n¿A qué unidad deseas llamar a filas?: ");
                        
                        int u = sc.nextInt();
                        if (u != 0 && u >= 1 && u <= 5) {
                            System.out.print("¿Cuántos guerreros necesitas?: ");
                            int qty = sc.nextInt();
                            
                            if (u == 1) {
                                myCiv.newSwordsman(qty);
                            } else if (u == 2) { 
                                myCiv.newCrossbowman(qty); 
                            } else if (u == 3) {
                                myCiv.newCannon(qty);   
                            } else if (u == 4) {
                                myCiv.newMagician(qty);
                            } else if (u == 5) {
                                myCiv.newPriest(qty);
                            }
                            
                            System.out.println("¡Tropas reclutadas!");
                        }
                        break;

                    case 3:
                        myCiv.printStats();
                        break;

                    case 4:
                        // --- PREPARAR BATALLA (Saber el nivel) ---
                        System.out.println("\n--- INFORME DE INTELIGENCIA ---");
                        System.out.println("El enemigo tiene nivel: " + (battleCount + 1));
                        System.out.print("¿Deseas atacar ahora? (1: SI / 2: RETIRARSE): ");
                        int decide = sc.nextInt();

                        if (decide == 1) {
                            Battle battle = new Battle(myCiv.getArmy(), battleCount);
                            String result = battle.startBattle();
                            System.out.println(result);

                            if (result.contains("VICTORIA")) {
                                myCiv.addWood(battle.getWasteWood());
                                myCiv.addIron(battle.getWasteIron());
                                battleCount++;
                            } else {
                                System.out.println("Tu civilización ha caído en combate.");
                                gameOver = true;
                            }
                        } else {
                            System.out.println("Sabia decisión. Tus tropas descansan por ahora.");
                        }
                        break;

                    case 5:
                        gameOver = true;
                        break;
                    
                    default:
                        System.out.println("Opción no disponible.");
                        break;
                }

                // --- PRODUCCIÓN POR TURNO ---
                myCiv.produceResources(); 

                // --- ATAQUE AUTOMÁTICO (Cada 3 minutos = 180.000 ms) ---
                long currentTime = System.currentTimeMillis();
                if (currentTime - startTime >= 180000) {
                    System.out.println("\n⚠️ ¡ALERTA! ¡HA PASADO DEMASIADO TIEMPO Y TE HAN EMBOSCADO!");
                    Battle ambush = new Battle(myCiv.getArmy(), battleCount);
                    String resAmbush = ambush.startBattle();
                    System.out.println(resAmbush);

                    if (!resAmbush.contains("VICTORIA")) {
                        System.out.println("La emboscada fue fatal...");
                        gameOver = true;
                    } else {
                        System.out.println("¡Has sobrevivido al ataque sorpresa!");
                        battleCount++; // También cuenta como victoria
                    }
                    startTime = System.currentTimeMillis(); // Reiniciar cronómetro
                }

            } catch (ResourceException | BuildingException e) {
                System.err.println("ALERTA: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("ERROR: Introduce un valor válido.");
                sc.nextLine(); 
            }
        } 
        System.out.println("Gracias por jugar.");
        sc.close();
    }
}