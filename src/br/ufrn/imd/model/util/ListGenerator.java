package br.ufrn.imd.model.util;

import java.util.ArrayList;
import java.util.List;

public class ListGenerator {
    public static List<String> getProgramLanguages(){
        List<String> languages = new ArrayList<>();

        languages.add("English");
        languages.add("Français");
        languages.add("Português");
        languages.add("日本語");

        return languages;
    }
}
