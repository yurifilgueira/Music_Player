package br.ufrn.imd.controllers;

import br.ufrn.imd.model.entities.CommonUser;
import br.ufrn.imd.model.entities.VipUser;
import br.ufrn.imd.repositories.UserDAO;
import br.ufrn.imd.services.UserService;
import br.ufrn.imd.services.WindowService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminUserController extends MainController implements Initializable {
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
        System.out.println(selectedCommonUser);

        if(selectedCommonUser != null){
            userService.putUser(new VipUser(selectedCommonUser.getId(), selectedCommonUser.getName(), selectedCommonUser.getEmail(), selectedCommonUser.getPassword()));
            userService.deleteUser(selectedCommonUser);
        }
    }

    @FXML
    public void down(){
        if(selectedVipUser != null){
            userService.putUser(new CommonUser(selectedVipUser.getId(), selectedVipUser.getName(), selectedVipUser.getEmail(), selectedVipUser.getPassword()));
            userService.deleteUser(selectedVipUser);
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
