package br.ufrn.imd.services;

import br.ufrn.imd.model.entities.Music;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicService {
    private static Map<String, String> musicDirectories = new HashMap<>();
    private static List<String> directories = new ArrayList<>();

    public static Music saveMusic(Music music){
        musicDirectories.put(music.toString(), music.getFullDirectory());
        if(!directories.contains(music.getFullDirectory()) && !music.getFullDirectory().equals("src/resources/songs")){
            directories.add(music.getFullDirectory());
        }
        return music;
    }
    
    public static void writeSavedMusics() {
        String musicsPath = "src/resources/dataResources/musics.txt";

        try (BufferedWriter musicsWriter = new BufferedWriter(new FileWriter(musicsPath, false))) {
            for (String s : musicDirectories.keySet()) {
                musicsWriter.append(s);
                musicsWriter.newLine();

                musicsWriter.append(musicDirectories.get(s));
                musicsWriter.newLine();


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String directoriesPath = "src/resources/dataResources/directories.txt";

        try (BufferedWriter directoriesWriter = new BufferedWriter(new FileWriter(directoriesPath, false))) {
            for (String s : directories) {
                directoriesWriter.append(s);
                directoriesWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
