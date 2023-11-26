package br.ufrn.imd.model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class VipUser extends User{
    private List<Playlist> playlists;

    public VipUser(Long id, String name, String email, String password) {
        super(id, name, email, password);

        playlists = new ArrayList<>();
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
        for (Playlist p : playlists) {
            if(p.equals(playlist)){
                return;
            }

            if(p.getName().equals(playlist.getName())){
                if(Pattern.compile("[0-9]$").matcher(playlist.getName()).find()){
                    StringBuilder newName = new StringBuilder(playlist.getName().substring(0, playlist.getName().length() - 1));
                    newName.append((Integer.parseInt(playlist.getName().substring(playlist.getName().length() - 1)) + 1));

                    playlist.setName(newName.toString());
                }else{
                    playlist.setName(new StringBuilder(p.getName()).append("1").toString());
                }

            }
        }

        playlists.add(playlist);
    }

    public void removePlaylist(Playlist playlist){
        playlists.remove(playlist);
    }
}
