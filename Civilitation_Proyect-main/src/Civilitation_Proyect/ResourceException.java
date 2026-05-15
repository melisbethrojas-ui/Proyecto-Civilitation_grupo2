package Civilitation_Proyect;

public class ResourceException extends Exception {

    /**
     * Constructor que recibe el mensaje de error específico.
     * @param message Descripción de qué recurso falta.
     */
    public ResourceException(String message) {
        super(message);
    }
}