package br.ufrn.imd.services;

import br.ufrn.imd.model.entities.Playlist;
import br.ufrn.imd.repositories.PlaylistDAO;

public class PlaylistService {

    private PlaylistDAO playlistDAO;

    public PlaylistService() {
        this.playlistDAO = PlaylistDAO.getInstance();
    }

    public void putPlaylist(Playlist playlist){
        playlistDAO.putPlaylist(playlist);
    }

    public void deletePlaylist(Playlist playlist){
        playlistDAO.putPlaylist(playlist);
    }
}
