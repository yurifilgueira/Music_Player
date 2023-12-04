package br.ufrn.imd.controllers;

import br.ufrn.imd.model.entities.CommonUser;
import br.ufrn.imd.model.entities.VipUser;
import br.ufrn.imd.repositories.UserDAO;
import br.ufrn.imd.repositories.exceptions.InvalidLanguageException;
import br.ufrn.imd.repositories.exceptions.InvalidThemeException;
import br.ufrn.imd.services.LanguageService;
import br.ufrn.imd.services.ThemeService;
import br.ufrn.imd.services.UserService;
import br.ufrn.imd.services.WindowService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminUserController extends MainController implements Initializable {
    private Stage stageConfig;

    @FXML
    private AnchorPane background;
    @FXML
    private Label labelCommon;
    @FXML
    private Label labelVip;
    @FXML
    private Button configLight;
    @FXML
    private Button configDark;
    @FXML
    private Button upLight, upDark;
    @FXML
    private Button downLight, downDark;

    @FXML
    private ListView<CommonUser> commonUsersListView;
    @FXML
    private ListView<VipUser> vipUsersListView;

    private UserDAO userDAO;
    private UserService userService;
    private CommonUser selectedCommonUser;
    private VipUser selectedVipUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDAO = UserDAO.getInstance();
        userService = UserService.getInstance();

        changeLanguage();
        changeTheme();

        refreshLists();
    }

    public void refreshLists(){
        commonUsersListView.getItems().clear();
        vipUsersListView.getItems().clear();

        userDAO.getUsers().stream().filter(user -> user instanceof CommonUser).forEachOrdered(user -> commonUsersListView.getItems().add((CommonUser) user));
        userDAO.getUsers().stream().filter(user -> user instanceof VipUser).forEachOrdered(user -> vipUsersListView.getItems().add((VipUser) user));
    }

    public void selectCommonUser(){
        selectedCommonUser = commonUsersListView.getSelectionModel().getSelectedItem();
    }

    public void selectVipUser(){
        selectedVipUser = vipUsersListView.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void up(){
        if(selectedCommonUser != null){
            userService.deleteUser(selectedCommonUser);
            userService.putUser(new VipUser(selectedCommonUser.getId(), selectedCommonUser.getName(), selectedCommonUser.getEmail(), selectedCommonUser.getPassword()));
        }

        refreshLists();
    }

    @FXML
    public void down(){
        if(selectedVipUser != null){
            userService.deleteUser(selectedVipUser);
            userService.putUser(new CommonUser(selectedVipUser.getId(), selectedVipUser.getName(), selectedVipUser.getEmail(), selectedVipUser.getPassword()));
        }

        refreshLists();
    }

    public void changeLanguage(){
        switch (LanguageService.getLanguage()){
            case "English":
                labelCommon.setText("Common users");
                labelVip.setText("VIP users");
                break;
            case "Français":
                labelCommon.setText("Utilisateurs communs");
                labelVip.setText("Utilisateurs VIPs");
                break;
            case "Português":
                labelCommon.setText("Usuários comuns");
                labelVip.setText("Usuários VIPs");
                break;
            case "日本語":
                labelCommon.setText("一般ユーザー");
                labelVip.setText("VIPユーザー");
                break;
            default:
                throw new InvalidLanguageException(LanguageService.getLanguage());
        }
    }

    public void changeTheme(){
        switch(ThemeService.getTheme()){
            case DARK:
                background.setStyle("-fx-background-color: black; -fx-border-color: white");
                configDark.setVisible(false);
                configLight.setVisible(true);
                labelCommon.setStyle("-fx-text-fill: white");
                labelVip.setStyle("-fx-text-fill: white");
                commonUsersListView.setStyle("-fx-border-color: transparent; -fx-border-width: 0");
                vipUsersListView.setStyle("-fx-border-color: transparent; -fx-border-width: 0");
                upDark.setVisible(false); downDark.setVisible(false);
                upLight.setVisible(true); downLight.setVisible(true);
                break;
            case LIGHT:
                background.setStyle("-fx-background-color: white; -fx-border-color: black");
                configLight.setVisible(false);
                configDark.setVisible(true);
                labelCommon.setStyle("-fx-text-fill: black");
                labelVip.setStyle("-fx-text-fill: black");
                commonUsersListView.setStyle("-fx-border-color: black; -fx-border-width: 2");
                vipUsersListView.setStyle("-fx-border-color: black; -fx-border-width: 2");
                upLight.setVisible(false); downLight.setVisible(false);
                upDark.setVisible(true); downDark.setVisible(true);
                break;
            default:
                throw new InvalidThemeException(ThemeService.getTheme().toString());
        }
    }

    @FXML
    public void onConfigButton(ActionEvent event) throws IOException {
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

    @FXML
    @Override
    public void onCloseButton(ActionEvent event) {
        WindowService.closeWindow(event, super.getStage());
    }

    @FXML
    @Override
    public void onMinimizeButton(ActionEvent event) {
        WindowService.minimizeWindow(event, super.getStage());
    }

    @FXML
    public void onLogoutButton(ActionEvent event) throws IOException {
        super.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/LoginView.fxml"))));
        super.setStage((Stage) ((Node)event.getSource()).getScene().getWindow());
        super.setScene(new Scene(super.getRoot()));

        WindowService.moveWindow(super.getStage(), super.getRoot());

        super.getStage().setScene(super.getScene());
        super.getStage().centerOnScreen();
        super.getStage().show();
    }
}
