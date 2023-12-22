package br.ufrn.imd.services;

import br.ufrn.imd.controllers.PlayerController;
import br.ufrn.imd.model.entities.Music;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerService {
    private static PlayerService instance;

    private Media media;
    private MediaPlayer mediaPlayer;

    private PlayerController currentPlayerController;
    private ProgressBar progressBar;
    private Label timerLabel;

    private Timer timer;
    private TimerTask task;
    private boolean isRunning;

    private PlayerService(){}

    public static PlayerService getInstance(){
        if(instance == null){
            instance = new PlayerService();
        }
        return instance;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setCurrentPlayerController(PlayerController currentPlayerController) {
        this.currentPlayerController = currentPlayerController;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setTimerLabel(Label timerLabel) {
        this.timerLabel = timerLabel;
    }

    public void selectMusicForPlayer(Music music) throws FileNotFoundException {
        if(music != null){
            if(mediaPlayer != null){
                mediaPlayer.stop();
            }

            media = new Media(music.getFile().toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            currentPlayerController.play();
        }
    }

    public void play() {
        if(mediaPlayer != null){
            beginTimer();
            mediaPlayer.play();
        }
    }

    public void pause() {
        if(mediaPlayer != null){
            cancelTimer();
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if(mediaPlayer != null){
            cancelTimer();
            mediaPlayer.stop();
        }
    }

    public TimerTask setupTimerTask(){
        return new TimerTask() {
            @Override
            public void run() {
                isRunning = true;

                double currentTime = mediaPlayer.getCurrentTime().toSeconds();

                double endingTime = media.getDuration().toSeconds();

                progressBar.setProgress(currentTime / endingTime);

                Platform.runLater(() -> {
                    long currentSeconds = (long) mediaPlayer.getCurrentTime().toSeconds();
                    long totalSeconds = (long) media.getDuration().toSeconds();
                    // These doubles are being converted to long, because the time refreshing wasn't working correctly when they were being converted directly to (int).

                    StringBuilder timerLabelText = new StringBuilder();

                    timerLabelText.append(String.format("%02d:%02d", currentSeconds / 60, currentSeconds % 60));
                    timerLabelText.append(" / ");
                    timerLabelText.append(String.format("%02d:%02d", totalSeconds / 60, totalSeconds % 60));

                    timerLabel.setText(timerLabelText.toString());
                });

                if(currentTime / endingTime == 1){
                    cancelTimer();

                    Platform.runLater(() -> {
                        try {
                            currentPlayerController.onNextButton();
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        };
    }

    public void beginTimer(){
        timer = new Timer();

        task = setupTimerTask();

        timer.scheduleAtFixedRate(task, 1000, 41);
        // The period is set to 41 because 1000 / 24 = 41.67, so the progress bar refreshes every 41 milliseconds (about 24 FPS).
    }

    public void cancelTimer(){
        isRunning = false;
        timer.cancel();
    }
}
