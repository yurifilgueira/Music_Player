package br.ufrn.imd.repositories.exceptions;

import java.io.Serial;

public class InvalidMusicFileException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidMusicFileException(){
        super("The selected file doesn't have the valid format for a music file. Note that .mp3 is the only valid format.");
    }
}
