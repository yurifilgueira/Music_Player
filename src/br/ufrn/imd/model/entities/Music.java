package br.ufrn.imd.model.entities;

import br.ufrn.imd.repositories.exceptions.InvalidMusicFileException;

import java.io.File;

public class Music {
    private String directory;
    private String fileName;
    private File file;

    public Music(String directory, String fileName) {
        if(!fileName.endsWith(".mp3")){
            throw new InvalidMusicFileException();
        }

        this.directory = directory;
        this.fileName = fileName;
        file = new File(directory + "/" + fileName);
    }

    public String getFileName() {
        return fileName;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return fileName;
    }
}
