package me.tdjones.main.view;

import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import me.tdjones.main.model.Episode;
import me.tdjones.main.model.Feed;
import me.tdjones.main.util.MediaPlayerUtil;
import me.tdjones.main.util.TimeUtil;

import java.util.List;

/**
 * Created by Tyler on 7/1/2016.
 */
public class RootLayoutController {

    private final BorderPane rootBorderPane;
    private ImageView currPlayThumbnail;
    private Label currPlayTitle;
    private Label currPlayTime, maxPlayTime;
    private SVGPath skipBackButton, playPauseButton, skipForwardButton, volumeButton;
    private GridPane playingList;
    private TilePane feedTilePane;
    private ProgressBar progressBar;
    private String unmutedSVG = "";
    private String mutedSVG = "";
    private String pauseSVG = "M 11,10 L 17,10 17,30 11,30 M 20,10 L 26,10 26,30 20,30";
    private String playSVG = "M 10,10 L 10,30 L 30,20 z";

    public RootLayoutController() {

        rootBorderPane = new BorderPane();
        rootBorderPane.setCenter(createFeedScrollPane());
        rootBorderPane.setBottom(createBottomHBox());
        rootBorderPane.setLeft(createLeftVBox());

    }

    private ScrollPane createFeedScrollPane() {
        ScrollPane feedScrollPane = new ScrollPane();

        feedTilePane = new TilePane();
        feedScrollPane.setContent(feedTilePane);
        return feedScrollPane;
    }

    // TODO: Refactor createBottomHBox method.
    private HBox createBottomHBox() {
        currPlayThumbnail = createThumbnail(MediaPlayerUtil.getCurrPlaying().getThumbnail(), 50);

        currPlayTitle = new Label(MediaPlayerUtil.getCurrPlaying().getTitle());
        currPlayTitle.setWrapText(true);
        currPlayTitle.setMaxWidth(150);

        AnchorPane progressNodes = new AnchorPane();
        // TODO: Remove hard-coded margins
        progressBar = new ProgressBar();
        progressBar.setOnMouseClicked(event -> handleProgressBarClick(event));

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
        skipBackButton.setOnMouseClicked(event -> handleSkipBackButton());

        playPauseButton = new SVGPath();
        playPauseButton.setContent("");
        playPauseButton.setOnMouseClicked(event -> handlePlayPauseButton());

        skipForwardButton = new SVGPath();
        skipForwardButton.setContent("");
        skipForwardButton.setOnMouseClicked(event -> handleSkipForwardButton());

        volumeButton = new SVGPath();
        volumeButton.setContent(unmutedSVG);
        volumeButton.setOnMouseClicked(event -> handleVolumeButtonClick());
        volumeButton.setOnMouseEntered(event -> handleVolumeButtonEnter());

        HBox hBox = new HBox(currPlayThumbnail, currPlayTitle, progressNodes, skipBackButton,
                             playPauseButton, skipForwardButton, volumeButton);

        return hBox;
    }

    // TODO: Refactor createLeftVBox method.
    private VBox createLeftVBox() {
        SVGPath menuButton = new SVGPath();
        menuButton.setContent("");

        TextField searchField = new TextField();
        searchField.setOnKeyTyped(event -> {
            // TODO
        });

        HBox searchBox = new HBox(menuButton, searchField);

        // TODO
        GridPane menuGrid = new GridPane();
        menuGrid.addRow(0);
        menuGrid.addRow(1);
        menuGrid.addRow(2);

        // TODO: Rename these variables
        playingList = new GridPane();
        ColumnConstraints thumbnailColumn = new ColumnConstraints();
        thumbnailColumn.setPercentWidth(25);
        playingList.getColumnConstraints().add(0, thumbnailColumn);
        ScrollPane playList = new ScrollPane(playingList);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(searchBox, menuGrid, playList);
        return vBox;
    }

    /**
     * @param playingEpisodes List of episode objects.
     */
    public void updatePlayingList(List<Episode> playingEpisodes) {
        int i = 0;
        for (Episode episode : playingEpisodes) {
            ImageView playingListThumbnail = createThumbnail(episode.getThumbnail(), 50);
            Label playingListLabel = new Label(episode.getTitle());
            // TODO: May create incredible amounts of rows, will have to test.
            playingList.addRow(i, playingListThumbnail, playingListLabel);
            i++;
        }
    }

    public void updateFeedTilePane(List<Feed> feedList) {
        for (Feed feed : feedList) {
            VBox vBox = new VBox();
            vBox.setOnMouseClicked(event -> {
                // TODO
            });

            ImageView feedThumbnail = createThumbnail(feed.getThumbnail(), 250);

            Label feedTitle = new Label(feed.getTitle());
            feedTitle.setWrapText(true);

            vBox.getChildren().addAll(feedThumbnail, feedTitle);
            feedTilePane.getChildren().add(vBox);
        }
    }

    private ImageView createThumbnail(String imageUrl, int imageWidth) {
        ImageView thumbnail = new ImageView(imageUrl);
        thumbnail.setPreserveRatio(true);
        thumbnail.setFitWidth(imageWidth);

        return thumbnail;
    }

    private void handleSkipBackButton() {
        MediaPlayerUtil.skipBack();
    }

    private void handlePlayPauseButton() {
        if (MediaPlayerUtil.isPlaying()) {
            MediaPlayerUtil.play();
            playPauseButton.setContent(pauseSVG);
        } else {
            MediaPlayerUtil.pause();
            playPauseButton.setContent(playSVG);
        }
    }

    private void handleSkipForwardButton() {
        MediaPlayerUtil.skipForward();
    }

    private void handleVolumeButtonClick() {
        if (MediaPlayerUtil.toggleMute()) {
            volumeButton.setContent(mutedSVG);
        } else {
            volumeButton.setContent(unmutedSVG);
        }
    }

    private void handleVolumeButtonEnter() {
        // TODO: Popup a volume slider
    }

    private void handleProgressBarClick(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        Bounds bounds = progressBar.getLayoutBounds();
        double seekTime = ((mouseX - bounds.getMinX()) / bounds.getMaxX()) * 1000
                            * MediaPlayerUtil.getCurrPlaying().getLength();
        MediaPlayerUtil.seek(seekTime);
    }

    public void updateEpisodeNodes(){
        Episode episode = MediaPlayerUtil.getCurrPlaying();

        currPlayThumbnail = createThumbnail(episode.getThumbnail(), 50);
        currPlayTitle.setText(episode.getTitle());
        currPlayTime.setText("0:00");
        maxPlayTime.setText(TimeUtil.formatTime(episode.getLength()));
    }

    public void updateProgressBar(){
            int currTime = (int) MediaPlayerUtil.getCurrTime().toSeconds();
            long maxTime = MediaPlayerUtil.getCurrPlaying().getLength();
            progressBar.setProgress(currTime / maxTime);
            currPlayTime.setText(TimeUtil.formatTime(currTime));
    }

    public void updateCurrPlayTime(){
        currPlayTime.setText(TimeUtil.formatTime((int) MediaPlayerUtil.getCurrTime().toSeconds()));
    }
}
