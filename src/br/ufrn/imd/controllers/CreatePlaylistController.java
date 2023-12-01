package br.ufrn.imd.controllers;

import br.ufrn.imd.model.entities.Playlist;
import br.ufrn.imd.model.entities.VipUser;
import br.ufrn.imd.services.LoginService;
import br.ufrn.imd.services.PlaylistService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class CreatePlaylistController{

    private LoginService loginService = LoginService.getInstance();
    private PlaylistService playlistService = new PlaylistService();

    @FXML
    private Label invalidPlaylistName;
    @FXML
    private TextField txtPlaylistName;

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