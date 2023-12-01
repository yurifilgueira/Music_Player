package br.ufrn.imd.controllers;

import br.ufrn.imd.model.entities.CommonUser;
import br.ufrn.imd.model.entities.User;
import br.ufrn.imd.model.entities.VipUser;
import br.ufrn.imd.model.enums.UserType;
import br.ufrn.imd.repositories.exceptions.InvalidLanguageException;
import br.ufrn.imd.services.LanguageService;
import br.ufrn.imd.services.RegisterService;
import br.ufrn.imd.services.WindowService;
import br.ufrn.imd.services.util.EmailValidator;
import br.ufrn.imd.services.util.ListGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController extends UserManagementController implements Initializable {
    private RegisterService registerService = new RegisterService();

    @FXML
    private ChoiceBox<UserType> userTypePicker;

    @FXML
    private Label greetingsLabel;
    @FXML
    private Label orientationLabel;
    @FXML
    private Label txtNameLabel;
    @FXML
    private Label successfulLabel;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelNameIsMissing;
    @FXML
    private Label labelInvalidEmail;
    @FXML
    private Label labelPasswordIsMissing;

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

        userTypePicker.getItems().addAll(UserType.values());
        userTypePicker.getSelectionModel().selectFirst();
    }

    @FXML @Override
    public void onButtonRegister(ActionEvent event) {

        registerService.resetMissingLabels(labelNameIsMissing, labelInvalidEmail, labelPasswordIsMissing);

        boolean someFieldIsEmpty = registerService.someFieldIsEmpty(txtName.getText(), super.getTxtEmail().getText(), super.getPasswordField().getText());

        if (someFieldIsEmpty){
            setStyleToEmptyField(txtName);

            if (!registerService.fieldEmailIsEmpty(super.getTxtEmail().getText())){
                registerService.setStyleToInvalidEmail(super.getTxtEmail(), labelInvalidEmail);
            }
        }
        else {

            String userEmail = super.getTxtEmail().getText();

            if (!EmailValidator.emailIsValid(userEmail)){
                registerService.setStyleToInvalidEmail(super.getTxtEmail(), labelInvalidEmail);
            }
            else if (!super.getUserService().containsUser(userEmail)) {

                String name = txtName.getText();
                String email = super.getTxtEmail().getText();
                String password = super.getPasswordField().getText();

                if(userTypePicker.getValue() == UserType.VIP){
                    User user = new VipUser(null, name, email, password);
                    super.getUserService().putUser(user);

                    System.out.println("Usuário VIP registrado.");
                }else{
                    User user = new CommonUser(null, name, email, password);
                    super.getUserService().putUser(user);

                    System.out.println("Usuário comum registrado.");
                }

                successfulLabel.setVisible(true);
            }
        }
    }

    public void setStyleToEmptyField(TextField txtName) {

        boolean nameFieldIsEmpty = registerService.fieldNameIsEmpty(txtName.getText());
        boolean emailFieldIsEmpty = registerService.fieldEmailIsEmpty(super.getTxtEmail().getText());
        boolean passwordFieldIsEmpty = registerService.fieldPasswordIsEmpty(super.getPasswordField().getText());

        Border emptyFieldBorder = new Border(new BorderStroke(
                Paint.valueOf("RED"),
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(1)
        ));

        if (nameFieldIsEmpty){
            switch(LanguageService.getLanguage()){
                case "Français":
                    labelNameIsMissing.setText("*Champ obligatoire.");
                    break;
                case "Português":
                    labelNameIsMissing.setText("*Campo obrigatório.");
                    break;
                default:
                    labelNameIsMissing.setText("*Required field.");
                    break;
            }

            txtName.setBorder(emptyFieldBorder);
        }

        if (emailFieldIsEmpty){
            switch(LanguageService.getLanguage()){
                case "Français":
                    labelInvalidEmail.setText("*Champ obligatoire.");
                    break;
                case "Português":
                    labelInvalidEmail.setText("*Campo obrigatório.");
                    break;
                default:
                    labelInvalidEmail.setText("*Required field.");
                    break;
            }

            super.getTxtEmail().setBorder(emptyFieldBorder);
        }

        if (passwordFieldIsEmpty){
            switch(LanguageService.getLanguage()){
                case "Français":
                    labelPasswordIsMissing.setText("*Champ obligatoire.");
                    break;
                case "Português":
                    labelPasswordIsMissing.setText("*Campo obrigatório.");
                    break;
                default:
                    labelPasswordIsMissing.setText("*Required field.");
                    break;
            }

            super.getPasswordField().setBorder(emptyFieldBorder);
        }
    }

    @FXML @Override
    public void onButtonLogin(ActionEvent event) throws IOException {
        super.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/LoginView.fxml"))));
        super.setStage((Stage) ((Node)event.getSource()).getScene().getWindow());
        super.setScene(new Scene(super.getRoot()));

        WindowService.moveWindow(super.getStage(), super.getRoot());

        super.getStage().setScene(super.getScene());
        super.getStage().show();
    }

    public void changeLanguage(){
        switch(super.getLanguagePicker().getValue()){
            case "English":
                greetingsLabel.setText("Welcome to Music Player");

                orientationLabel.setText("Enter your data to register in MusicPlayer");

                txtNameLabel.setText("Username");
                txtName.setPromptText("Enter your name");

                super.getTxtEmailLabel().setText("Email");
                super.getTxtEmail().setPromptText("Enter your email");

                super.getTxtPasswordLabel().setText("Password");
                super.getPasswordField().setPromptText("Enter your password");

                super.getButtonRegister().setText("Sign up");

                successfulLabel.setText("Account created successfully.");

                super.getQuestionLabel().setText("Already have an account ?");

                super.getButtonLogin().setText("Back");

                break;
            case "Français":
                greetingsLabel.setText("Bienvenue sur Music Player");

                orientationLabel.setText("");

                txtNameLabel.setText("Nom d'utilisateur");
                txtName.setPromptText("Entrez votre nom d'utilisateur");

                super.getTxtEmailLabel().setText("Email");
                super.getTxtEmail().setPromptText("Entrez votre email");

                super.getTxtPasswordLabel().setText("Mot de passe");
                super.getPasswordField().setPromptText("Entrez votre mot de passe");

                super.getButtonRegister().setText("S'inscrire");

                successfulLabel.setText("");

                super.getQuestionLabel().setText("Vous avez déjà un compte ?");

                super.getButtonLogin().setText("Retourner");

                break;
            case "Português":
                greetingsLabel.setText("Seja bem-vindo(a) ao Music Player");

                orientationLabel.setText("Insira seus dados para se registrar no Music Player");

                txtNameLabel.setText("Nome de Usuário");
                txtName.setPromptText("Insira seu nome");

                super.getTxtEmailLabel().setText("Email");
                super.getTxtEmail().setPromptText("Insira seu email");

                super.getTxtPasswordLabel().setText("Senha");
                super.getPasswordField().setPromptText("Insira sua senha");

                super.getButtonRegister().setText("Registre-se");

                successfulLabel.setText("Conta criada com sucesso.");

                super.getQuestionLabel().setText("Já possui login ?");

                super.getButtonLogin().setText("Voltar");

                break;
            case "日本語":
                greetingsLabel.setText("「Music Player」へようこそ");

                orientationLabel.setText("");

                txtNameLabel.setText("名前");
                txtName.setPromptText("名前を入力してください");

                super.getTxtEmailLabel().setText("メール");
                super.getTxtEmail().setPromptText("メールを入力してください");

                super.getTxtPasswordLabel().setText("パスワード");
                super.getPasswordField().setPromptText("パスワードを入力してください");

                super.getButtonRegister().setText("アカウント登録");

                successfulLabel.setText("");

                super.getQuestionLabel().setText("すでにアカウントをお持ちですか？");

                super.getButtonLogin().setText("戻る");

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

    public void setSuccessfulLabelNotVisible(){
        successfulLabel.setVisible(false);
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