package br.ufrn.imd.model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Playlist {
    private String name;

    private List<Music> musics;

    public Playlist(String name) {
        this.name = name;

        musics = new ArrayList<>();
    }

    public Playlist(String name, Music firstMusic) {
        this.name = name;

        musics = new ArrayList<>();

        musics.add(firstMusic);

        autoAdjustPreviousAndNext();
    }

    public void autoAdjustPreviousAndNext(){
        musics.get(0).setPrevious(null);

        musics.get(musics.size() - 1).setNext(null);


    }

    public void addMusicToPlaylist(Music music){
        musics.add(music);

        autoAdjustPreviousAndNext();

        IntStream.range(1, musics.size()).forEach(i -> musics.get(i).setPrevious(musics.get(i - 1)));

        IntStream.range(0, musics.size() - 1).forEach(i -> musics.get(i).setNext(musics.get(i + 1)));
    }

    public void removeMusicFromPlaylist(Music music){
        musics.remove(music);

        autoAdjustPreviousAndNext();
    }
}
