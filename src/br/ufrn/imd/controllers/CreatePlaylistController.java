package br.ufrn.imd.controllers;

import br.ufrn.imd.model.entities.Playlist;
import br.ufrn.imd.model.entities.VipUser;
import br.ufrn.imd.repositories.exceptions.InvalidLanguageException;
import br.ufrn.imd.repositories.exceptions.InvalidThemeException;
import br.ufrn.imd.services.LanguageService;
import br.ufrn.imd.services.LoginService;
import br.ufrn.imd.services.PlaylistService;
import br.ufrn.imd.services.ThemeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreatePlaylistController implements Initializable {

    private LoginService loginService = LoginService.getInstance();
    private PlaylistService playlistService = new PlaylistService();

    @FXML
    private AnchorPane background;
    @FXML
    private Label title;
    @FXML
    private Button createButton, cancelButton;
    @FXML
    private Label invalidPlaylistName;
    @FXML
    private TextField txtPlaylistName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLanguage();
        loadTheme();
    }

    public void loadLanguage(){
        switch (LanguageService.getLanguage()){
            case "English":
                title.setText("New playlist");
                createButton.setText("Create");
                cancelButton.setText("Cancel");
                txtPlaylistName.setPromptText("Playlist name");
                break;
            case "Français":
                title.setText("Nouvelle playlist");
                createButton.setText("Créer");
                cancelButton.setText("Annuler");
                txtPlaylistName.setPromptText("Nom de la playlist");
                break;
            case "Português":
                title.setText("Nova playlist");
                createButton.setText("Criar");
                cancelButton.setText("Cancelar");
                txtPlaylistName.setPromptText("Nome da playlist");
                break;
            case "日本語":
                title.setText("");
                createButton.setText("");
                cancelButton.setText("");
                txtPlaylistName.setPromptText("");
                break;
            default:
                throw new InvalidLanguageException(LanguageService.getLanguage());
        }
    }

    public void loadTheme(){
        switch (ThemeService.getTheme()){
            case DARK:
                background.setStyle("-fx-background-color: black; -fx-border-color: white");
                title.setStyle("-fx-text-fill: white");
                createButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 25");
                cancelButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 25");
                txtPlaylistName.setStyle("-fx-background-color: #222222; -fx-prompt-text-fill: white; -fx-text-fill: white");
                break;
            case LIGHT:
                background.setStyle("-fx-background-color: white; -fx-border-color: black");
                title.setStyle("-fx-text-fill: black");
                createButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-background-radius: 25");
                cancelButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-background-radius: 25");
                txtPlaylistName.setStyle("-fx-background-color: #DDDDDD; -fx-prompt-text-fill: black; -fx-text-fill: black");
                break;
            default:
                throw new InvalidThemeException(ThemeService.getTheme().toString());
        }
    }

    @FXML
    public void onButtonCreate(ActionEvent event){

        String playlistName = txtPlaylistName.getText();

        if (playlistName.isEmpty()){
            invalidPlaylistName.setText("*A playlist name cannot be empty.");
        }
        else {
            playlistService.putPlaylist(new Playlist((VipUser) loginService.getLoggedUser(), playlistName));
            getStage(event).close();
        }
    }

    @FXML
    public void onMouseClickedPlaylistNameField(){
        invalidPlaylistName.setText("");
    }

    @FXML
    public void onButtonCancel(ActionEvent event) {

        getStage(event).close();

    }

    public Stage getStage(ActionEvent event){
        return ((Stage) ((Node)event.getSource()).getScene().getWindow());
    }
}