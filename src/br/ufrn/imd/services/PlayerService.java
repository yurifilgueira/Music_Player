package br.ufrn.imd.services;

import br.ufrn.imd.model.entities.Music;
import br.ufrn.imd.model.enums.PlayerStatus;
import javafx.scene.control.ProgressBar;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static br.ufrn.imd.model.enums.PlayerStatus.FINISHED;
import static br.ufrn.imd.model.enums.PlayerStatus.NOTSTARTED;
import static br.ufrn.imd.model.enums.PlayerStatus.PAUSED;
import static br.ufrn.imd.model.enums.PlayerStatus.PLAYING;

public class PlayerService /*extends Thread*/ {
    private static PlayerService instance;

    private Music currentMusic;

    private double currentMusicSize;

    private Player player;

    private PlayerStatus playerStatus;

    private ProgressBar progressBar;

    private final Object playerLock = new Object();

    private PlayerService(){}

    public static PlayerService getInstance(){
        if(instance == null){
            instance = new PlayerService();
        }
        return instance;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setCurrentMusicSize(Music music) throws FileNotFoundException, BitstreamException {
        FileInputStream fileInputStream = new FileInputStream(music.getFile());
        Bitstream bitstream = new Bitstream(fileInputStream);

        Header h = bitstream.readFrame();

        int size = h.calculate_framesize();

        long tn = 0;

        try {
            tn = fileInputStream.getChannel().size();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        currentMusicSize = h.total_ms((int) tn)/1000;
    }

    public void selectMusicForPlayer(Music music) throws FileNotFoundException, JavaLayerException {
        if(music != null){
            currentMusic = music;

            setCurrentMusicSize(music);

            player = new Player(new BufferedInputStream(new FileInputStream(music.getFile())));
        }

        if(playerStatus != PLAYING){
            playerStatus = NOTSTARTED;
        }
    }

    public void play() throws JavaLayerException {
        synchronized (playerLock) {
            switch (playerStatus) {
                case NOTSTARTED:
                    final Runnable r = new Runnable() {
                        public void run() {
                            playInternal();
                        }
                    };
                    final Thread t = new Thread(r);
                    t.setDaemon(true);
                    t.setPriority(Thread.MAX_PRIORITY);
                    playerStatus = PLAYING;
                    t.start();
                    break;
                case PAUSED:
                    resume();
                    break;
                default:
                    break;
            }
        }
    }

    public boolean pause() {
        synchronized (playerLock) {
            if (playerStatus == PLAYING) {
                playerStatus = PAUSED;
            }
            return playerStatus == PAUSED;
        }
    }

    public final boolean resume() {
        synchronized (playerLock) {
            if (playerStatus == PAUSED) {
                playerStatus = PLAYING;
                playerLock.notifyAll();
            }
            return playerStatus == PLAYING;
        }
    }

    public void stop() {
        synchronized (playerLock) {
            playerStatus = FINISHED;
            playerLock.notifyAll();
        }
    }

    private void playInternal() {
        while (playerStatus != FINISHED) {
            try {
                if (!player.play(1)) {
                    break;
                }

                progressBar.setProgress(player.getPosition() / 1000.0 / currentMusicSize);
            } catch (final JavaLayerException e) {
                break;
            }
            // check if paused or terminated
            synchronized (playerLock) {
                while (playerStatus == PAUSED) {
                    try {
                        playerLock.wait();
                    } catch (final InterruptedException e) {
                        // terminate player
                        break;
                    }
                }
            }
        }
        close();
    }

    public void close() {
        synchronized (playerLock) {
            playerStatus = FINISHED;
        }
        try {
            player.close();

            if(currentMusic.getNext() != null){
                selectMusicForPlayer(currentMusic.getNext());

                play();
            }
        } catch (final Exception e) {
            // ignore, we are terminating anyway
        }
    }
}
