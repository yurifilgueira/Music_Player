package br.ufrn.imd.services.util;

import br.ufrn.imd.model.enums.Theme;
import javafx.scene.control.ChoiceBox;

public class ChoiceBoxFormatter {
    public static void themeFormatter(ChoiceBox<String> themes, Theme theme){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(theme.toString().charAt(0));
        stringBuilder.append(theme.toString().substring(1).toLowerCase());
        themes.getItems().add(stringBuilder.toString());
    }
}
