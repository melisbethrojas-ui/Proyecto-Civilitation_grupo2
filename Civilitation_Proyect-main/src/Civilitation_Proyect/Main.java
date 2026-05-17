package Civilitation_Proyect;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main implements Variables {
    
    // Hacemos la civilización y el contador estáticos para que los hilos puedan acceder a ellos
    private static Civilization myCiv = new Civilization();
    private static int battleCount = 0;
    private static boolean gameOver = false;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("--- BIENVENIDO A CIVILIZATIONS PROJECT ---");

        // =====================================================================
        // HILO 1: GENERACIÓN PASIVA DE RECURSOS (Cada 1 minuto = 60000 ms)
        // =====================================================================
        Timer timerRecursos = new Timer(true); // 'true' para que corra de fondo (daemon)
        timerRecursos.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!gameOver) {
                    // Llamamos directamente al método optimizado de tu Civilization
                    myCiv.produceResources();
                    
                    // Notificación silenciosa en consola para avisar al jugador
                    System.out.println("\n[PRODUCCIÓN] +MINUTO PASADO+ Recursos generados por tus edificios y añadidos al almacén.");
                }
            }
        }, 10000, 10000); // Empieza en 1 min, se repite cada 1 min


        // =====================================================================
        // HILO 2: ATAQUE ENEMIGO AUTOMÁTICO (Cada 3 minutos = 180000 ms)
        // =====================================================================
        Timer timerAtaques = new Timer(true);
        timerAtaques.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!gameOver) {
                    System.out.println("\n¡ALERTA! ¡LA FLOTA ENEMIGA TE HA LOCALIZADO Y LANZA UN ATAQUE AUTOMÁTICO! ⚠️ ⚠️");
                    
                    if (myCiv.getArmy().isEmpty()) {
                        System.out.println("¡No tenías tropas defendiendo tu imperio! Tu civilización ha sido arrasada.");
                        gameOver = true;
                        System.out.println("Presiona cualquier número y ENTER para salir.");
                    } else {
                        // Generamos el ejército enemigo para este nivel
                        ArrayList<MilitaryUnit> enemyArmy = generateEnemyArmy(battleCount);
                        
                        // Creamos y ejecutamos la batalla
                        Battle ambush = new Battle(myCiv.getArmy(), enemyArmy);
                        ambush.startBattle();
                        
                        // Mostramos todo el desarrollo de la batalla por consola
                        System.out.println(ambush.getBattleDevelopment());

                        if (ambush.getCivilizationArmy().isEmpty()) {
                            System.out.println("Tu ejército fue completamente aniquilado en la emboscada...");
                            gameOver = true;
                            System.out.println("Presiona cualquier número y ENTER para salir.");
                        } else {
                            System.out.println("¡Increíble! Has repelido el ataque sorpresa.");
                            // Al ganar sumamos los materiales/escombros recogidos del campo de batalla
                            myCiv.setWood(myCiv.getWood() + ambush.getWasteWood());
                            myCiv.setIron(myCiv.getIron() + ambush.getWasteIron());
                            battleCount++;
                        }
                    }
                }
            }
        }, 180000, 180000); // Empieza en 3 min, se repite cada 3 min


        // =====================================================================
        // BUCLE PRINCIPAL DEL JUEGO (Menú por consola)
        // =====================================================================
        while (!gameOver) {
            System.out.println("\n¿Qué deseas hacer?");
            System.out.println("1. Construir Edificio  2. Reclutar Unidades  3. Ver Estado  4. ¡A LA BATALLA VOLUNTARIA!  5. Salir");
            
            try {
                int option = sc.nextInt();

                if (gameOver) break; // Por si el hilo nos mató mientras esperábamos input

                if (option == 1) {
                    System.out.println("\n--- MENÚ DE CONSTRUCCIÓN ---");
                    System.out.println("1. Carpintería (M:" + WOOD_COST_CARPENTRY + " H:" + IRON_COST_CARPENTRY + " C:" + FOOD_COST_CARPENTRY + ")");
                    System.out.println("2. Herrería    (M:" + WOOD_COST_SMITHY + " H:" + IRON_COST_SMITHY + " C:" + FOOD_COST_SMITHY + ")");
                    System.out.println("3. Granja      (M:" + WOOD_COST_FARM + " H:" + IRON_COST_FARM + " C:" + FOOD_COST_FARM + ")");
                    System.out.println("4. Torre Mágica(M:" + WOOD_COST_MAGICTOWER + " H:" + IRON_COST_MAGICTOWER + " C:" + FOOD_COST_MAGICTOWER + " Mana:" + MANA_COST_MAGICTOWER + ")");
                    System.out.println("5. Iglesia     (M:" + WOOD_COST_CHURCH + " H:" + IRON_COST_CHURCH + " C:" + FOOD_COST_CHURCH + ")");
                    System.out.println("0. <-- VOLVER ATRÁS");
                    System.out.print("\n¿Qué edificio deseas levantar?: ");
                    
                    int b = sc.nextInt();
                    if (b != 0) {
                        if (b == 1) myCiv.upgradeCarpentry(); 
                        else if (b == 2) myCiv.upgradeSmithy();
                        else if (b == 3) myCiv.upgradeFarm(); 
                        else if (b == 4) myCiv.upgradeMagicTower();
                        else if (b == 5) myCiv.upgradeChurch();
                        else System.out.println("Opción no válida.");
                        System.out.println("Acción procesada.");
                    }

                } else if (option == 2) {
                    System.out.println("\n--- RECLUTAR TROPAS ---");
                    System.out.println("1. Espadachín (C:" + FOOD_COST_SWORDSMAN + " M:" + WOOD_COST_SWORDSMAN + " H:" + IRON_COST_SWORDSMAN + ")");
                    System.out.println("2. Ballestero (M:" + WOOD_COST_CROSSBOW + " H:" + IRON_COST_CROSSBOW + ")");
                    System.out.println("3. Cañón      (M:" + WOOD_COST_CANNON + " H:" + IRON_COST_CANNON + ")");
                    System.out.println("4. Mago       (C:" + FOOD_COST_MAGICIAN + " Mana:" + MANA_COST_MAGICIAN + ")");
                    System.out.println("5. Sacerdote  (C:" + FOOD_COST_PRIEST + " Mana:" + MANA_COST_PRIEST + ")");
                    System.out.println("0. <-- VOLVER ATRÁS");
                    System.out.print("\n¿A qué unidad deseas llamar?: ");
                    
                    int u = sc.nextInt();
                    if (u != 0 && u >= 1 && u <= 5) {
                        System.out.print("¿Cuántos guerreros necesitas?: ");
                        int qty = sc.nextInt();
                        
                        for (int i = 0; i < qty; i++) {
                            if (u == 1) myCiv.recruitSwordsman();
                            else if (u == 2) myCiv.recruitCrossbow(); 
                            else if (u == 3) myCiv.recruitCannon();   
                            else if (u == 4) myCiv.recruitMagician();
                            else if (u == 5) myCiv.recruitPriest();
                        }
                        System.out.println("¡Tropas reclutadas!");
                    }

                } else if (option == 3) {
                    System.out.println("\n=============================================");
                    System.out.println("        ESTADO DE LA CIVILIZACIÓN            ");
                    System.out.println("=============================================");
                    System.out.printf("Comida: %-6d | Madera: %-6d | Hierro: %-6d | Mana: %-6d\n", 
                            myCiv.getFood(), myCiv.getWood(), myCiv.getIron(), myCiv.getMana());
                    
                    System.out.println("\n--- EDIFICIOS CONSTRUIDOS ---");
                    System.out.println("  Granja:       Lvl " + myCiv.getFarmLevel());
                    System.out.println("  Carpinteria:  Lvl " + myCiv.getCarpentryLevel());
                    System.out.println("  Herreria:     Lvl " + myCiv.getSmithyLevel());
                    System.out.println("  Torre Magica: " + (myCiv.getMagicTowerLevel() > 0 ? "Lvl " + myCiv.getMagicTowerLevel() : "No construida"));
                    System.out.println("  Iglesia:      " + (myCiv.getChurchLevel() > 0 ? "Lvl " + myCiv.getChurchLevel() : "No construida"));
                    
                    System.out.println("\n--- TECNOLOGIAS ---");
                    System.out.println("  Tecnologia de Ataque:   Lvl " + myCiv.getAttackTechnologyLevel());
                    System.out.println("  Tecnologia de Armadura: Lvl " + myCiv.getArmorTechnologyLevel());

                    System.out.println("\n--- CUARTEL Y TROPAS DISPONIBLES ---");
                    int swordsmen = 0, spearmen = 0, crossbows = 0, cannons = 0, magicians = 0, priests = 0;
                    
                    for (MilitaryUnit unit : myCiv.getArmy()) {
                        if (unit instanceof Swordsman) swordsmen++;
                        else if (unit instanceof Spearman) spearmen++;
                        else if (unit instanceof Crossbow) crossbows++;
                        else if (unit instanceof Cannon) cannons++;
                        else if (unit instanceof Magician) magicians++;
                        else if (unit instanceof Priest) priests++;
                    }
                    
                    System.out.println("Total de unidades militares: " + myCiv.getArmy().size());
                    System.out.println("  Espadachines: " + swordsmen);
                    System.out.println("  Lanceros:     " + spearmen);
                    System.out.println("  Ballesteros:  " + crossbows);
                    System.out.println("  Canones:      " + cannons);
                    System.out.println("  Magos:        " + magicians);
                    System.out.println("  Sacerdotes:   " + priests);
                    System.out.println("=============================================");
                } else if (option == 4) {
                    System.out.println("\n--- INFORME DE INTELIGENCIA ---");
                    System.out.println("La flota enemiga esperada es de nivel: " + (battleCount + 1));
                    System.out.print("¿Deseas lanzar un ataque preventivo voluntario? (1: SI / 2: RETIRARSE): ");
                    int decide = sc.nextInt();

                    if (decide == 1) {
                        if (myCiv.getArmy().isEmpty()) {
                            System.out.println("No puedes atacar sin soldados.");
                        } else {
                            ArrayList<MilitaryUnit> enemyArmy = generateEnemyArmy(battleCount);
                            Battle battle = new Battle(myCiv.getArmy(), enemyArmy);
                            
                            battle.startBattle();
                            System.out.println(battle.getBattleDevelopment());

                            if (battle.getCivilizationArmy().isEmpty()) {
                                System.out.println("Tu civilización ha sido derrotada por completo.");
                                gameOver = true;
                            } else {
                                // Ganamos: recolectamos escombros de madera y hierro
                                myCiv.setWood(myCiv.getWood() + battle.getWasteWood());
                                myCiv.setIron(myCiv.getIron() + battle.getWasteIron());
                                battleCount++;
                            }
                        }
                    }

                } else if (option == 5) {
                    gameOver = true;
                } else {
                    System.out.println("Opción no disponible.");
                }

            } catch (ResourceException e) {
                System.err.println("ALERTA RECURSOS: " + e.getMessage());
            } catch (BuildingException e) {
                System.err.println("ALERTA EDIFICIO: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("ERROR: Introduce un valor válido.");
                sc.nextLine(); 
            }
        } 
        
        // Cancelamos los timers para que la aplicación de consola se cierre limpiamente al salir
        timerRecursos.cancel();
        timerAtaques.cancel();
        System.out.println("Gracias por jugar. Fin de la partida.");
        sc.close();
    }

    /**
     * Generador de ejército enemigo balanceado según porcentajes de tu PDF
     */
    private static ArrayList<MilitaryUnit> generateEnemyArmy(int level) {
        ArrayList<MilitaryUnit> enemy = new ArrayList<>();
        int multiplier = 100 + (level * ENEMY_FLEET_INCREASE);
        
        int virtualIron = (IRON_BASE_ENEMY_ARMY * multiplier) / 100;
        int virtualWood = (WOOD_BASE_ENEMY_ARMY * multiplier) / 100;
        
        // Consumimos los recursos del enemigo dándole tropas variadas
        while (virtualIron > 100 && virtualWood > 2000) {
            enemy.add(new Swordsman());
            enemy.add(new Spearman());
            virtualIron -= (IRON_COST_SWORDSMAN + IRON_COST_SPEARMAN);
            virtualWood -= (WOOD_COST_SWORDSMAN + WOOD_COST_SPEARMAN);
        }
        return enemy;
    }
}