package Civilitation_Proyect;

public class BuildingException extends Exception {

    // Constructor básico sin mensaje
    public BuildingException() {
        super("No se puede reclutar esta unidad porque falta el edificio requerido.");
    }

    // Constructor con mensaje personalizado
    public BuildingException(String message) {
        super(message);
    }
}