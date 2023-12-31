package br.ufrn.imd.services;

import br.ufrn.imd.repositories.PlaylistDAO;
import br.ufrn.imd.repositories.UserDAO;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

public class WindowService {
    public static void closeWindow(ActionEvent event, Stage stage){
        stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
        stage.close();

        MusicService.writeSavedMusics();
        UserDAO userDAO = UserDAO.getInstance();
        PlaylistDAO playlistDAO = PlaylistDAO.getInstance();
        userDAO.saveUsers();
        playlistDAO.savePlaylists();

        PlayerService playerService = PlayerService.getInstance();
        if(playerService.getMediaPlayer() != null && playerService.isRunning()){
            playerService.stop();
        }
    }

    public static void minimizeWindow(ActionEvent event, Stage stage){
        stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
        stage.setIconified(true);
    }

    public static void moveWindow(Stage stage, Parent root){
        AtomicReference<Double> x = new AtomicReference<>((double) 0);
        AtomicReference<Double> y = new AtomicReference<>((double) 0);

        root.setOnMousePressed( mouseEvent -> {
            x.set(mouseEvent.getSceneX());
            y.set(mouseEvent.getSceneY());
        });

        root.setOnMouseDragged( mouseEvent -> {
            if(y.get() < 27){
                stage.setX(mouseEvent.getScreenX() - x.get());
                stage.setY(mouseEvent.getScreenY() - y.get());
            }
        });
    }
}
