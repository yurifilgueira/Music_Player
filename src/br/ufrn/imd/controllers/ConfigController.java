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

        languagePicker.getSelectionModel().select(LanguageService.getLanguage());

        changeLanguage();

        languagePicker.setOnAction(this::onLanguagePicker);

        ChoiceBoxFormatter.themeFormatter(themePicker);

        if(ThemeService.getTheme() == Theme.DARK){
            themePicker.getSelectionModel().selectFirst();
        }else{
            themePicker.getSelectionModel().select(1);
        }
        
        themePicker.setOnAction(this::onThemePicker);

        changeTheme();
    }

    public void changeLanguage(){
        switch (languagePicker.getValue()) {
            case "English":
                languageLabel.setText("Language:");
                themeLabel.setText("Theme:");
                applyButton.setText("Apply");
                cancelButton.setText("Cancel");
                break;
            case "Français":
                languageLabel.setText("Langue:");
                themeLabel.setText("Thème:");
                applyButton.setText("Appliquer");
                cancelButton.setText("Annuler");
                break;
            case "Português":
                languageLabel.setText("Idioma:");
                themeLabel.setText("Tema:");
                applyButton.setText("Aplicar");
                cancelButton.setText("Cancelar");
                break;
            case "日本語":
                languageLabel.setText("言語:");
                themeLabel.setText("テーマ:");
                applyButton.setText("適用する");
                cancelButton.setText("キャンセル");
                break;
            default:
                throw new InvalidLanguageException(languagePicker.getValue());
        }
    }

    public void changeTheme(){
       switch (themePicker.getValue()){
            case "Dark":
                background.setStyle("-fx-background-color: black; -fx-border-color: white; -fx-border-width: 1");
                languageLabel.setStyle("-fx-text-fill: white;");
                themeLabel.setStyle("-fx-text-fill: white;");
                languagePicker.setStyle("-fx-background-color: white; -fx-background-radius:25");
                themePicker.setStyle("-fx-background-color: white; -fx-background-radius:25");
                applyButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius:25");
                cancelButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius:25");
                break;
            case "Light":
                background.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2");
                languageLabel.setStyle("-fx-text-fill: black;");
                themeLabel.setStyle("-fx-text-fill: black;");
                languagePicker.setStyle("-fx-background-color: #DDDDDD; -fx-background-radius:25");
                themePicker.setStyle("-fx-background-color: #DDDDDD; -fx-background-radius:25");
                applyButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-background-radius:25");
                cancelButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-background-radius:25");
                break;
            default:
                throw new InvalidThemeException(themePicker.getValue());
        }
    }

    @FXML
    public void onLanguagePicker(ActionEvent event){
        changeLanguage();
    }

    @FXML
    public void onThemePicker(ActionEvent event){
        changeTheme();
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
