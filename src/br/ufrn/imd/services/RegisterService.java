package br.ufrn.imd.services;

import javafx.scene.control.Label;

public class RegisterService {

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

    public void resetMissingLabels(Label labelNameIsMissing, Label labelEmailIsMissing, Label labelPasswordIsMissing) {

        if (!labelNameIsMissing.getText().isEmpty()) labelNameIsMissing.setText("");
        if (!labelEmailIsMissing.getText().isEmpty()) labelEmailIsMissing.setText("");
        if (!labelPasswordIsMissing.getText().isEmpty()) labelPasswordIsMissing.setText("");

    }
}