package br.ufrn.imd.model.entities;

import br.ufrn.imd.repositories.exceptions.InvalidMusicFileException;

import java.io.File;

public class Music {
    private File file;
    private Music previous;
    private Music next;

    public Music(String directory, String fileName) {
        if(!fileName.endsWith(".mp3")){
            throw new InvalidMusicFileException();
        }

        file = new File(directory + "/" + fileName);
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
        return file.getName().substring(0, file.getName().length() - 4);
    }
}
