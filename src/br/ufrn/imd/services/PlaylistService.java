package br.ufrn.imd.services;

import br.ufrn.imd.model.entities.Playlist;
import br.ufrn.imd.repositories.PlaylistDAO;

import java.util.List;

public class PlaylistService {

    private PlaylistDAO playlistDAO = PlaylistDAO.getInstance();

    public PlaylistService() {
        this.playlistDAO = PlaylistDAO.getInstance();
    }

    public void putPlaylist(Playlist playlist){
        playlistDAO.putPlaylist(playlist);
    }

    public void deletePlaylist(Playlist playlist){
        playlistDAO.putPlaylist(playlist);
    }

    public List<Playlist> getPlaylist() {
        return playlistDAO.getPlaylists();
    }
}
