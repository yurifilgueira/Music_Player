package br.ufrn.imd.model.entities;

import br.ufrn.imd.repositories.exceptions.InvalidMusicFileException;

import java.io.File;

public class Music {
    private String fileName;
    private File file;
    private Music previous;
    private Music next;

    public Music(String directory, String fileName) {
        if(!fileName.endsWith(".mp3")){
            throw new InvalidMusicFileException();
        }

        this.fileName = fileName;
        file = new File(directory + "/" + fileName);
    }

    public String getFileName() {
        return fileName;
    }

    public File getFile() {
        return file;
    }

    public Music getPrevious() {
        return previous;
    }

    public void setPrevious(Music previous) {
        this.previous = previous;
    }

    public Music getNext() {
        return next;
    }

    public void setNext(Music next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return fileName.substring(0, fileName.length() - 4);
    }
}
