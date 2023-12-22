package br.ufrn.imd.controllers;

import br.ufrn.imd.model.entities.Music;
import br.ufrn.imd.services.PlayerService;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class PlayerController extends MainController {
    private String directory;
    private PlayerService playerService;
    private boolean isPlaying;
    private boolean loop;

    protected String getDirectory() {
        return directory;
    }

    protected void setDirectory(String directory) {
        this.directory = directory;
    }

    protected PlayerService getPlayerService() {
        return playerService;
    }

    protected void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    protected boolean isPlaying() {
        return isPlaying;
    }

    protected void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    protected boolean isLoop() {
        return loop;
    }

    protected void setLoop(boolean loop) {
        this.loop = loop;
    }

    public abstract Music getSelectedMusic();

    public abstract ListView<Music> getMusicsList();

    public abstract void getMusicsFromDirectory();

    public abstract void setPlayerService();

    public abstract void refreshPlayingNow();

    public abstract void refreshSelectedMusic(int index);

    public abstract void selectMusic() throws FileNotFoundException;

    public abstract void onChooseDirectoryButton();

    public abstract void onDefaultDirectoryButton();

    public abstract void onPreviousButton() throws FileNotFoundException;

    public abstract void play() throws FileNotFoundException;

    public abstract void pause();

    public abstract void onPlayButton() throws FileNotFoundException;

    public abstract void onNextButton() throws FileNotFoundException;

    public abstract void onLogoutButton(ActionEvent event) throws IOException;
}
