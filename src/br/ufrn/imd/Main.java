package br.ufrn.imd;

import br.ufrn.imd.repositories.PlaylistDAO;
import br.ufrn.imd.services.PlaylistService;
import br.ufrn.imd.services.UserService;
import br.ufrn.imd.services.WindowService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        UserService userService = UserService.getInstance();
        PlaylistService playlistService = new PlaylistService();

        userService.loadUsers();
        playlistService.loadPlaylists();

        Parent root = FXMLLoader.load(getClass().getResource("view/LoginView.fxml"));

        WindowService.moveWindow(stage, root);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setOnCloseRequest(e -> Platform.exit());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
