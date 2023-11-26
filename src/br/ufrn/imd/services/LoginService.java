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

        switch (LanguageService.getLanguage()){
            case "Français":
                labelInvalidLogin.setText("Email ou mot de passe invalide.");
                break;
            case "Português":
                labelInvalidLogin.setText("Email ou senha de usuário inválidos.");
                break;
            case "日本語":
                labelInvalidLogin.setText("メールアドレスまたはパスワードが無効です。");
                break;
            default:
                labelInvalidLogin.setText("Invalid email address or password.");
                break;
        }

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