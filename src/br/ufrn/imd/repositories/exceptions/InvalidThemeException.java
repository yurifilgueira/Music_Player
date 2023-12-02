package br.ufrn.imd.repositories.exceptions;

import java.io.Serial;

public class InvalidThemeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidThemeException(String theme){
        super("The theme " + theme + " is not valid. Note that the only valid themes are dark and light.");
    }
}
