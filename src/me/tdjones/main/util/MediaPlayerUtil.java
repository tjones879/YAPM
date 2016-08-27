package me.tdjones.main.util;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import me.tdjones.main.model.Episode;

public class MediaPlayerUtil {
    private static Episode currPlaying;
    private static MediaPlayer mediaPlayer;
    private static SimpleBooleanProperty isMuted = new SimpleBooleanProperty(false);
    private static SimpleBooleanProperty isPlaying = new SimpleBooleanProperty(false);


    public static void setCurrPlaying(Episode episode){
        currPlaying = episode;
    }

    public static Episode getCurrPlaying(){
        return currPlaying;
    }

    public static SimpleBooleanProperty getIsPlaying(){
        return isPlaying;
    }

    public static SimpleBooleanProperty getIsMuted(){
        return isMuted;
    }

    public static void setMediaPlayer(MediaPlayer mp){
        mediaPlayer = mp;
    }

    public static MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }

    public static void toggleMute(){
        isMuted.set(!isMuted.get());
        mediaPlayer.setMute(isMuted.get());
    }

    public static boolean handlePlayPause(){
        if (isPlaying.get()){
            pause();
        } else {
            play();
        }
        return isPlaying.get();
    }

    public static void play(){
        mediaPlayer.play();
        isPlaying.set(true);
    }

    public static void pause(){
        mediaPlayer.pause();
        isPlaying.set(false);
    }

    public static void skipBack(){
        mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(Duration.seconds(10)));
    }

    public static void skipForward(){
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(10)));
    }

    public static void seek(Double seekTime){
        mediaPlayer.seek(Duration.millis(seekTime));
    }

    public static Duration getCurrTime(){
        return mediaPlayer.getCurrentTime();
    }

    private static void setOnEndOfMedia(){
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
