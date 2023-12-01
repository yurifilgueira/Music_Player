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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.JFileChooser;
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
    private Music selectedMusic;

    @FXML
    private ListView<Music> musicListView;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label playingNowText;
    @FXML
    private Label musicNamePlayingNow;
    @FXML
    private Label timer;

    @Override
    public Music getSelectedMusic() {
        return selectedMusic;
    }

    @Override
    public ListView<Music> getMusicsList() {
        return musicListView;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.setPlaying(false);

        super.setDirectory("src/resources/songs");

        getMusicsFromDirectory();

        setPlayerService();
    }

    @Override
    public void getMusicsFromDirectory(){
        List<Music> musics = new ArrayList<>();

        Stream.of(Objects.requireNonNull(new File(super.getDirectory()).listFiles()))
                .filter(file -> !file.isDirectory() && file.getName().endsWith(".mp3"))
                .forEach(file -> musics.add(new Music(super.getDirectory(), file.getName())));

        musicListView.getItems().clear();

        musicListView.getItems().addAll(musics);
    }

    @Override
    public void setPlayerService(){
        super.setPlayerService(PlayerService.getInstance());

        super.getPlayerService().setCurrentPlayerController(this);

        super.getPlayerService().setProgressBar(progressBar);

        super.getPlayerService().setTimer(timer);
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
        selectedMusic = musicListView.getItems().get(index);

        musicListView.getSelectionModel().select(index);

        refreshPlayingNow();
    }

    @FXML
    @Override
    public void selectMusic() throws FileNotFoundException, JavaLayerException {
        selectedMusic = musicListView.getSelectionModel().getSelectedItem();

        super.getPlayerService().selectMusicForPlayer(selectedMusic);

        playingNowText.setVisible(true);

        refreshPlayingNow();
    }

    @FXML
    @Override
    public void onChooseDirectoryButton(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showOpenDialog(null);

        super.setDirectory(fileChooser.getSelectedFile().getAbsolutePath());

        getMusicsFromDirectory();
    }

    @FXML
    @Override
    public void onDefaultDirectoryButton(){
        super.setDirectory(System.getProperty("user.dir") + "/src/resources/songs");

        getMusicsFromDirectory();
    }

    @FXML
    @Override
    public void onLogoutButton(ActionEvent event) throws IOException {
        super.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/LoginView.fxml"))));
        super.setStage((Stage) ((Node)event.getSource()).getScene().getWindow());
        super.setScene(new Scene(super.getRoot()));

        WindowService.moveWindow(super.getStage(), super.getRoot());

        super.getStage().setScene(super.getScene());
        super.getStage().centerOnScreen();
        super.getStage().show();

        super.getPlayerService().pause();
    }

    @FXML
    @Override
    public void onPreviousButton() throws FileNotFoundException, JavaLayerException {
        if(musicListView.getSelectionModel().getSelectedIndex() - 1 >= 0){
            if(selectedMusic != null && musicListView.getItems().get(musicListView.getSelectionModel().getSelectedIndex() - 1) != null){
                refreshSelectedMusic(musicListView.getSelectionModel().getSelectedIndex() - 1);

                super.getPlayerService().selectMusicForPlayer(selectedMusic);
            }
        }
    }

    @FXML
    @Override
    public void onPlayButton() {
        if(selectedMusic != null){
            if(super.isPlaying()){
                super.setPlaying(false);

                super.getPlay().setVisible(true);
                super.getPause().setVisible(false);

                super.getPlayerService().pause();
            }else{
                super.setPlaying(true);

                super.getPlay().setVisible(false);
                super.getPause().setVisible(true);

                super.getPlayerService().play();
            }
        }
    }

    @FXML
    @Override
    public void onNextButton() throws FileNotFoundException, JavaLayerException {
        if(musicListView.getSelectionModel().getSelectedIndex() + 1 < musicListView.getItems().size()){
            if(selectedMusic != null && musicListView.getItems().get(musicListView.getSelectionModel().getSelectedIndex() + 1) != null){
                refreshSelectedMusic(musicListView.getSelectionModel().getSelectedIndex() + 1);

                super.getPlayerService().selectMusicForPlayer(selectedMusic);
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
