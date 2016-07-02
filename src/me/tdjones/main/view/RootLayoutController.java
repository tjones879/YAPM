package me.tdjones.main.view;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import me.tdjones.main.model.Feed;
import me.tdjones.main.util.PlayMedia;

import java.util.List;

/**
 * Created by Tyler on 7/1/2016.
 */
public class RootLayoutController {

    private final BorderPane rootBorderPane;
    private Label currPlayTime;
    private Label maxPlayTime;
    private SVGPath skipBackButton, playPauseButton, skipForwardButton, volumeButton;
    private ListView playingList;

    public RootLayoutController(List<Feed> feedList) {

        rootBorderPane = new BorderPane();
        rootBorderPane.setCenter(createFeedScrollPane(feedList));
        rootBorderPane.setBottom(createBottomHBox());
        rootBorderPane.setLeft(createLeftVBox());

    }

    private ScrollPane createFeedScrollPane(List<Feed> feedList) {
        ScrollPane feedScrollPane = new ScrollPane();

        TilePane tilePane = new TilePane();

        for (Feed feed : feedList) {
            VBox vBox = new VBox();
            vBox.setOnMouseClicked(event -> {
                // TODO
            });

            ImageView feedThumbnail = new ImageView(feed.getThumbnail());
            feedThumbnail.setPreserveRatio(true);
            feedThumbnail.setFitHeight(250);

            Label feedTitle = new Label(feed.getTitle());
            feedTitle.setWrapText(true);

            vBox.getChildren().addAll(feedThumbnail, feedTitle);
            tilePane.getChildren().add(vBox);
        }

        feedScrollPane.setContent(tilePane);
        return feedScrollPane;
    }

    // TODO: Refactor createBottomHBox method.
    private HBox createBottomHBox() {
        ImageView currPlayingThumbnail = new ImageView(PlayMedia.getCurrPlaying().getThumbnail());
        currPlayingThumbnail.setPreserveRatio(true);
        currPlayingThumbnail.setFitWidth(50);

        Label currPlayingFeed = new Label(PlayMedia.getCurrPlaying().getFeed().getTitle());
        currPlayingFeed.setWrapText(true);
        currPlayingFeed.setMaxWidth(150);

        AnchorPane progressNodes = new AnchorPane();
        // TODO: Remove hard-coded margins
        ProgressBar progressBar = new ProgressBar();
        progressNodes.setTopAnchor(progressBar, 2.0);
        progressNodes.setLeftAnchor(progressBar, 2.0);
        progressNodes.setRightAnchor(progressBar, 2.0);

        currPlayTime = new Label();
        progressNodes.setBottomAnchor(currPlayTime, 2.0);
        progressNodes.setLeftAnchor(currPlayTime, 2.0);

        maxPlayTime = new Label();
        progressNodes.setBottomAnchor(maxPlayTime, 2.0);
        progressNodes.setRightAnchor(maxPlayTime, 2.0);

        skipBackButton = new SVGPath();
        skipBackButton.setContent("");
        skipBackButton.setOnMouseClicked(event -> {
            // TODO
        });

        playPauseButton = new SVGPath();
        playPauseButton.setContent("");
        playPauseButton.setOnMouseClicked(event -> {
            // TODO
        });

        skipForwardButton = new SVGPath();
        skipForwardButton.setContent("");
        skipForwardButton.setOnMouseClicked(event -> {
            // TODO
        });

        volumeButton = new SVGPath();
        volumeButton.setContent("");
        volumeButton.setOnMouseClicked(event -> {
            // TODO: Mute on click
        });
        volumeButton.setOnMouseEntered(event -> {
            // TODO: Popup a volume slider
        });

        HBox hBox = new HBox(currPlayingThumbnail, currPlayingFeed, progressNodes, skipBackButton,
                             playPauseButton, skipForwardButton, volumeButton);

        return hBox;
    }

    // TODO: Refactor createLeftVBox method.
    private VBox createLeftVBox(){
        SVGPath menuButton = new SVGPath();
        menuButton.setContent("");

        TextField searchField = new TextField();
        searchField.setOnKeyTyped(event -> {
            // TODO
        });

        HBox searchBox = new HBox(menuButton, searchField);

        // TODO
        GridPane menuGrid = new GridPane();
        menuGrid.addRow();
        menuGrid.addRow();
        menuGrid.addRow();

        playingList = new ListView();

        ScrollPane playList = new ScrollPane(playingList);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(searchBox, menuGrid, playList);
        return vBox;
    }

}
