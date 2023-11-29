package br.ufrn.imd.controllers;

import br.ufrn.imd.repositories.exceptions.InvalidLanguageException;
import br.ufrn.imd.services.LanguageService;
import br.ufrn.imd.services.LoginService;
import br.ufrn.imd.services.WindowService;
import br.ufrn.imd.services.util.EmailValidator;
import br.ufrn.imd.services.util.ListGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController extends UserManagementController implements Initializable {
    private LoginService loginService = new LoginService();

    @FXML
    private Label labelInvalidLogin; // the only one

    @FXML
    private Button buttonRegister;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.getLanguagePicker().getItems().addAll(ListGenerator.getProgramLanguages());

        if(LanguageService.getLanguage() == null){
            super.getLanguagePicker().getSelectionModel().selectFirst();
            LanguageService.setLanguage("English");
        }else{
            super.getLanguagePicker().getSelectionModel().select(LanguageService.getLanguage());
            changeLanguage();
        }

        super.getLanguagePicker().setOnAction(this::onLanguagePicker);
    }

    @FXML @Override
    public void onButtonLogin(ActionEvent event) throws IOException {

        String userEmail = super.getTxtEmail().getText();
        String userPassword = super.getPasswordField().getText();

        if (super.getUserService().containsUser(userEmail) && EmailValidator.emailIsValid(userEmail)){
            if (super.getUserService().loginInformationIsValid(userEmail, userPassword)){
                super.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/CommonUserView.fxml"))));
                super.setStage((Stage) ((Node)event.getSource()).getScene().getWindow());
                super.setScene(new Scene(super.getRoot()));

                WindowService.moveWindow(super.getStage(), super.getRoot());

                super.getStage().setScene(super.getScene());
                super.getStage().centerOnScreen();
                super.getStage().show();
            }
            else {
                loginService.setStyleForInvalidLogin(labelInvalidLogin, super.getTxtEmail(), super.getPasswordField());
            }
        }
        else {
            loginService.setStyleForInvalidLogin(labelInvalidLogin, super.getTxtEmail(), super.getPasswordField());
        }

    }

    @FXML @Override
    public void onButtonRegister(ActionEvent event) throws IOException {
        super.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/RegisterView.fxml"))));
        super.setStage((Stage) ((Node)event.getSource()).getScene().getWindow());
        super.setScene(new Scene(super.getRoot()));

        WindowService.moveWindow(super.getStage(), super.getRoot());

        super.getStage().setScene(super.getScene());
        super.getStage().show();

    }

    public void changeLanguage(){
        switch(super.getLanguagePicker().getValue()){
            case "English":
                super.getTxtEmailLabel().setText("Email");
                super.getTxtEmail().setPromptText("Enter your email");

                super.getTxtPasswordLabel().setText("Password");
                super.getPasswordField().setPromptText("Enter your password");

                super.getButtonLogin().setText("Login");
                super.getQuestionLabel().setText("Don't have an account ?");
                super.getButtonRegister().setText("Sign up");

                break;
            case "Français":
                super.getTxtEmailLabel().setText("Email");
                super.getTxtEmail().setPromptText("Entrez votre email");

                super.getTxtPasswordLabel().setText("Mot de passe");
                super.getPasswordField().setPromptText("Entrez votre mot de passe");

                super.getButtonLogin().setText("Se connecter");
                super.getQuestionLabel().setText("Vous n'avez pas de compte ?");
                super.getButtonRegister().setText("S'inscrire");

                break;
            case "Português":
                super.getTxtEmailLabel().setText("Email");
                super.getTxtEmail().setPromptText("Insira seu email");

                super.getTxtPasswordLabel().setText("Senha");
                super.getPasswordField().setPromptText("Insira sua senha");

                super.getButtonLogin().setText("Entrar");
                super.getQuestionLabel().setText("Não possui login ?");
                super.getButtonRegister().setText("Registre-se");

                break;
            case "日本語":
                super.getTxtEmailLabel().setText("メール");
                super.getTxtEmail().setPromptText("メールを入力してください");

                super.getTxtPasswordLabel().setText("パスワード");
                super.getPasswordField().setPromptText("パスワードを入力してください");

                super.getButtonLogin().setText("ログイン");
                super.getQuestionLabel().setText("アカウントをお持ちではありませんか?");
                super.getButtonRegister().setText("アカウント登録");

                break;
            default:
                throw new InvalidLanguageException(super.getLanguagePicker().getValue());
        }
    }

    @FXML
    public void onLanguagePicker(ActionEvent event){
        changeLanguage();

        LanguageService.setLanguage(super.getLanguagePicker().getValue());
    }

    @FXML @Override
    public void onCloseButton(ActionEvent event){
        WindowService.closeWindow(event, super.getStage());
    }

    @FXML @Override
    public void onMinimizeButton(ActionEvent event){
        WindowService.minimizeWindow(event, super.getStage());
    }
}