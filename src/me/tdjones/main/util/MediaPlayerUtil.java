package me.tdjones.main.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import me.tdjones.main.model.Episode;

public class MediaPlayerUtil {
    private static Episode currPlaying;
    private static boolean isMuted;
    private static MediaPlayer mediaPlayer;
    private static boolean isPlaying;


    public static void setCurrPlaying(Episode episode){
        currPlaying = episode;
    }

    public static Episode getCurrPlaying(){
        return currPlaying;
    }

    public static void setMediaPlayer(MediaPlayer mp){
        mediaPlayer = mp;
    }

    public static boolean toggleMute(){
        mediaPlayer.setMute(!isMuted);
        isMuted = !isMuted;
        return isMuted;
    }

    
    public static boolean handlePlayPause(){
        if (isPlaying){
            pause();
        } else {
            play();
        }
        return isPlaying;
    }

    public static void play(){
        mediaPlayer.play();
        isPlaying = true;
    }

    public static void pause(){
        mediaPlayer.pause();
        isPlaying = false;
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
