package pe.jsaire.tiendaapp.utils.exceptions;

public class ArticuloNotFoundException extends RuntimeException {
    public ArticuloNotFoundException(String message) {
        super(message);
    }
}
