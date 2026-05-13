package src.Buildings;

import src.Excepciones.BuildingException;
import src.Interfaces.Variables;

public class Smithy implements Variables {
    private int level;

    public Smithy() {
        this.level = 0;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() throws BuildingException {
        // Podrías añadir aquí una restricción de nivel máximo si quieres
        this.level++;
    }

    // Métodos para obtener el coste de la próxima mejora
    public int getFoodCost() {
        return FOOD_COST_SMITHY + (level * NEXT_LEVEL_FOOD_COST_SMITHY);
    }

    public int getWoodCost() {
        return WOOD_COST_SMITHY + (level * NEXT_LEVEL_WOOD_COST_SMITHY);
    }

    public int getIronCost() {
        return IRON_COST_SMITHY + (level * NEXT_LEVEL_IRON_COST_SMITHY);
    }
}