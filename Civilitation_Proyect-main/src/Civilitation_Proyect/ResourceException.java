package Civilitation_Proyect;

public class ResourceException extends Exception {
    
    // Constructor básico sin mensaje
    public ResourceException() {
        super("No hay suficientes recursos disponibles.");
    }

    // Constructor que permite pasarle un mensaje personalizado (ej: qué recurso falta)
    public ResourceException(String message) {
        super(message);
    }
}