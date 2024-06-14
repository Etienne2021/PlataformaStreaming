package Excepciones;

public class UsuarioYaexisteException extends Exception{

    public UsuarioYaexisteException(String message) {
        super(message);
    }
}
