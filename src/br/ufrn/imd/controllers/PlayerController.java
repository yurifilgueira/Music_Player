package br.ufrn.imd.controllers;

import br.ufrn.imd.model.entities.Music;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javazoom.jl.decoder.JavaLayerException;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class PlayerController extends Controller {
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
