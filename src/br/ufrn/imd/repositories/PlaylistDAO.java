package br.ufrn.imd.repositories;

import br.ufrn.imd.model.entities.Playlist;

import java.util.ArrayList;
import java.util.List;

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
}
