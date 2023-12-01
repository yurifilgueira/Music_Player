package br.ufrn.imd.services;

import br.ufrn.imd.model.entities.Playlist;
import br.ufrn.imd.model.entities.VipUser;
import br.ufrn.imd.repositories.PlaylistDAO;

import java.util.List;
import java.util.regex.Pattern;

public class PlaylistService {

    private PlaylistDAO playlistDAO;

    public PlaylistService() {
        this.playlistDAO = PlaylistDAO.getInstance();
    }

    public void putPlaylist(Playlist playlist){
        LoginService loginService = LoginService.getInstance();

        for (Playlist p : ((VipUser) loginService.getLoggedUser()).getPlaylists()) {
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

        playlistDAO.putPlaylist(playlist);
        ((VipUser) loginService.getLoggedUser()).addPlaylist(playlist);
    }

    public void deletePlaylist(Playlist playlist){
        LoginService loginService = LoginService.getInstance();

        playlistDAO.putPlaylist(playlist);
        ((VipUser) loginService.getLoggedUser()).removePlaylist(playlist);
    }

    public List<Playlist> getPlaylist() {
        return playlistDAO.getPlaylists();
    }
}
