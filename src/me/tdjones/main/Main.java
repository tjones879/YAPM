package me.tdjones.main;

import javafx.application.Application;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Main extends Application{

    private static Stage primaryStage;
    private BorderPane musicMain;
    private static File subscriptionDir;
    private Playlist nowPlaying;
    private MediaPlayer mediaPlayer;
    private Scene scene;
    private RootLayoutController rootLayoutController;
    private FeedLayoutController feedLayoutController;
    private static List<Feed> subscriptions;
    private Feed feed;

    public final static String itunesNamespace = "http://www.itunes.com/dtds/podcast-1.0.dtd";

    public static void main(String[] args) {
        subscriptionDir = getSubscriptionDir();
        importSubscriptions();

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
        rootLayoutController.updateFeedTilePane(subscriptions);
    }

    private void initLayout(){
        rootLayoutController = new RootLayoutController();

        scene = new Scene(rootLayoutController.getRootBorderPane(), 900, 600);
        scene.getStylesheets().add(getClass().getResource("resources/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void importLibrary(){
    }

    private static void importSubscriptions(){
        subscriptions = new ArrayList<>();
        List<URL> RSSUrls = XMLUtil.parseOPML(subscriptionDir);
        for (URL feedURL : RSSUrls){
            subscriptions.add(XMLUtil.parseRSS(feedURL));
        }
    }

    private static File getSubscriptionDir(){
        String directory = new String();
        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0) || (OS.indexOf("nux") >= 0)){
            directory = System.getProperty("user.home") + File.separator + "YAPM" + File.separator + "subscriptions.opml";
        } else if (OS.indexOf("win") >= 0) {
            directory = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "YAPM" + File.separator + "subscriptions.opml";
        } else {
            // TODO: Handle other OS or error cases
            directory = "";
        }
        return new File(directory);
    }
}
