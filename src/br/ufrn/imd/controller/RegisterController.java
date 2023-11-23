package br.ufrn.imd.controller;

import br.ufrn.imd.model.entities.CommonUser;
import br.ufrn.imd.model.entities.User;
import br.ufrn.imd.services.RegisterService;
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

public class RegisterController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private RegisterService registerService = new RegisterService();
    private UserService userService = new UserService();

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;
    @FXML
    private Label labelNameIsMissing;
    @FXML
    private Label labelEmailIsMissing;
    @FXML
    private Label labelPasswordIsMissing;

    @FXML
    void onButtonRegister(ActionEvent event) {

        registerService.resetMissingLabels(labelNameIsMissing, labelEmailIsMissing, labelPasswordIsMissing);

        boolean someFieldIsEmpty = registerService.someFieldIsEmpty(txtName.getText(), txtEmail.getText(), passwordField.getText());

        if (someFieldIsEmpty){
            setStyleToEmptyField(txtName, txtEmail, passwordField);
        }
        else {

            String userEmail = txtEmail.getText();

            if (!userService.containsUser(userEmail)) {

                String name = txtName.getText();
                String email = txtEmail.getText();
                String password = passwordField.getText();

                User user = new CommonUser(null, name, email, password);
                userService.putUser(user);

                System.out.println("Registro realizado.");

            }
        }
    }

    public void setStyleToEmptyField(TextField txtName, TextField txtEmail, PasswordField passwordField) {

        boolean nameFieldIsEmpty = registerService.fieldNameIsEmpty(txtName.getText());
        boolean emailFieldIsEmpty = registerService.fieldEmailIsEmpty(txtEmail.getText());
        boolean passwordFieldIsEmpty = registerService.fieldPasswordIsEmpty(passwordField.getText());

        Border emptyFieldBorder = new Border(new BorderStroke(
                Paint.valueOf("RED"),
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(1)
        ));

        if (nameFieldIsEmpty){
            labelNameIsMissing.setText("*Campo obrigatório");
            txtName.setBorder(emptyFieldBorder);
        }

        if (emailFieldIsEmpty){
            labelEmailIsMissing.setText("*Campo obrigatório");
            txtEmail.setBorder(emptyFieldBorder);
        }

        if (passwordFieldIsEmpty){
            labelPasswordIsMissing.setText("*Campo obrigatório");
            passwordField.setBorder(emptyFieldBorder);
        }
    }

    @FXML
    void onButtonLogin(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("../view/LoginView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}