package br.ufrn.imd.controller;

import br.ufrn.imd.model.entities.Music;
import br.ufrn.imd.services.PlayerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CommonUserPlayerController extends Thread implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private PlayerService playerService;

    private String directory;

    private Music selectedMusic;

    private boolean isPlaying;

    private boolean loop;

    @FXML
    private Button play;

    @FXML
    private ListView<Music> musicsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        directory = "src/resources/songs";

        getMusicsFromDirectory();
    }

    public void getMusicsFromDirectory(){
        isPlaying = false;

        List<String> musicName = Stream.of(new File(directory).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .toList();

        List<Music> musics = musicName.stream().filter(s -> s.endsWith(".mp3")).map(s -> new Music(directory, s)).toList();

        IntStream.range(1, musics.size()).forEach(i -> musics.get(i).setPrevious(musics.get(i - 1)));

        IntStream.range(0, musics.size() - 1).forEach(i -> musics.get(i).setNext(musics.get(i + 1)));

        musicsList.getItems().clear();

        musicsList.getItems().addAll(musics);

        playerService = PlayerService.getInstance();
    }

    @FXML
    public void selectMusic() throws FileNotFoundException, JavaLayerException {
        selectedMusic = this.musicsList.getSelectionModel().getSelectedItem();

        playerService.stop();

        playerService.selectMusicForPlayer(selectedMusic);
    }

    @FXML
    public void onChooseDirectoryButton(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.showOpenDialog(null);

        directory = fileChooser.getCurrentDirectory().getPath();

        getMusicsFromDirectory();
    }

    @FXML
    public void onDefaultDirectoryButton(){
        directory = System.getProperty("user.dir") + "/src/resources/songs";

        getMusicsFromDirectory();
    }

    @FXML
    public void onLogoutButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/LoginView.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        playerService.stop();
    }

    @FXML
    public void onPreviousButton() throws FileNotFoundException, JavaLayerException {
        if(selectedMusic != null && selectedMusic.getPrevious() != null){
            selectedMusic = selectedMusic.getPrevious();

            playerService.selectMusicForPlayer(selectedMusic);
        }
    }

    @FXML
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

    @FXML
    public void onNextButton() throws FileNotFoundException, JavaLayerException {
        if(selectedMusic != null && selectedMusic.getNext() != null){
            selectedMusic = selectedMusic.getNext();

            playerService.selectMusicForPlayer(selectedMusic);
        }
    }
}
