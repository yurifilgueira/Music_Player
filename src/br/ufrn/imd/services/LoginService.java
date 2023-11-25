package br.ufrn.imd.services;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Paint;

public class LoginService {

    public void setStyleForInvalidLogin(Label labelInvalidLogin, TextField txtEmail, PasswordField passwordField){

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
}