package br.ufrn.imd.services.util;

import br.ufrn.imd.model.enums.Theme;
import br.ufrn.imd.services.LanguageService;
import javafx.scene.control.ChoiceBox;

public class ChoiceBoxFormatter {
    public static void themeFormatter(ChoiceBox<String> themes){
        themes.getItems().clear();

        themes.getItems().add("Dark");
        themes.getItems().add("Light");
    }
}
