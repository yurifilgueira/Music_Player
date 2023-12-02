package br.ufrn.imd.controllers;

import br.ufrn.imd.model.enums.Theme;
import br.ufrn.imd.repositories.exceptions.InvalidLanguageException;
import br.ufrn.imd.repositories.exceptions.InvalidThemeException;
import br.ufrn.imd.services.LanguageService;
import br.ufrn.imd.services.ThemeService;
import br.ufrn.imd.services.util.ChoiceBoxFormatter;
import br.ufrn.imd.services.util.ListGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigController implements Initializable {
    @FXML
    private AnchorPane background;
    @FXML
    private Label languageLabel;
    @FXML
    private Label themeLabel;
    @FXML
    private ChoiceBox<String> languagePicker;
    @FXML
    private ChoiceBox<String> themePicker;
    @FXML
    private Button applyButton;
    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        languagePicker.getItems().addAll(ListGenerator.getProgramLanguages());

        languagePicker.getSelectionModel().selectFirst();

        ChoiceBoxFormatter.themeFormatter(themePicker, Theme.DARK);
        ChoiceBoxFormatter.themeFormatter(themePicker, Theme.LIGHT);

        themePicker.getSelectionModel().selectFirst();
    }

    @FXML
    public void onLanguagePicker(ActionEvent event){
        System.out.println("a");
    }

    @FXML
    public void onThemePicker(ActionEvent event){
        switch (themePicker.getValue()){
            case "Dark":
                background.setStyle("-fx-background-color: black;");
                languageLabel.setStyle("-fx-background-color: white;");
                themeLabel.setStyle("-fx-background-color: white;");
                languagePicker.setStyle("-fx-background-color: white;");
                languageLabel.setStyle("-fx-background-color: white;");
                applyButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
                cancelButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
                break;
            case "Light":
                background.setStyle("-fx-background-color: white;");
                languageLabel.setStyle("-fx-background-color: black;");
                themeLabel.setStyle("-fx-background-color: black;");
                languagePicker.setStyle("-fx-background-color: black;");
                languageLabel.setStyle("-fx-background-color: black;");
                applyButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
                cancelButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
                break;
            default:
                throw new InvalidThemeException(themePicker.getValue());
        }
    }

    public void changeSystemLanguage(){
        switch(languagePicker.getValue()){
            case "English", "Français", "Português", "日本語":
                LanguageService.setLanguage(languagePicker.getValue());
                break;
            default:
                throw new InvalidLanguageException(languagePicker.getValue());
        }
    }

    public void changeSystemTheme(){
        switch(themePicker.getValue()){
            case "Dark":
                ThemeService.setTheme(Theme.DARK);
                break;
            case "Light":
                ThemeService.setTheme(Theme.LIGHT);
                break;
            default:
                throw new InvalidThemeException(themePicker.getValue());
        }
    }

    @FXML
    public void onButtonApply(ActionEvent event){
        changeSystemLanguage();

        changeSystemTheme();

        getStage(event).close();
    }

    @FXML
    public void onButtonCancel(ActionEvent event) {
        getStage(event).close();
    }

    public Stage getStage(ActionEvent event){
        return ((Stage) ((Node)event.getSource()).getScene().getWindow());
    }
}
