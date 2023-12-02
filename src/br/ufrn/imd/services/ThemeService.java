package br.ufrn.imd.services;

import br.ufrn.imd.model.enums.Theme;

public class ThemeService {
    private static Theme theme;

    public static Theme getTheme() {
        return theme;
    }

    public static void setTheme(Theme theme) {
        ThemeService.theme = theme;
    }
}
