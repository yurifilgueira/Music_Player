package br.ufrn.imd.model.entities;

import java.util.ArrayList;
import java.util.List;

public class VipUser extends User{
    private List<Playlist> playlists;

    public VipUser(Long id, String name, String email, String password) {
        super(id, name, email, password);

        playlists = new ArrayList<>();
    }
}
