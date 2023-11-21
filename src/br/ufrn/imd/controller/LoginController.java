package br.ufrn.imd.controller;

import br.ufrn.imd.Repository.UserDAO;
import br.ufrn.imd.model.entities.User;
import br.ufrn.imd.model.entities.VipUser;
import br.ufrn.imd.services.UserServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class LoginController {

    AtomicLong idGenerator = new AtomicLong();
    List<User> users = new ArrayList<>();
    UserServices userService = new UserServices();

    @FXML
    protected TextField txtEmail;
    @FXML
    protected Label labelInvalidLogin;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonRegister;

    @FXML
    public void onButtonLogin(){

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
    public void onButtonRegister(){
        userService.putUser(new VipUser(null, txtEmail.getText(), "Test", passwordField.getText()));
        System.out.println("Usuário registrado.");

    }
}