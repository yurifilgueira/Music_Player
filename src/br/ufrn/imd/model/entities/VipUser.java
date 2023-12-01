package br.ufrn.imd.model.entities;

import br.ufrn.imd.services.PlaylistService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class VipUser extends User{
    private List<Playlist> playlists;
    private PlaylistService playlistService;

    public VipUser() {
        super();
    }

    public VipUser(Long id, String name, String email, String password) {
        super(id, name, email, password);

        playlists = new ArrayList<>();
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public Playlist getPlaylistById(Long id){
        if(playlists.isEmpty()){
            return null;
        }

        for (Playlist p : playlists) {
            if(id == p.getId()){
                return p;
            }
        }

        return null;
    }

    public int getPlaylistsQuantity(){
        return playlists.size();
    }

    public void addPlaylist(Playlist playlist){
        playlists.add(playlist);
    }

    public void removePlaylist(Playlist playlist){
        playlists.remove(playlist);
    }
}
