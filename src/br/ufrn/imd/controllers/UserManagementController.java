package br.ufrn.imd.controllers;

import br.ufrn.imd.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public abstract class UserManagementController extends MainController {
    private UserService userService = UserService.getInstance();

    @FXML
    private Label txtEmailLabel;
    @FXML
    private Label txtPasswordLabel;
    @FXML
    private Label questionLabel;

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonRegister;

    protected UserService getUserService() {
        return userService;
    }

    protected Label getTxtEmailLabel() {
        return txtEmailLabel;
    }

    protected Label getTxtPasswordLabel() {
        return txtPasswordLabel;
    }

    protected Label getQuestionLabel() {
        return questionLabel;
    }

    protected TextField getTxtEmail() {
        return txtEmail;
    }

    protected PasswordField getPasswordField() {
        return passwordField;
    }

    protected Button getButtonLogin() {
        return buttonLogin;
    }

    protected Button getButtonRegister() {
        return buttonRegister;
    }

    @FXML
    public abstract void onButtonLogin(ActionEvent event) throws IOException;

    @FXML
    public abstract void onButtonRegister(ActionEvent event) throws IOException;
}
