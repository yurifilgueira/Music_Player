package br.ufrn.imd.controllers;

import br.ufrn.imd.model.entities.Music;
import br.ufrn.imd.services.PlayerService;
import br.ufrn.imd.services.WindowService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class CommonUserPlayerController extends PlayerController implements Initializable {
    private PlayerService playerService;

    private String directory;

    private Music selectedMusic;

    private boolean isPlaying;

    private boolean loop;

    @FXML
    private Button play;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label playingNowText;

    @FXML
    private Label musicNamePlayingNow;

    @FXML
    private Label timer;

    @FXML
    private ListView<Music> musicsList;

    @Override
    public Music getSelectedMusic() {
        return selectedMusic;
    }

    @Override
    public ListView<Music> getMusicsList() {
        return musicsList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isPlaying = false;

        directory = "src/resources/songs";

        getMusicsFromDirectory();

        setPlayerService();
    }

    @Override
    public void getMusicsFromDirectory(){
        List<Music> musics = new ArrayList<>();

        Stream.of(Objects.requireNonNull(new File(directory).listFiles()))
                .filter(file -> !file.isDirectory() && file.getName().endsWith(".mp3"))
                .forEach(file -> musics.add(new Music(directory, file.getName())));

        musicsList.getItems().clear();

        musicsList.getItems().addAll(musics);
    }

    @Override
    public void setPlayerService(){
        playerService = PlayerService.getInstance();

        playerService.setCurrentPlayerController(this);

        playerService.setProgressBar(progressBar);

        playerService.setTimer(timer);
    }

    @Override
    public void refreshPlayingNow(){
        if(String.valueOf(selectedMusic).length() > 40){
            musicNamePlayingNow.setFont(new Font("System", (double) 820 / String.valueOf(selectedMusic).length()));
        }else{
            musicNamePlayingNow.setFont(new Font("System", 20));
        }

        musicNamePlayingNow.setText(String.valueOf(selectedMusic));
    }

    @Override
    public void refreshSelectedMusic(int index){
        selectedMusic = musicsList.getItems().get(index);

        musicsList.getSelectionModel().select(index);

        refreshPlayingNow();
    }

    @FXML @Override
    public void selectMusic() throws FileNotFoundException, JavaLayerException {
        selectedMusic = musicsList.getSelectionModel().getSelectedItem();

        playerService.selectMusicForPlayer(selectedMusic);

        playingNowText.setVisible(true);

        refreshPlayingNow();
    }

    @FXML @Override
    public void onChooseDirectoryButton(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.showOpenDialog(null);

        directory = fileChooser.getCurrentDirectory().getPath();

        getMusicsFromDirectory();
    }

    @FXML @Override
    public void onDefaultDirectoryButton(){
        directory = System.getProperty("user.dir") + "/src/resources/songs";

        getMusicsFromDirectory();
    }

    @FXML @Override
    public void onLogoutButton(ActionEvent event) throws IOException {
        super.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/LoginView.fxml"))));
        super.setStage((Stage) ((Node)event.getSource()).getScene().getWindow());
        super.setScene(new Scene(super.getRoot()));

        WindowService.moveWindow(super.getStage(), super.getRoot());

        super.getStage().setScene(super.getScene());
        super.getStage().centerOnScreen();
        super.getStage().show();

        playerService.pause();
    }

    @FXML @Override
    public void onPreviousButton() throws FileNotFoundException, JavaLayerException {
        if(musicsList.getSelectionModel().getSelectedIndex() - 1 >= 0){
            if(selectedMusic != null && musicsList.getItems().get(musicsList.getSelectionModel().getSelectedIndex() - 1) != null){
                refreshSelectedMusic(musicsList.getSelectionModel().getSelectedIndex() - 1);

                playerService.selectMusicForPlayer(selectedMusic);
            }
        }
    }

    @FXML @Override
    public void onPlayButton() {
        if(selectedMusic != null){
            if(isPlaying){
                isPlaying = false;

                play.setText("Play");

                playerService.pause();
            }else{
                isPlaying = true;

                play.setText("Pause");

                playerService.play();
            }
        }
    }

    @FXML @Override
    public void onNextButton() throws FileNotFoundException, JavaLayerException {
        if(musicsList.getSelectionModel().getSelectedIndex() + 1 < musicsList.getItems().size()){
            if(selectedMusic != null && musicsList.getItems().get(musicsList.getSelectionModel().getSelectedIndex() + 1) != null){
                refreshSelectedMusic(musicsList.getSelectionModel().getSelectedIndex() + 1);

                playerService.selectMusicForPlayer(selectedMusic);
            }
        }
    }

    @FXML
    public void onCloseButton(ActionEvent event){
        WindowService.closeWindow(event, super.getStage());
    }

    @FXML
    public void onMinimizeButton(ActionEvent event){
        WindowService.minimizeWindow(event, super.getStage());
    }
}
