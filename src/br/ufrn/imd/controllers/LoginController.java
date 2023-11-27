package br.ufrn.imd.controllers;

import br.ufrn.imd.repositories.exceptions.InvalidLanguageException;
import br.ufrn.imd.services.LanguageService;
import br.ufrn.imd.services.LoginService;
import br.ufrn.imd.services.UserService;
import br.ufrn.imd.services.util.EmailValidator;
import br.ufrn.imd.services.util.ListGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {
    private UserService userService = new UserService();
    private LoginService loginService = new LoginService();

    @FXML
    private ChoiceBox<String> languagePicker;

    @FXML
    private Label txtEmailLabel;
    @FXML
    private Label txtPasswordLabel;
    @FXML
    private Label questionLabel;

    @FXML
    private TextField txtEmail;
    @FXML
    private Label labelInvalidLogin;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonRegister;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        languagePicker.getItems().addAll(ListGenerator.getProgramLanguages());

        if(LanguageService.getLanguage() == null){
            languagePicker.getSelectionModel().selectFirst();
            LanguageService.setLanguage("English");
        }else{
            languagePicker.getSelectionModel().select(LanguageService.getLanguage());
            changeLanguage();
        }

        languagePicker.setOnAction(this::onLanguagePicker);
    }

    @FXML
    public void onButtonLogin(ActionEvent event) throws IOException {

        String userEmail = txtEmail.getText();
        String userPassword = passwordField.getText();

        if (userService.containsUser(userEmail) && EmailValidator.emailIsValid(userEmail)){
            if (userService.loginInformationIsValid(userEmail, userPassword)){
                super.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/CommonUserView.fxml"))));
                super.setStage((Stage) ((Node)event.getSource()).getScene().getWindow());
                super.setScene(new Scene(super.getRoot()));
                super.getStage().setScene(super.getScene());
                super.getStage().show();
            }
            else {
                loginService.setStyleForInvalidLogin(labelInvalidLogin, txtEmail, passwordField);
            }
        }
        else {
            loginService.setStyleForInvalidLogin(labelInvalidLogin, txtEmail, passwordField);
        }

    }

    @FXML
    public void onButtonRegister(ActionEvent event) throws IOException {
        super.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/RegisterView.fxml"))));
        super.setStage((Stage) ((Node)event.getSource()).getScene().getWindow());
        super.setScene(new Scene(super.getRoot()));
        super.getStage().setScene(super.getScene());
        super.getStage().show();

    }

    public void changeLanguage(){
        switch(languagePicker.getValue()){
            case "English":
                txtEmailLabel.setText("Email");
                txtEmail.setPromptText("Enter your email");

                txtPasswordLabel.setText("Password");
                passwordField.setPromptText("Enter your password");

                buttonLogin.setText("Login");
                questionLabel.setText("Don't have an account ?");
                buttonRegister.setText("Sign up");

                break;
            case "Français":
                txtEmailLabel.setText("Email");
                txtEmail.setPromptText("");

                txtPasswordLabel.setText("Mot de passe");
                passwordField.setPromptText("");

                buttonLogin.setText("Se connecter");
                questionLabel.setText("Vous n'avez pas de compte ?");
                buttonRegister.setText("S'inscrire");

                break;
            case "Português":
                txtEmailLabel.setText("Email");
                txtEmail.setPromptText("Insira seu email");

                txtPasswordLabel.setText("Senha");
                passwordField.setPromptText("Insira sua senha");

                buttonLogin.setText("Entrar");
                questionLabel.setText("Não possui login ?");
                buttonRegister.setText("Registre-se");

                break;
            case "日本語":
                txtEmailLabel.setText("メール");
                txtEmail.setPromptText("メールを入力してください");

                txtPasswordLabel.setText("パスワード");
                passwordField.setPromptText("パスワードを入力してください");

                buttonLogin.setText("ログイン");
                questionLabel.setText("アカウントをお持ちではありませんか?");
                buttonRegister.setText("アカウント登録");

                break;
            default:
                throw new InvalidLanguageException(languagePicker.getValue());
        }
    }

    @FXML
    public void onLanguagePicker(ActionEvent event){
        changeLanguage();

        LanguageService.setLanguage(languagePicker.getValue());
    }
}