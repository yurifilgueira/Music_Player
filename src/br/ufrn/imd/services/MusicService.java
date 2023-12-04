package br.ufrn.imd.services;

import br.ufrn.imd.model.entities.Music;
import br.ufrn.imd.model.entities.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicService {
    private static Map<String, String> musicDirectories = new HashMap<>();
    private static List<String> directories = new ArrayList<>();
    private static LoginService loginService = LoginService.getInstance();

    private static MusicService musicService;
    private static UserService userService = UserService.getInstance();

    public static MusicService getInstance(){
        if (musicService == null){
            musicService = new MusicService();
        }

        return musicService;
    }

    public static Music saveMusic(Music music){
        musicDirectories.put(music.toString(), music.getFullDirectory());
        if(!loginService.getLoggedUser().getDirectories().contains(music.getFullDirectory()) && !music.getFullDirectory().contains("src/resources/songs")){
            directories.add(music.getFullDirectory());
            loginService.getLoggedUser().addDirectory(music.getFullDirectory());
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


            for (User user : userService.getUsers()){

                if (!user.getDirectories().isEmpty()) {

                    directoriesWriter.write(String.valueOf(user.getId()));
                    for (String directory : user.getDirectories()) {
                        directoriesWriter.newLine();
                        directoriesWriter.append(directory);
                    }
                    directoriesWriter.newLine();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadMusics() {

        String path = "src/resources/dataResources/directories.txt";


        if(verifyIfFileExists(path)) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {

                String line = bufferedReader.readLine();
                long userId = 0L;

                while (line != null){
                    if (isNumeric(line)){

                        userId = Long.parseLong(line);
                        line = bufferedReader.readLine();

                        if (!isNumeric(line)) {

                            User user = userService.getById(userId);

                            if(user != null) {
                                user.addDirectory(line);
                                directories.add(line);
                            }
                        }
                    }
                    else {
                        if (userService.getById(userId) != null) {
                            userService.getById(userId).addDirectory(line);
                            directories.add(line);
                        }
                    }

                    line = bufferedReader.readLine();

                }

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public boolean verifyIfFileExists(String path){
        File file = new File(path);

        return file.exists();

    }

    public boolean isNumeric(String line){
        for (Character c : line.toCharArray()){
            if (!Character.isDigit(c)){
                return false;
            }
        }

        return true;
    }

}
