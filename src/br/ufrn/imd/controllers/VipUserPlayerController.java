package br.ufrn.imd.controllers;

import br.ufrn.imd.model.entities.Music;
import br.ufrn.imd.model.entities.Playlist;
import br.ufrn.imd.model.entities.VipUser;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

public class VipUserPlayerController extends PlayerController implements Initializable {
    private PlaylistService playlistService = new PlaylistService();
    private boolean defaultDirectory;
    private LoginService loginService = LoginService.getInstance();
    private Stage stageConfig;
    private Music selectedMusic;
    private Playlist selectedPlaylist;
    private Stage stagePlaylist;

    @FXML
    private AnchorPane background;
    @FXML
    private Button configDark;
    @FXML
    private Button configLight;
    @FXML
    private Button buttonSelectDirectory;
    @FXML
    private Button buttonDefaultDirectory;
    @FXML
    private Button buttonCreatePlaylist;
    @FXML
    private Button buttonAddMusic;
    @FXML
    private Button playDark, playLight, pauseDark, pauseLight, nextLight, nextDark, previousLight, previousDark;
    @FXML
    private Label txt11, txt12, txt21, txt22, txt31, txt32, txt41, txt42;
    @FXML
    private ListView<Music> musicListView;
    @FXML
    private ListView<Playlist> playlistListView;
    @FXML
    private Label labelDirectory;
    @FXML
    private Label labelPlaylist;
    @FXML
    private Label musicNamePlayingNow;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label playingNowText;
    @FXML
    private Label timer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        super.setPlaying(false);

        defaultDirectory = true;

        super.setDirectory("src/resources/songs");

        getMusicsFromDirectory();

        reloadPlaylists();

        setPlayerService();

        changeLanguage();

