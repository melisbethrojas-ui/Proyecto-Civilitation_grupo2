package Civilitation_Proyect;

/**
 * Excepción personalizada para el proyecto Civilizations.
 * Se lanza cuando los recursos de la civilización son insuficientes
 * para realizar una acción (construir o reclutar).
 */
public class ResourceException extends Exception {

    /**
     * Constructor que recibe el mensaje de error específico.
     * @param message Descripción de qué recurso falta.
     */
    public ResourceException(String message) {
        super(message);
    }
}