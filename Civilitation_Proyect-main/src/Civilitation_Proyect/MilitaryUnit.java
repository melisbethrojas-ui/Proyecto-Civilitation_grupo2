package Civilitation_Proyect;

public interface MilitaryUnit {

    // Métodos para el combate
    int attack();
    void takeDamage(int receivedDamage);
    int getActualArmor();
    void resetArmor();

    // Costes de recursos para crear la unidad
    int getFoodCost();
    int getWoodCost();
    int getIronCost();
    int getManaCost();

    // Probabilidades de combate y escombros
    int getChanceGeneratinWaste(); // Mantiene la errata del enunciado
    int getChanceAttackAgain();

    // Gestión de la experiencia de la unidad
    void setExperience(int n);
    int getExperience();
}