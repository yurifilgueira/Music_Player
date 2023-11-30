package br.ufrn.imd.model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Playlist {
    private Long id;
    private String name;
    private List<Music> musics;

    public Playlist(VipUser owner, String name) {
        id = (long) (owner.getPlaylistsQuantity() + 1);
        this.name = name;

        musics = new ArrayList<>();
    }

    public Playlist(VipUser owner, String name, Music firstMusic) {
        id = (long) (owner.getPlaylistsQuantity() + 1);
        this.name = name;

        musics = new ArrayList<>();

        musics.add(firstMusic);
    }

    public Playlist(VipUser owner, String name, Playlist importedPlaylist) {
        id = (long) (owner.getPlaylistsQuantity() + 1);
        this.name = name;

        musics = new ArrayList<>();

        musics = importedPlaylist.getMusics();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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
}
