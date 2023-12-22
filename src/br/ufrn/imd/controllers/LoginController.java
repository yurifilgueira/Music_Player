package br.ufrn.imd.controllers;

import br.ufrn.imd.model.entities.CommonUser;
import br.ufrn.imd.model.entities.VipUser;
import br.ufrn.imd.repositories.exceptions.InvalidLanguageException;
import br.ufrn.imd.repositories.exceptions.InvalidThemeException;
import br.ufrn.imd.services.LanguageService;
import br.ufrn.imd.services.LoginService;
import br.ufrn.imd.services.ThemeService;
import br.ufrn.imd.services.WindowService;
import br.ufrn.imd.services.util.EmailValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController extends UserManagementController implements Initializable {
    @FXML
    private AnchorPane background;
    @FXML
    private ImageView logoLight;
    @FXML
    private ImageView logoDark;
    @FXML
    private Button configLight;
    @FXML
    private Button configDark;
    @FXML
    private Label title;

    private LoginService loginService = LoginService.getInstance();

    private Stage stageConfig;
    
    @FXML
    private Label labelInvalidLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeLanguage();
        changeTheme();
    }

    @FXML
    @Override
    public void onButtonLogin(ActionEvent event) throws IOException {

        String userEmail = super.getTxtEmail().getText();
        String userPassword = super.getPasswordField().getText();

        if (super.getUserService().containsUser(userEmail) && EmailValidator.emailIsValid(userEmail)){
            if (super.getUserService().loginInformationIsValid(userEmail, userPassword)){

                loginService.setLoggedUser(getUserService().getByEmail(userEmail));

                if (super.getUserService().getByEmail(userEmail) instanceof CommonUser) {
                    super.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/CommonUserView.fxml"))));
                }
                else if (super.getUserService().getByEmail(userEmail) instanceof VipUser) {
                    super.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/VipUserView.fxml"))));
                }
                else{
                    super.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/AdminUserView.fxml"))));
                }

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

    @FXML
    @Override
    public void onButtonRegister(ActionEvent event) throws IOException {
        super.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/RegisterView.fxml"))));
        super.setStage((Stage) ((Node)event.getSource()).getScene().getWindow());
        super.setScene(new Scene(super.getRoot()));

        WindowService.moveWindow(super.getStage(), super.getRoot());

        super.getStage().setScene(super.getScene());
        super.getStage().show();

    }

    public void changeLanguage(){
        switch(LanguageService.getLanguage()){
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
                throw new InvalidLanguageException(LanguageService.getLanguage());
        }
    }

    public void changeTheme(){
        switch(ThemeService.getTheme()){
            case DARK:
                background.setStyle("-fx-background-color: black; -fx-border-color: white");
                configDark.setVisible(false);
                configLight.setVisible(true);
                logoDark.setVisible(false);
                logoLight.setVisible(true);
                title.setStyle("-fx-text-fill: white;");
                super.getTxtEmailLabel().setStyle("-fx-text-fill: white;");
                super.getTxtEmail().setStyle("-fx-background-radius:0; -fx-border-color: #FFFFFF; -fx-background-color: #222222; -fx-prompt-text-fill: white; -fx-text-fill: white;");
                super.getTxtPasswordLabel().setStyle("-fx-text-fill: white;");
                super.getPasswordField().setStyle("-fx-background-radius:0; -fx-border-color: #FFFFFF; -fx-background-color: #222222; -fx-prompt-text-fill: white; -fx-text-fill: white;");
                super.getQuestionLabel().setStyle("-fx-text-fill: white;");
                super.getButtonLogin().setStyle("-fx-background-radius: 25; -fx-background-color: white; -fx-text-fill: black;");
                super.getButtonRegister().setStyle("-fx-background-radius: 25; -fx-background-color: white; -fx-text-fill: black;");
                break;
            case LIGHT:
                background.setStyle("-fx-background-color: white; -fx-border-color: black");
                configLight.setVisible(false);
                configDark.setVisible(true);
                logoLight.setVisible(false);
                logoDark.setVisible(true);
                title.setStyle("-fx-text-fill: black;");
                super.getTxtEmailLabel().setStyle("-fx-text-fill: black;");
                super.getTxtEmail().setStyle("-fx-background-radius: 1; -fx-border-color: black; -fx-background-color: #DEDEDE; -fx-prompt-text-fill: black; -fx-text-fill: black;");
                super.getTxtPasswordLabel().setStyle("-fx-text-fill: black;");
                super.getPasswordField().setStyle("-fx-background-radius: 1; -fx-border-color: black; -fx-background-color: #DEDEDE; -fx-prompt-text-fill: black; -fx-text-fill: black;");
                super.getQuestionLabel().setStyle("-fx-text-fill: black;");
                super.getButtonLogin().setStyle("-fx-background-radius: 25; -fx-background-color: black; -fx-text-fill: white;");
                super.getButtonRegister().setStyle("-fx-background-radius: 25; -fx-background-color: black; -fx-text-fill: white;");
                break;
            default:
                throw new InvalidThemeException(ThemeService.getTheme().toString());
        }
    }

    @FXML
    public void onConfigButton(ActionEvent event) throws IOException {
        if(stageConfig == null || !stageConfig.isShowing()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ConfigView.fxml"));
            Parent root = loader.load();

            stageConfig = new Stage();

            stageConfig.initStyle(StageStyle.UNDECORATED);

            WindowService.moveWindow(stageConfig, root);

            stageConfig.setScene(new Scene(root));
            stageConfig.setResizable(false);
            stageConfig.setAlwaysOnTop(true);
            stageConfig.showAndWait();

            changeLanguage();
            changeTheme();
        }
    }

    @FXML
    @Override
    public void onCloseButton(ActionEvent event){
        WindowService.closeWindow(event, super.getStage());
    }

    @FXML
    @Override
    public void onMinimizeButton(ActionEvent event){
        WindowService.minimizeWindow(event, super.getStage());
    }
}