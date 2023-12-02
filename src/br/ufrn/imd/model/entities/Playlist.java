package br.ufrn.imd.model.entities;

import br.ufrn.imd.repositories.PlaylistDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Playlist {
    private Long id;
    private String name;
    private VipUser owner;
    private List<Music> musics;

    public Playlist(VipUser owner, String name) {
        PlaylistDAO playlistDAO = PlaylistDAO.getInstance();

        id = (long) (playlistDAO.getPlaylists().size() + 1);
        this.name = name;
        this.owner = owner;

        musics = new ArrayList<>();
    }

    public Playlist(VipUser owner, Long id, String name) {
        this.id = id;
        this.name = name;
        this.owner = owner;

        musics = new ArrayList<>();
    }

    public Playlist(VipUser owner, String name, Music firstMusic) {
        PlaylistDAO playlistDAO = PlaylistDAO.getInstance();

        id = (long) (playlistDAO.getPlaylists().size() + 1);
        this.name = name;
        this.owner = owner;

        musics = new ArrayList<>();

        musics.add(firstMusic);
    }

    public Playlist(VipUser owner, String name, Playlist importedPlaylist) {
        id = (long) (owner.getPlaylistsQuantity() + 1);
        this.name = name;
        this.owner = owner;

        musics = new ArrayList<>();

        musics = importedPlaylist.getMusics();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public VipUser getOwner() {
        return owner;
    }

    public void setOwner(VipUser owner) {
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Music> getMusics() {
        return musics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(id, playlist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addMusicToPlaylist(Music music){
        musics.add(music);
    }

    public void removeMusicFromPlaylist(Music music){
        musics.remove(music);
    }

    @Override
    public String toString() {
        return name;
    }
}
