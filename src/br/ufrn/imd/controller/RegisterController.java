package br.ufrn.imd.controller;

import br.ufrn.imd.model.entities.CommonUser;
import br.ufrn.imd.model.entities.User;
import br.ufrn.imd.model.entities.VipUser;
import br.ufrn.imd.model.enums.UserType;
import br.ufrn.imd.model.util.ListGenerator;
import br.ufrn.imd.repositories.exceptions.InvalidLanguageException;
import br.ufrn.imd.services.LanguageService;
import br.ufrn.imd.services.RegisterService;
import br.ufrn.imd.services.UserService;
import br.ufrn.imd.services.util.EmailValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private RegisterService registerService = new RegisterService();
    private UserService userService = new UserService();

    @FXML
    private ChoiceBox<String> languagePicker;
    @FXML
    private ChoiceBox<UserType> userTypePicker;

    @FXML
    private Label greetingsLabel;
    @FXML
    private Label orientationLabel;
    @FXML
    private Label txtNameLabel;
    @FXML
    private Label txtEmailLabel;
    @FXML
    private Label txtPasswordLabel;
    @FXML
    private Label questionLabel;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Label labelNameIsMissing;
    @FXML
    private Label labelInvalidEmail;
    @FXML
    private Label labelPasswordIsMissing;

    @FXML
    private Button buttonRegister;
    @FXML
    private Button buttonLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        languagePicker.getItems().addAll(ListGenerator.getProgramLanguages());

        if(LanguageService.getLanguage() == null){
            languagePicker.getSelectionModel().selectFirst();
        }else{
            languagePicker.getSelectionModel().select(LanguageService.getLanguage());
            changeLanguage();
        }

        languagePicker.setOnAction(this::onLanguagePicker);

        userTypePicker.getItems().addAll(UserType.values());
        userTypePicker.getSelectionModel().selectFirst();
    }

    @FXML
    void onButtonRegister(ActionEvent event) {

        registerService.resetMissingLabels(labelNameIsMissing, labelInvalidEmail, labelPasswordIsMissing);

        boolean someFieldIsEmpty = registerService.someFieldIsEmpty(txtName.getText(), txtEmail.getText(), passwordField.getText());

        if (someFieldIsEmpty){
            setStyleToEmptyField(txtName, txtEmail, passwordField);

            if (!registerService.fieldEmailIsEmpty(txtEmail.getText())){
                registerService.setStyleToInvalidEmail(txtEmail, labelInvalidEmail);
            }
        }
        else {

            String userEmail = txtEmail.getText();

            if (!EmailValidator.emailIsValid(userEmail)){
                registerService.setStyleToInvalidEmail(txtEmail, labelInvalidEmail);
            }
            else if (!userService.containsUser(userEmail)) {

                String name = txtName.getText();
                String email = txtEmail.getText();
                String password = passwordField.getText();

                if(userTypePicker.getValue() == UserType.VIP){
                    User user = new VipUser(null, name, email, password);
                    userService.putUser(user);

                    System.out.println("Usuário VIP registrado.");
                }else{
                    User user = new CommonUser(null, name, email, password);
                    userService.putUser(user);

                    System.out.println("Usuário comum registrado.");
                }
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
            labelInvalidEmail.setText("*Campo obrigatório");
            txtEmail.setBorder(emptyFieldBorder);
        }

        if (passwordFieldIsEmpty){
            labelPasswordIsMissing.setText("*Campo obrigatório");
            passwordField.setBorder(emptyFieldBorder);
        }
    }

    @FXML
    public void onButtonLogin(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("../view/LoginView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeLanguage(){
        switch(languagePicker.getValue()){
            case "English":
                greetingsLabel.setText("Welcome to Music Player");

                orientationLabel.setText("Enter your data to register in MusicPlayer");

                txtNameLabel.setText("Username");
                txtName.setPromptText("Enter your name");

                txtEmailLabel.setText("Email");
                txtEmail.setPromptText("Enter your email");

                txtPasswordLabel.setText("Password");
                passwordField.setPromptText("Enter your password");

                buttonRegister.setText("Sign up");

                questionLabel.setText("Already have an account ?");

                buttonLogin.setText("Back");

                break;
            case "Français":
                greetingsLabel.setText("Bienvenue");

                orientationLabel.setText("");

                txtNameLabel.setText("Nom d'utilisateur");
                txtName.setPromptText("");

                txtEmailLabel.setText("Email");
                txtEmail.setPromptText("");

                txtPasswordLabel.setText("Mot de passe");
                passwordField.setPromptText("");

                buttonRegister.setText("S'inscrire");

                questionLabel.setText("Vous avez déjà un compte ?");

                buttonLogin.setText("Retourner");

                break;
            case "Português":
                greetingsLabel.setText("Seja bem-vindo(a) ao Music Player");

                orientationLabel.setText("Insira seus dados para se registrar no Music Player");

                txtNameLabel.setText("Nome de Usuário");
                txtName.setPromptText("Insira seu nome");

                txtEmailLabel.setText("Email");
                txtEmail.setPromptText("Insira seu email");

                txtPasswordLabel.setText("Senha");
                passwordField.setPromptText("Insira sua senha");

                buttonRegister.setText("Registre-se");

                questionLabel.setText("Já possui login ?");

                buttonLogin.setText("Voltar");

                break;
            case "日本語":
                greetingsLabel.setText("ようこそ");

                orientationLabel.setText("");

                txtNameLabel.setText("名前");
                txtName.setPromptText("名前を入力してください");

                txtEmailLabel.setText("メール");
                txtEmail.setPromptText("メールを入力してください");

                txtPasswordLabel.setText("パスワード");
                passwordField.setPromptText("パスワードを入力してください");

                buttonRegister.setText("アカウント登録");

                questionLabel.setText("すでにアカウントをお持ちですか？");

                buttonLogin.setText("戻る");

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