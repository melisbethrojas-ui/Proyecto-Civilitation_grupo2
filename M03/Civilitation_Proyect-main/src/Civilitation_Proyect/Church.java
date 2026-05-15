package Civilitation_Proyect;

/**
 * Clase Church: Edificio religioso de la civilización.
 * Su presencia es obligatoria para poder reclutar unidades de tipo Priest.
 */
public class Church implements Variables {

    private int level;

    public Church() {
        this.level = 1;
    }

    /**
     * Aunque el PDF se centra en que la Iglesia habilita al Priest,
     * podemos añadir una pequeña producción de maná o fe si el diseño lo requiere.
     */
    public int produceMana() {
        // Si decides que la iglesia también aporte maná, usa una constante de Variables
        return 50 * level; 
    }

    public int getLevel() {
        return level;
    }

    public void upgrade() {
        this.level++;
    }
}