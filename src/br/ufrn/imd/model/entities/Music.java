package br.ufrn.imd.model.entities;

import br.ufrn.imd.repositories.exceptions.InvalidMusicFileException;

import java.io.File;

public class Music {
    private File file;
    private String fullDirectory;

    public Music(File file) {
        if(!file.getName().endsWith(".mp3")){
            throw new InvalidMusicFileException();
        }

        this.file = file;
        fullDirectory = file.getAbsolutePath();
    }

    public Music(String fullDirectory) {
        if(!fullDirectory.endsWith(".mp3")){
            throw new InvalidMusicFileException();
        }

        file = new File(fullDirectory);
        this.fullDirectory = fullDirectory;
    }

    public Music(String directory, String fileName) {
        if(!fileName.endsWith(".mp3")){
            throw new InvalidMusicFileException();
        }

        file = new File(directory + "/" + fileName);
        fullDirectory = directory + "/" + fileName;
    }

    public File getFile() {
        return file;
    }

    public String getFullDirectory() {
        return fullDirectory;
    }

    @Override
    public String toString() {
        return file.getName().substring(0, file.getName().length() - 4);
    }
}
