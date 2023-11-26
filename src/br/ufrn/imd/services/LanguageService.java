package br.ufrn.imd.services;

import br.ufrn.imd.repositories.exceptions.InvalidLanguageException;

public class LanguageService {
    private static String language;

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        if(!language.equals("English") && !language.equals("Français") && !language.equals("Português") && !language.equals("日本語")){
            throw new InvalidLanguageException(language);
        }
        LanguageService.language = language;
    }
}
