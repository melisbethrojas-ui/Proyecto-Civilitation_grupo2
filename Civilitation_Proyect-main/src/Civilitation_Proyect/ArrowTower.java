package Civilitation_Proyect;

// Clase que representa la torre de arqueros, una unidad defensiva que ataca a distancia
public class ArrowTower extends DefenseUnit {
    // Constructor para el jugador 
    public ArrowTower(int technologyArmorLevel, int technologyAttackLevel) {
        super(ARMOR_ARROWTOWER, BASE_DAMAGE_ARROWTOWER);
        this.armor += (ARMOR_ARROWTOWER * technologyArmorLevel * PLUS_ARMOR_ARROWTOWER_BY_TECHNOLOGY) / 100;
        this.initialArmor = this.armor;
        this.baseDamage += (BASE_DAMAGE_ARROWTOWER * technologyAttackLevel * PLUS_ATTACK_ARROWTOWER_BY_TECHNOLOGY) / 100;
    }

    // Constructor para el enemigo 
    public ArrowTower() {
        super(ARMOR_ARROWTOWER, BASE_DAMAGE_ARROWTOWER);
    }

    //METODO DE ATAQUE
    @Override
    public int attack() {
        int totalDamage = this.baseDamage;
        // Incremento por experiencia (4% por punto)
        totalDamage += (this.baseDamage * this.experience * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT) / 100;
        // Incremento por santificación (7%)
        if (this.isSanctified()) {
            totalDamage += (this.baseDamage * PLUS_ATTACK_UNIT_SANCTIFIED) / 100;
        }
        return totalDamage;
    }

    // GETTERS DE LOS COSTOS Y PROBABILIDADES
    @Override
    public int getFoodCost() { 
        return FOOD_COST_ARROWTOWER; }
    @Override
    public int getWoodCost() { 
        return WOOD_COST_ARROWTOWER; }
    @Override
    public int getIronCost() { 
        return IRON_COST_ARROWTOWER; }
    @Override
    public int getManaCost() { 
        return MANA_COST_ARROWTOWER; }
    @Override
    public int getChanceGeneratinWaste() { 
        return CHANCE_GENERATING_WASTE_ARROWTOWER; }
    @Override
    public int getChanceAttackAgain() { 
        return CHANCE_ATTACK_AGAIN_ARROWTOWER; }
}