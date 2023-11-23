package br.ufrn.imd.controller;

import br.ufrn.imd.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private UserService userService = new UserService();

    @FXML
    private TextField txtEmail;
    @FXML
    private Label labelInvalidLogin;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void onButtonLogin(ActionEvent event){

        String userEmail = txtEmail.getText();
        String userPassword = passwordField.getText();

        if (userService.containsUser(userEmail)){
            if (userService.loginInformationIsValid(userEmail, userPassword)){
                System.out.println("Login realizado.");
            }
            else {
                setStyleForInvalidLogin();
            }
        }
        else {
            setStyleForInvalidLogin();
        }

    }

    public void setStyleForInvalidLogin(){

        labelInvalidLogin.setText("Email ou senha de usuário inválidos.");

        Border invalidLoginBorder = new Border(new BorderStroke(
                Paint.valueOf("RED"),
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(1)
        ));

        txtEmail.setBorder(invalidLoginBorder);
        passwordField.setBorder(invalidLoginBorder);

    }

    @FXML
    public void onButtonRegister(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("../view/RegisterView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}