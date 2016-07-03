package me.tdjones.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import me.tdjones.main.model.Feed;
import me.tdjones.main.model.Playlist;
import me.tdjones.main.util.MediaPlayerUtil;
import me.tdjones.main.util.XMLUtil;
import me.tdjones.main.view.FeedLayoutController;
import me.tdjones.main.view.RootLayoutController;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


public class Main extends Application{

    private static Stage primaryStage;
    private BorderPane musicMain;
    private File directory;
    private Playlist nowPlaying;
    private MediaPlayer mediaPlayer;
    private Scene scene;
    private RootLayoutController rootLayoutController;
    private FeedLayoutController feedLayoutController;
    private Feed feed;

    public final static String itunesNamespace = "http://www.itunes.com/dtds/podcast-1.0.dtd";

    public static void main(String[] args) {
        Application.launch(Main.class);
    }

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        try {
            feed = XMLUtil.parseRSS(new URL("http://rss.earwolf.com/beautiful-anonymous"));
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        MediaPlayerUtil.setCurrPlaying(feed.getEpisodeList().get(0));
        mediaPlayer = new MediaPlayer(new Media("http://feeds.soundcloud.com/stream/271124645-beautiful-anonymous-17-early-onset.mp3"));
        initLayout();
    }

    private void initLayout(){
        rootLayoutController = new RootLayoutController();

        scene = new Scene(rootLayoutController.getRootBorderPane(), 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
