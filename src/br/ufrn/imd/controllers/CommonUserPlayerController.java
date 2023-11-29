package br.ufrn.imd.controllers;

import br.ufrn.imd.model.entities.Music;
import br.ufrn.imd.services.PlayerService;
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

import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
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

        IntStream.range(1, musics.size()).forEach(i -> musics.get(i).setPrevious(musics.get(i - 1)));

        IntStream.range(0, musics.size() - 1).forEach(i -> musics.get(i).setNext(musics.get(i + 1)));

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

    @FXML @Override
    public void selectMusic() throws FileNotFoundException, JavaLayerException {
        selectedMusic = this.musicsList.getSelectionModel().getSelectedItem();

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

        AtomicReference<Double> x = new AtomicReference<>((double) 0);
        AtomicReference<Double> y = new AtomicReference<>((double) 0);

        super.getRoot().setOnMousePressed( mouseEvent -> {
            x.set(mouseEvent.getSceneX());
            y.set(mouseEvent.getSceneY());
        });

        super.getRoot().setOnMouseDragged( mouseEvent -> {
            if(y.get() < 14){
                super.getStage().setX(mouseEvent.getScreenX() - x.get());
                super.getStage().setY(mouseEvent.getScreenY() - y.get());
            }
        });

        super.getStage().setScene(super.getScene());
        super.getStage().centerOnScreen();
        super.getStage().show();

        playerService.pause();
    }

    @FXML @Override
    public void onPreviousButton() throws FileNotFoundException, JavaLayerException {
        if(selectedMusic != null && selectedMusic.getPrevious() != null){
            selectedMusic = selectedMusic.getPrevious();

            playerService.selectMusicForPlayer(selectedMusic);
        }

        refreshPlayingNow();
    }

    @FXML @Override
    public void onPlayButton() throws JavaLayerException {
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
        if(selectedMusic != null && selectedMusic.getNext() != null){
            selectedMusic = selectedMusic.getNext();

            playerService.selectMusicForPlayer(selectedMusic);
        }
        
        refreshPlayingNow();
    }

    @Override
    public void autoNext(){
        selectedMusic = selectedMusic.getNext();

        refreshPlayingNow();
    }

    @FXML
    public void onCloseButton(ActionEvent event){
        super.setStage((Stage) ((Button) event.getSource()).getScene().getWindow());
        super.getStage().close();
    }

    @FXML
    public void onMinimizeButton(ActionEvent event){
        super.setStage((Stage) ((Button) event.getSource()).getScene().getWindow());
        super.getStage().setIconified(true);
    }
}
