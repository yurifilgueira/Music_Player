package br.ufrn.imd.services;

public class LanguageService {
    private static String language;

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        LanguageService.language = language;
    }
}
