package br.ufrn.imd.controllers;

import br.ufrn.imd.model.entities.Music;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javazoom.jl.decoder.JavaLayerException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class VipUserPlayerController extends PlayerController {
    @Override
    public Music getSelectedMusic() {
        return null;
    }

    @Override
    public ListView<Music> getMusicsList() {
        return null;
    }

    @Override
    public void getMusicsFromDirectory() {

    }

    @Override
    public void setPlayerService() {

    }

    @Override
    public void refreshPlayingNow() {

    }

    @Override
    public void refreshSelectedMusic(int index) {

    }

    @Override
    public void selectMusic() throws FileNotFoundException, JavaLayerException {

    }

    @Override
    public void onChooseDirectoryButton() {

    }

    @Override
    public void onDefaultDirectoryButton() {

    }

    @Override
    public void onPreviousButton() throws FileNotFoundException, JavaLayerException {

    }

    @Override
    public void onPlayButton() throws JavaLayerException {

    }

    @Override
    public void onNextButton() {

    }

    @Override
    public void onCloseButton(ActionEvent event) {

    }

    @Override
    public void onMinimizeButton(ActionEvent event) {

    }

    @Override
    public void onLogoutButton(ActionEvent event) throws IOException {

    }
}
