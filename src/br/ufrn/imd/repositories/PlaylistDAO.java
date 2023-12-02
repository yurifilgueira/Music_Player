package br.ufrn.imd.repositories;

import br.ufrn.imd.model.entities.Music;
import br.ufrn.imd.model.entities.Playlist;
import br.ufrn.imd.model.entities.User;
import br.ufrn.imd.model.entities.VipUser;
import br.ufrn.imd.services.PlaylistService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PlaylistDAO {

    private List<Playlist> playlists;
    private static PlaylistDAO playlistDAO;

    private PlaylistDAO() {
        this.playlists = new ArrayList<>();
    }

    public static PlaylistDAO getInstance(){
        if (playlistDAO == null){
            playlistDAO = new PlaylistDAO();
        }

        return playlistDAO;
    }

    public void putPlaylist(Playlist playlist){
        playlists.add(playlist);
    }

    public void deletePlaylist(Playlist playlist){
        playlists.remove(playlist);
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void savePlaylists(){
        for (Playlist p : playlists) {
            StringBuilder path = new StringBuilder("src/resources/dataResources/playlist_");
            path.append(p.getOwner().getId()).append("_").append(p.getId()).append(".txt");

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toString(), false))){

                bufferedWriter.append(p.getName());
                bufferedWriter.newLine();

                bufferedWriter.append(String.valueOf(p.getMusics().size()));
                bufferedWriter.newLine();

                for (Music m : p.getMusics()) {
                    bufferedWriter.append(m.getFullDirectory());
                    bufferedWriter.newLine();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void loadPlaylist(File file){
        String[] fileName = file.getName().split("_");

        UserDAO userDAO = UserDAO.getInstance();
        User user = userDAO.getById(Integer.parseInt(fileName[1]));


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();

            if(user instanceof VipUser){
                Playlist playlist = new Playlist((VipUser) user, line);

                ((VipUser) user).addPlaylist(playlist);

                line = bufferedReader.readLine();
                line = bufferedReader.readLine();

                while (line != null) {
                    playlist.addMusicToPlaylist(new Music(line));

                    line = bufferedReader.readLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void loadPlaylists(){
        String path = ("src/resources/dataResources");
        Stream.of(Objects.requireNonNull(new File(path).listFiles()))
                .filter(file -> !file.isDirectory() && file.getName().startsWith("playlist") && file.getName().endsWith(".txt"))
                .forEach(this::loadPlaylist);
    }
}
