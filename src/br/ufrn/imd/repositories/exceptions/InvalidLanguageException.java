package br.ufrn.imd.repositories.exceptions;

import java.io.Serial;

public class InvalidLanguageException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidLanguageException(){
        super("The selected language is not valid. Note that the only valid languages are english, french, portuguese and japanese.");
    }

    public InvalidLanguageException(String language){
        super("The language " + language + " is not valid. Note that the only valid languages are english, french, portuguese and japanese.");
    }
}
