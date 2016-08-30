package me.tdjones.main.util;

public class TimeUtil {
    public static String formatTime(int seconds){
        if (seconds > 3600){
            return (String.format("%d:%02d:%02d", seconds/3600, (seconds%3600)/60, seconds%60));
        } else if (seconds < 3600 && seconds > 60) {
            return String.format("%d:%02d", seconds/60, seconds%60);
        } else {
            return String.format("%d:%02d", seconds/60, seconds%60);
        }
    }

    public static int parseTimeStamp(String length){
        String[] values = length.split(":");
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        if(values.length == 3){
            hours = Integer.parseInt(values[0]);
            minutes = Integer.parseInt(values[1]);
            seconds = Integer.parseInt(values[2]);
        }else if (values.length == 2){
            minutes = Integer.parseInt(values[0]);
            seconds = Integer.parseInt(values[1]);
        }else if (values.length == 1){
            seconds = Integer.parseInt(values[0]);
        }
        return hours*3600 + minutes*60 + seconds;
    }
}
