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
        // HILO 1: GENERACIÓN PASIVA DE RECURSOS (Cada 1 minuto)
        // =====================================================================
        Timer timerRecursos = new Timer(true); 
        timerRecursos.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!gameOver) {
                    myCiv.produceResources();
                    System.out.println("\n[PRODUCCIÓN] +MINUTO PASADO+ Recursos generados por tus edificios y añadidos al almacén.");
                }
            }
        }, 60000, 60000); // 1 minuto reglamentario


        // =====================================================================
        // HILO 2: ATAQUE ENEMIGO AUTOMÁTICO (Cada 3 minutos = 180000 ms)
        // =====================================================================
        Timer timerAtaques = new Timer(true);
        timerAtaques.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!gameOver) {
                    System.out.println("\n⚠️ ¡ALERTA! ¡LA FLOTA ENEMIGA TE HA LOCALIZADO Y LANZA UN ATAQUE AUTOMÁTICO! ⚠️");
                    
                    if (isArmyEmpty(myCiv.getArmy())) {
                        System.out.println("¡No tenías tropas defendiendo tu imperio! Tu civilización ha sido arrasada.");
                        gameOver = true;
                        System.out.println("Presiona cualquier número y ENTER para salir.");
                    } else {
                        // Generamos el ejército enemigo para este nivel
                        ArrayList<MilitaryUnit>[] enemyArmy = generateEnemyArmy(battleCount);
                        
                        // NOTA: Recuerda que tu clase Battle debe estar adaptada para recibir Array de ArrayLists en el constructor
                        Battle ambush = new Battle(myCiv.getArmy(), enemyArmy);
                        ambush.startBattle();
                        
                        System.out.println(ambush.getBattleDevelopment());

                        if (isArmyEmpty(myCiv.getArmy())) {
                            System.out.println("Tu ejército fue completamente aniquilado en la emboscada...");
                            gameOver = true;
                            System.out.println("Presiona cualquier número y ENTER para salir.");
                        } else {
                            System.out.println("¡Increíble! Has repelido el ataque sorpresa.");
                            myCiv.setWood(myCiv.getWood() + ambush.getWasteWood());
                            myCiv.setIron(myCiv.getIron() + ambush.getWasteIron());
                            battleCount++;
                        }
                    }
                }
            }
        }, 180000, 180000); // 3 minutos reglamentarios


        // =====================================================================
        // BUCLE PRINCIPAL DEL JUEGO (Menú por consola)
        // =====================================================================
        while (!gameOver) {
            System.out.println("\n¿Qué deseas hacer?");
            System.out.println("1. Gestión de Edificios  2. Reclutar Unidades  3. Ver Estado  4. ¡BATALLA VOLUNTARIA!  5. Salir");
            System.out.print("Selecciona una opción: ");
            
            try {
                int option = sc.nextInt();

                if (gameOver) break; 

                if (option == 1) {
                    System.out.println("\n--- MENÚ DE CONSTRUCCIÓN Y TECNOLOGÍA ---");
                    System.out.println("1. Carpintería    (M:" + WOOD_COST_CARPENTRY + " H:" + IRON_COST_CARPENTRY + " C:" + FOOD_COST_CARPENTRY + ")");
                    System.out.println("2. Herrería       (M:" + WOOD_COST_SMITHY + " H:" + IRON_COST_SMITHY + " C:" + FOOD_COST_SMITHY + ") *Coste aumenta por nivel");
                    System.out.println("3. Granja         (M:" + WOOD_COST_FARM + " H:" + IRON_COST_FARM + " C:" + FOOD_COST_FARM + ")");
                    System.out.println("4. Torre Mágica   (M:" + WOOD_COST_MAGICTOWER + " H:" + IRON_COST_MAGICTOWER + " C:" + FOOD_COST_MAGICTOWER + " Mana:" + MANA_COST_MAGICTOWER + ") *Coste aumenta por nivel");
                    System.out.println("5. Iglesia        (M:" + WOOD_COST_CHURCH + " H:" + IRON_COST_CHURCH + " C:" + FOOD_COST_CHURCH + ")");
                    System.out.println("6. Investigar Tecnología de Ataque   (Hierro incremental)");
                    System.out.println("7. Investigar Tecnología de Armadura (Hierro incremental)");
                    System.out.println("0. <-- VOLVER ATRÁS");
                    System.out.print("\nSelección: ");
                    
                    int b = sc.nextInt();
                    if (b != 0) {
                        if (b == 1) myCiv.upgradeCarpentry(); 
                        else if (b == 2) myCiv.upgradeSmithy();
                        else if (b == 3) myCiv.upgradeFarm(); 
                        else if (b == 4) myCiv.upgradeMagicTower();
                        else if (b == 5) myCiv.upgradeChurch();
                        else if (b == 6) myCiv.upgradeAttackTechnology();
                        else if (b == 7) myCiv.upgradeArmorTechnology();
                        else System.out.println("Opción no válida.");
                    }

                } else if (option == 2) {
                    System.out.println("\n--- RECLUTAR TROPAS ---");
                    System.out.println("1. Espadachín (Índice 0) -> C:" + FOOD_COST_SWORDSMAN + " M:" + WOOD_COST_SWORDSMAN + " H:" + IRON_COST_SWORDSMAN);
                    System.out.println("2. Ballestero (Índice 2) -> M:" + WOOD_COST_CROSSBOW + " H:" + IRON_COST_CROSSBOW);
                    System.out.println("3. Cañón      (Índice 3) -> M:" + WOOD_COST_CANNON + " H:" + IRON_COST_CANNON);
                    System.out.println("4. Mago       (Índice 7) -> C:" + FOOD_COST_MAGICIAN + " Mana:" + MANA_COST_MAGICIAN + " *Requiere Torre");
                    System.out.println("5. Sacerdote  (Índice 8) -> C:" + FOOD_COST_PRIEST + " Mana:" + MANA_COST_PRIEST + " *Requiere Iglesia");
                    System.out.println("0. <-- VOLVER ATRÁS");
                    System.out.print("\n¿A qué unidad deseas llamar?: ");
                    
                    int u = sc.nextInt();
                    if (u != 0 && u >= 1 && u <= 5) {
                        System.out.print("¿Cuántos guerreros necesitas?: ");
                        int qty = sc.nextInt();
                        
                        // CORRECCIÓN 2: Llamamos pasándole la cantidad directamente al método del lote optimizado
                        if (u == 1) myCiv.recruitSwordsman(qty);
                        else if (u == 2) myCiv.recruitCrossbow(qty); 
                        else if (u == 3) myCiv.recruitCannon(qty);   
                        else if (u == 4) myCiv.recruitMagician(qty);
                        else if (u == 5) myCiv.recruitPriest(qty);
                        
                        System.out.println("Procesando orden de reclutamiento...");
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

                    // CORRECCIÓN 1: Contamos recorriendo de forma correcta los ArrayLists por índice
                    System.out.println("\n--- CUARTEL Y TROPAS DISPONIBLES ---");
                    ArrayList<MilitaryUnit>[] armyArray = myCiv.getArmy();
                    int totalUnits = 0;
                    for (int i = 0; i < armyArray.length; i++) {
                        totalUnits += armyArray[i].size();
                    }
                    
                    System.out.println("Total de unidades militares: " + totalUnits);
                    System.out.println("  [0] Espadachines: " + armyArray[0].size());
                    System.out.println("  [1] Lanceros:     " + armyArray[1].size());
                    System.out.println("  [2] Ballesteros:  " + armyArray[2].size());
                    System.out.println("  [3] Cañones:      " + armyArray[3].size());
                    System.out.println("  [7] Magos:        " + armyArray[7].size());
                    System.out.println("  [8] Sacerdotes:   " + armyArray[8].size());
                    System.out.println("=============================================");
                    
                } else if (option == 4) {
                    System.out.println("\n--- INFORME DE INTELIGENCIA ---");
                    System.out.println("La flota enemiga esperada es de nivel: " + (battleCount + 1));
                    System.out.print("¿Deseas lanzar un ataque preventivo voluntario? (1: SI / 2: RETIRARSE): ");
                    int decide = sc.nextInt();

                    if (decide == 1) {
                        if (isArmyEmpty(myCiv.getArmy())) {
                            System.out.println("No puedes atacar sin soldados.");
                        } else {
                            ArrayList<MilitaryUnit>[] enemyArmy = generateEnemyArmy(battleCount);
                            Battle battle = new Battle(myCiv.getArmy(), enemyArmy);
                            
                            battle.startBattle();
                            System.out.println(battle.getBattleDevelopment());

                            if (isArmyEmpty(myCiv.getArmy())) {
                                System.out.println("Tu civilización ha sido derrotada por completo.");
                                gameOver = true;
                            } else {
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
                System.out.println("\n[ALERTA RECURSOS] " + e.getMessage());
            } catch (BuildingException e) {
                System.out.println("\n[ALERTA EDIFICIO] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n[ERROR] Introduce un valor numérico válido.");
                sc.nextLine(); 
            }
        } 
        
        timerRecursos.cancel();
        timerAtaques.cancel();
        System.out.println("Gracias por jugar. Fin de la partida.");
        sc.close();
    }

    // Auxiliar para comprobar si el array de listas está completamente vacío
    private static boolean isArmyEmpty(ArrayList<MilitaryUnit>[] army) {
        for (ArrayList<MilitaryUnit> list : army) {
            if (!list.isEmpty()) return false;
        }
        return true;
    }

    // CORRECCIÓN 3: Generador adaptado al Array de ArrayLists con el escalado tecnológico del enemigo
    @SuppressWarnings("unchecked")
    private static ArrayList<MilitaryUnit>[] generateEnemyArmy(int level) {
        ArrayList<MilitaryUnit>[] enemy = new ArrayList[9];
        for (int i = 0; i < 9; i++) {
            enemy[i] = new ArrayList<>();
        }
        
        int multiplier = 100 + (level * ENEMY_FLEET_INCREASE);
        int virtualIron = (IRON_BASE_ENEMY_ARMY * multiplier) / 100;
        int virtualWood = (WOOD_BASE_ENEMY_ARMY * multiplier) / 100;
        
        // Reparto proporcional aproximado según los recursos acumulados del bot enemigo
        // Añadimos unidades pasándole el nivel de tecnología del enemigo (igual al nivel actual de batalla)
        while (virtualIron > 2000 && virtualWood > 5000) {
            enemy[0].add(new Swordsman(level, level)); // Espadachines (35%)
            enemy[1].add(new Spearman(level, level)); // Lanceros (25%)
            enemy[2].add(new Crossbow(level, level));  // Ballesteros (20%)
            enemy[3].add(new Cannon(level, level));    // Cañones (20%)
            
            virtualIron -= (IRON_COST_SWORDSMAN + IRON_COST_SPEARMAN + IRON_COST_CROSSBOW + IRON_COST_CANNON);
            virtualWood -= (WOOD_COST_SWORDSMAN + WOOD_COST_SPEARMAN + WOOD_COST_CROSSBOW + WOOD_COST_CANNON);
        }
        return enemy;
    }
}