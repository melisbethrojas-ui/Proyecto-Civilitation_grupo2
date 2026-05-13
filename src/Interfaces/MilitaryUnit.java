package src.Interfaces;

public interface MilitaryUnit {
    
    // devuelve el poder de ataque de la unidad
    int attack();
    
    // resta del blindaje el daño recibido por parámetro
    void takeDamage(int receivedDamage);
    
    // devuelve el blindaje actual después de recibir daño
    int getActualArmor();
    
    // restablece el blindaje a su valor original
    void resetArmor();
    
    // devuelve el coste de comida de la unidad
    int getFoodCost();
    
    // devuelve el coste de madera de la unidad
    int getWoodCost();
    
    // devuelve el coste de oro de la unidad
    int getGoldCost();
    
    // devuelve el coste de hierro de la unidad
    int getIronCost();
    
    // devuelve el coste de maná de la unidad
    int getManaCost();

    // probabilidad de generar residuos al ser eliminada (blindaje ≤ 0)
    int getChanceGeneratingWaste();
    
    // probabilidad de volver a atacar
    int getChanceAttackAgain();

    // establece la experiencia a un nuevo valor
    void setExperience(int n);
    
    // suma la cantidad indicada de experiencia
    void addExperience(int amount);
    
    // devuelve la experiencia actual de la unidad
    int getExperience();
    
    boolean isAlive();

}
