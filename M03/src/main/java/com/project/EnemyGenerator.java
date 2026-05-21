package com.project;

import Logic.*;
import java.util.ArrayList;
import java.util.Random;

public class EnemyGenerator {

    @SuppressWarnings("unchecked")
    public static ArrayList<MilitaryUnit>[] generateEnemyArmy() {

        ArrayList<MilitaryUnit>[] army = new ArrayList[9];
        for (int i = 0; i < 9; i++) army[i] = new ArrayList<>();

        Random r = new Random();

        int swords = 5 + r.nextInt(10);
        int spears = 5 + r.nextInt(10);
        int crossbows = 3 + r.nextInt(7);
        int cannons = 1 + r.nextInt(3);

        int arrowTowers = 2 + r.nextInt(4);
        int catapults = 1 + r.nextInt(3);
        int rockets = r.nextInt(2);

        int magicians = r.nextInt(3);
        int priests = r.nextInt(3);

        for (int i = 0; i < swords; i++) army[0].add(new Swordsman());
        for (int i = 0; i < spears; i++) army[1].add(new Spearman());
        for (int i = 0; i < crossbows; i++) army[2].add(new Crossbow());
        for (int i = 0; i < cannons; i++) army[3].add(new Cannon());

        for (int i = 0; i < arrowTowers; i++) army[4].add(new ArrowTower());
        for (int i = 0; i < catapults; i++) army[5].add(new Catapult());
        for (int i = 0; i < rockets; i++) army[6].add(new RocketLauncherTower());

        for (int i = 0; i < magicians; i++) army[7].add(new Magician());
        for (int i = 0; i < priests; i++) army[8].add(new Priest());

        return army;
    }
}