        changeTheme();
    }

    @Override
    public Music getSelectedMusic() {
        return selectedMusic;
    }

    @Override
    public ListView<Music> getMusicsList() {
        return musicListView;
    }

    @FXML
    @Override
    public void getMusicsFromDirectory(){
        Set<Music> musics = new HashSet<>();

        if (defaultDirectory){
            for (String directory : loginService.getLoggedUser().getDirectories()) {
                musics.add(new Music(directory));
            }
        }

        if (selectedPlaylist == null) {
            Stream.of(Objects.requireNonNull(new File(super.getDirectory()).listFiles()))
                    .filter(file -> !file.isDirectory() && file.getName().endsWith(".mp3"))
                    .forEach(file -> musics.add(MusicService.saveMusic(MusicService.saveMusic(new Music(super.getDirectory(), file.getName())))));
        }
        else {
            Stream.of(Objects.requireNonNull(new File(super.getDirectory()).listFiles()))
                    .filter(file -> !file.isDirectory() && file.getName().endsWith(".mp3"))
                    .forEach(file -> musics.add(MusicService.saveMusic(new Music(super.getDirectory(), file.getName()))));
        }

        musicListView.getItems().clear();
        musicListView.getItems().addAll(musics);
    }

    @Override
    public void setPlayerService() {
        super.setPlayerService(PlayerService.getInstance());

        super.getPlayerService().setCurrentPlayerController(this);

        super.getPlayerService().setProgressBar(progressBar);

        super.getPlayerService().setTimer(timer);
    }

    @Override
    public void refreshPlayingNow() {
        if(String.valueOf(selectedMusic).length() > 42){
            musicNamePlayingNow.setFont(new Font("System", (double) 860 / String.valueOf(selectedMusic).length()));
        }else{
            musicNamePlayingNow.setFont(new Font("System", 20));
        }

        musicNamePlayingNow.setText(String.valueOf(selectedMusic));
    }

    @Override
    public void refreshSelectedMusic(int index) {
        selectedMusic = musicListView.getItems().get(index);

        musicListView.getSelectionModel().select(index);

        refreshPlayingNow();

    }

    @Override
    public void selectMusic() throws FileNotFoundException, JavaLayerException {
        selectedMusic = musicListView.getSelectionModel().getSelectedItem();

        if (selectedMusic != null){
            super.getPlayerService().selectMusicForPlayer(selectedMusic);

            playingNowText.setVisible(true);

            refreshPlayingNow();
        }
    }

    @FXML
    @Override
    public void onChooseDirectoryButton() {

        defaultDirectory = false;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showOpenDialog(null);

        if(fileChooser.getSelectedFile() != null){
            selectedPlaylist = null;

            labelDirectory.setText(fileChooser.getName());

            super.setDirectory(fileChooser.getSelectedFile().getAbsolutePath());
            getMusicsFromDirectory();
        }
    }

    @FXML
    @Override
    public void onDefaultDirectoryButton() {
        defaultDirectory = true;

        switch (LanguageService.getLanguage()){
            case "English":
                labelDirectory.setText("Default directory");
                break;
            case "Français":
                labelDirectory.setText("Répertoire par défaut");
                break;
            case "Português":
                labelDirectory.setText("Diretório padrão");
                break;
            case "日本語":
                labelDirectory.setText("");
                break;
            default:
                throw new InvalidLanguageException(LanguageService.getLanguage());
        }

        selectedPlaylist = null;

        super.setDirectory(System.getProperty("user.dir") + "/src/resources/songs");

        getMusicsFromDirectory();
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

    @FXML
    @Override
    public void onCloseButton(ActionEvent event){
        WindowService.closeWindow(event, super.getStage());
        if (stagePlaylist != null){
            stagePlaylist.close();
        }
    }

    @FXML
    @Override
    public void onMinimizeButton(ActionEvent event){
        WindowService.minimizeWindow(event, super.getStage());
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
    public void onButtonCreatePlaylist(ActionEvent event) throws IOException {

        if (stagePlaylist == null || !stagePlaylist.isShowing()) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CreatePlaylistView.fxml"));
            Parent root = loader.load();

            stagePlaylist = new Stage();

            stagePlaylist.initStyle(StageStyle.UNDECORATED);

            WindowService.moveWindow(stagePlaylist, root);

            stagePlaylist.setScene(new Scene(root));
            stagePlaylist.setResizable(false);
            stagePlaylist.setAlwaysOnTop(true);
            stagePlaylist.showAndWait();

            reloadPlaylists();
        }
    }

    public void reloadPlaylists(){
        playlistListView.getItems().clear();

        LoginService loginService = LoginService.getInstance();

        playlistListView.getItems().addAll(((VipUser) loginService.getLoggedUser()).getPlaylists());
    }

    @FXML
    public void selectPlaylist(){
        selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();

        if(selectedPlaylist != null) {
            musicListView.getItems().clear();
            musicListView.getItems().addAll(selectedPlaylist.getMusics());
            labelDirectory.setText(selectedPlaylist.getName());
        }

    }

    @FXML
    public void onButtonAddMusic(){
        JFileChooser jFileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3", "mp3");

        jFileChooser.setFileFilter(filter);
        jFileChooser.setMultiSelectionEnabled(true);

        int response = jFileChooser.showOpenDialog(null);

        if(response == JFileChooser.APPROVE_OPTION){
            List<File> files = List.of(jFileChooser.getSelectedFiles());

            if(selectedPlaylist == null){
                files.forEach(file -> musicListView.getItems().add(MusicService.saveMusic(new Music(file))));
            }else{
                defaultDirectory = false;
                files.forEach(file -> selectedPlaylist.addMusicToPlaylist(new Music(file)));
                musicListView.getItems().clear();
                musicListView.getItems().addAll(selectedPlaylist.getMusics());
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

    public void editPlaylistListStyle(String backgroundColor, String textColor){
        playlistListView.setCellFactory(new Callback<ListView<Playlist>, ListCell<Playlist>>() {
            @Override
            public ListCell<Playlist> call(ListView<Playlist> playlistListView) {
                return new ListCell<Playlist>() {
                    @Override
                    protected void updateItem(Playlist item, boolean empty) {
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
                txt31.setText("Create a"); txt32.setText("playlist");
                txt41.setText("Add"); txt42.setText("music");
                playingNowText.setText("Playing now:");
                if(super.getDirectory().endsWith("src/resources/songs")){
                    labelDirectory.setText("Default directory");
                }
                labelPlaylist.setText("My playlists");
                break;
            case "Français":
                txt11.setText("Sélectionner"); txt12.setText("un répertoire");
                txt21.setText("Répertoire"); txt22.setText("par défaut");
                txt31.setText("Créer une"); txt32.setText("playlist");
                txt41.setText("Additionner"); txt42.setText("musique");
                playingNowText.setText("En ce moment:");
                if(super.getDirectory().endsWith("src/resources/songs")){
                    labelDirectory.setText("Répertoire par défaut");
                }
                labelPlaylist.setText("Mes playlists");
                break;
            case "Português":
                txt11.setText("Selecionar"); txt12.setText("um diretório");
                txt21.setText("Diretório"); txt22.setText("padrão");
                txt31.setText("Criar uma"); txt32.setText("playlist");
                txt41.setText("Adicionar"); txt42.setText("música");
                playingNowText.setText("Tocando agora:");
                if(super.getDirectory().endsWith("src/resources/songs")){
                    labelDirectory.setText("Diretório padrão");
                }
                labelPlaylist.setText("Minhas playlists");
                break;
            case "日本語":
                txt11.setText(""); txt12.setText("");
                txt21.setText(""); txt22.setText("");
                txt31.setText(""); txt32.setText("");
                txt41.setText(""); txt42.setText("");
                playingNowText.setText("今演奏:");
                if(super.getDirectory().endsWith("src/resources/songs")){
                    labelDirectory.setText("私の音楽");
                }
                labelPlaylist.setText("私のプレイリスト");
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
                buttonCreatePlaylist.setStyle("-fx-background-radius: 25; -fx-background-color: white");
                buttonAddMusic.setStyle("-fx-background-radius: 25; -fx-background-color: white");

                labelDirectory.setStyle("-fx-text-fill: white");
                labelPlaylist.setStyle("-fx-text-fill: white");

                txt11.setStyle("-fx-text-fill: black"); txt12.setStyle("-fx-text-fill: black");
                txt21.setStyle("-fx-text-fill: black"); txt22.setStyle("-fx-text-fill: black");
                txt31.setStyle("-fx-text-fill: black"); txt32.setStyle("-fx-text-fill: black");
                txt41.setStyle("-fx-text-fill: black"); txt42.setStyle("-fx-text-fill: black");

                progressBar.setStyle("-fx-control-inner-background: #111111; -fx-border-color: black; -fx-accent: #4AFF22");

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

                editMusicListStyle("white", "black");
                editPlaylistListStyle("white", "black");
                break;
            case LIGHT:
                background.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2;");

                configLight.setVisible(false);
                configDark.setVisible(true);

                buttonSelectDirectory.setStyle("-fx-background-radius: 25; -fx-background-color: black;");
                buttonDefaultDirectory.setStyle("-fx-background-radius: 25; -fx-background-color: black;");
                buttonCreatePlaylist.setStyle("-fx-background-radius: 25; -fx-background-color: black;");
                buttonAddMusic.setStyle("-fx-background-radius: 25; -fx-background-color: black;");

                labelDirectory.setStyle("-fx-text-fill: black");
                labelPlaylist.setStyle("-fx-text-fill: black");

                txt11.setStyle("-fx-text-fill: white"); txt12.setStyle("-fx-text-fill: white");
                txt21.setStyle("-fx-text-fill: white"); txt22.setStyle("-fx-text-fill: white");
                txt31.setStyle("-fx-text-fill: white"); txt32.setStyle("-fx-text-fill: white");
                txt41.setStyle("-fx-text-fill: white"); txt42.setStyle("-fx-text-fill: white");

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

                editMusicListStyle("#EEEEEE", "black");
                editPlaylistListStyle("#EEEEEE", "black");
                break;
            default:
                throw new InvalidThemeException(String.valueOf(ThemeService.getTheme()));
        }
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