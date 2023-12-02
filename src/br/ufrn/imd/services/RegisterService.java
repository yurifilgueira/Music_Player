package br.ufrn.imd.services;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Paint;

public class RegisterService {

    UserService userService = UserService.getInstance();

    public boolean someFieldIsEmpty(String name, String email, String password){
        return fieldNameIsEmpty(name) || fieldEmailIsEmpty(email) || fieldPasswordIsEmpty(password);
    }

    public boolean fieldNameIsEmpty(String name){
        return name.isEmpty();
    }

    public boolean fieldEmailIsEmpty(String email){



        return email.isEmpty();
    }

    public boolean fieldPasswordIsEmpty(String password){
        return password.isEmpty();
    }

    public void resetMissingLabels(Label labelNameIsMissing, Label labelInvalidEmail, Label labelPasswordIsMissing) {

        if (!labelNameIsMissing.getText().isEmpty()) labelNameIsMissing.setText("");
        if (!labelInvalidEmail.getText().isEmpty()) labelInvalidEmail.setText("");
        if (!labelPasswordIsMissing.getText().isEmpty()) labelPasswordIsMissing.setText("");

    }

    public void setStyleToInvalidEmail(TextField txtEmail, Label labelInvalidEmail) {

        Border invalidEmailBorder = new Border(new BorderStroke(
                Paint.valueOf("RED"),
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(1)
        ));

        txtEmail.setBorder(invalidEmailBorder);

        switch(LanguageService.getLanguage()){
            case "Français":
                labelInvalidEmail.setText("*Email invalide.");
            case "Português":
                labelInvalidEmail.setText("*Email inválido.");
            case "日本語":
                labelInvalidEmail.setText("*無効なメール。");
            default:
                labelInvalidEmail.setText("*Invalid email.");

        }
    }

    public boolean emailAlreadyInUse(String email){
        return userService.containsUser(email);
    }

}