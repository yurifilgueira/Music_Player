package br.ufrn.imd.controllers;

import br.ufrn.imd.model.entities.Music;
import br.ufrn.imd.repositories.exceptions.InvalidLanguageException;
import br.ufrn.imd.repositories.exceptions.InvalidThemeException;
import br.ufrn.imd.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
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
    private Stage stageConfig;

    @FXML
    private AnchorPane background;
    @FXML
    private Button configLight, configDark;
    @FXML
    private Button buttonSelectDirectory;
    @FXML
    private Button buttonDefaultDirectory;
    @FXML
    private Label txt11, txt12, txt21, txt22;
    @FXML
    private Button playDark, playLight;
    @FXML
    private Button pauseDark, pauseLight;
    @FXML
    private Button nextDark, nextLight;
    @FXML
    private Button previousDark, previousLight;

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
                .forEach(file -> musics.add(MusicService.saveMusic(new Music(super.getDirectory(), file.getName()))));

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

        if(selectedMusic != null) {

            super.getPlayerService().selectMusicForPlayer(selectedMusic);

            playingNowText.setVisible(true);

            refreshPlayingNow();
        }
    }

    @FXML
    @Override
    public void onChooseDirectoryButton(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showOpenDialog(null);



        if(fileChooser.getSelectedFile() != null){

            super.setDirectory(fileChooser.getSelectedFile().getAbsolutePath());
            getMusicsFromDirectory();
        }
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

                switch (ThemeService.getTheme()){
                    case DARK:
                        pauseDark.setVisible(false);
                        pauseLight.setVisible(false);
                        playLight.setVisible(true);
                        playDark.setVisible(false);
                        break;
                    case LIGHT:
                        pauseDark.setVisible(false);
                        pauseLight.setVisible(false);
                        playDark.setVisible(true);
                        playLight.setVisible(false);
                        break;
                    default:
                        throw new InvalidThemeException(ThemeService.getTheme().toString());
                }

                super.getPlayerService().pause();
            }else{
                super.setPlaying(true);

                switch (ThemeService.getTheme()){
                    case DARK:
                        playLight.setVisible(false);
                        playDark.setVisible(false);
                        pauseLight.setVisible(true);
                        pauseDark.setVisible(false);
                        break;
                    case LIGHT:
                        playDark.setVisible(false);
                        playLight.setVisible(false);
                        pauseDark.setVisible(true);
                        pauseLight.setVisible(false);
                        break;
                    default:
                        throw new InvalidThemeException(ThemeService.getTheme().toString());
                }

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

    public void editMusicListStyle(String backgroundColor, String textColor){
        musicListView.setCellFactory(new Callback<ListView<Music>, ListCell<Music>>() {
            @Override
            public ListCell<Music> call(ListView<Music> musicListView) {
                return new ListCell<Music>() {
                    @Override
                    protected void updateItem(Music item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(item.toString());
                            setStyle("-fx-background-color: " + backgroundColor + "; -fx-text-fill: " + textColor);
                        }
                    }
                };
            }
        });
    }

    public void changeLanguage(){
        switch (LanguageService.getLanguage()){
            case "English":
                txt11.setText("Select"); txt12.setText("a directory");
                txt21.setText("Default"); txt22.setText("directory");
                break;
            case "Français":
                txt11.setText("Sélectionner"); txt12.setText("un répertoire");
                txt21.setText("Répertoire"); txt22.setText("par défaut");
                break;
            case "Português":
                txt11.setText("Selecionar"); txt12.setText("um diretório");
                txt21.setText("Diretório"); txt22.setText("padrão");
                break;
            case "日本語":
                txt11.setText(""); txt12.setText("");
                txt21.setText(""); txt22.setText("");
                break;
            default:
                throw new InvalidLanguageException(LanguageService.getLanguage());
        }
    }

    public void changeTheme(){
        switch (ThemeService.getTheme()){
            case DARK:
                background.setStyle("-fx-background-color: black; -fx-border-color: white; -fx-border-width: 1;");

                configDark.setVisible(false);
                configLight.setVisible(true);

                buttonSelectDirectory.setStyle("-fx-background-radius: 25; -fx-background-color: white");
                buttonDefaultDirectory.setStyle("-fx-background-radius: 25; -fx-background-color: white");

                txt11.setStyle("-fx-text-fill: black"); txt12.setStyle("-fx-text-fill: black");
                txt21.setStyle("-fx-text-fill: black"); txt22.setStyle("-fx-text-fill: black");

                progressBar.setStyle("-fx-control-inner-background: #111111; -fx-border-color: black; -fx-accent: lime");

                previousDark.setVisible(false);
                previousLight.setVisible(true);

                if(isPlaying()){
                    pauseDark.setVisible(false);
                    pauseLight.setVisible(true);
                }else{
                    playDark.setVisible(false);
                    playLight.setVisible(true);
                }

                nextDark.setVisible(false);
                nextLight.setVisible(true);

                playingNowText.setStyle("-fx-text-fill: white");
                musicNamePlayingNow.setStyle("-fx-text-fill: white");
                timer.setStyle("-fx-text-fill: white");

                musicListView.setStyle("-fx-background-color: white");

                editMusicListStyle("white", "black");
                break;
            case LIGHT:
                background.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2;");

                configLight.setVisible(false);
                configDark.setVisible(true);

                buttonSelectDirectory.setStyle("-fx-background-radius: 25; -fx-background-color: black;");
                buttonDefaultDirectory.setStyle("-fx-background-radius: 25; -fx-background-color: black;");

                txt11.setStyle("-fx-text-fill: white"); txt12.setStyle("-fx-text-fill: white");
                txt21.setStyle("-fx-text-fill: white"); txt22.setStyle("-fx-text-fill: white");

                progressBar.setStyle("-fx-control-inner-background: #EEEEEE; -fx-border-color: white; -fx-accent: black");

                previousLight.setVisible(false);
                previousDark.setVisible(true);

                if(isPlaying()){
                    pauseLight.setVisible(false);
                    pauseDark.setVisible(true);
                }else{
                    playLight.setVisible(false);
                    playDark.setVisible(true);
                }

                nextLight.setVisible(false);
                nextDark.setVisible(true);

                playingNowText.setStyle("-fx-text-fill: black");
                musicNamePlayingNow.setStyle("-fx-text-fill: black");
                timer.setStyle("-fx-text-fill: black");

                musicListView.setStyle("-fx-background-color: #DDDDDD");

                editMusicListStyle("#EEEEEE", "black");
                break;
            default:
                throw new InvalidThemeException(ThemeService.getTheme().toString());
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

    @FXML
    public void onConfigButton() throws IOException {
        if(stageConfig == null || !stageConfig.isShowing()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ConfigView.fxml"));
            Parent root = loader.load();

            stageConfig = new Stage();

            stageConfig.initStyle(StageStyle.UNDECORATED);

            WindowService.moveWindow(stageConfig, root);

            stageConfig.setScene(new Scene(root));
            stageConfig.setResizable(false);
            stageConfig.setAlwaysOnTop(true);
            stageConfig.showAndWait();

            changeLanguage();
            changeTheme();
        }
    }
}
