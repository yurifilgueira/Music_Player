package br.ufrn.imd.controller;

import br.ufrn.imd.model.entities.Music;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayerController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private String directory;

    private Music selectedMusic;

    private boolean isPlaying;

    @FXML
    private Button play;

    @FXML
    private ListView<Music> musicsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        directory = "src/resources/songs";

        isPlaying = false;

        Set<String> musicName = Stream.of(new File(directory).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());

        List<Music> musics = new ArrayList<>();

        for (String s : musicName) {
            musics.add(new Music(directory, s));
        }

        musicsList.getItems().addAll(musics);

        selectedMusic = this.musicsList.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void onPlayButton(){
        if(isPlaying){
            isPlaying = false;
            play.setText("Play");
        }else{
            isPlaying = true;
            play.setText("Pause");
        }
    }

}
