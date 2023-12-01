package br.ufrn.imd.model.entities;

import br.ufrn.imd.repositories.exceptions.InvalidMusicFileException;

import java.io.File;

public class Music {
    private File file;

    public Music(File file) {
        this.file = file;
    }

    public Music(String directory, String fileName) {
        if(!fileName.endsWith(".mp3")){
            throw new InvalidMusicFileException();
        }

        file = new File(directory + "/" + fileName);
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return file.getName().substring(0, file.getName().length() - 4);
    }
}
