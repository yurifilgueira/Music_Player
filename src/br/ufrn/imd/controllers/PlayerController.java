package br.ufrn.imd.controllers;

import br.ufrn.imd.model.entities.Music;
import br.ufrn.imd.services.PlayerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javazoom.jl.decoder.JavaLayerException;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class PlayerController extends MainController {
    private String directory;
    private PlayerService playerService;
    private boolean isPlaying;
    private boolean loop;

    @FXML
    private Button play;
    @FXML
    private Button pause;

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

    protected Button getPlay() {
        return play;
    }

    protected void setPlay(Button play) {
        this.play = play;
    }

    protected Button getPause() {
        return pause;
    }

    protected void setPause(Button pause) {
        this.pause = pause;
    }

    public abstract Music getSelectedMusic();

    public abstract ListView<Music> getMusicsList();

    public abstract void getMusicsFromDirectory();

    public abstract void setPlayerService();

    public abstract void refreshPlayingNow();

    public abstract void refreshSelectedMusic(int index);

    public abstract void selectMusic() throws FileNotFoundException, JavaLayerException;

    public abstract void onChooseDirectoryButton();

    public abstract void onDefaultDirectoryButton();

    public abstract void onPreviousButton() throws FileNotFoundException, JavaLayerException;

    public abstract void onPlayButton() throws JavaLayerException;

    public abstract void onNextButton() throws FileNotFoundException, JavaLayerException;

    public abstract void onLogoutButton(ActionEvent event) throws IOException;
}
