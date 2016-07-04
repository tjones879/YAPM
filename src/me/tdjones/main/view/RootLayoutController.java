package me.tdjones.main.view;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextAlignment;
import me.tdjones.main.model.Episode;
import me.tdjones.main.model.Feed;
import me.tdjones.main.util.MediaPlayerUtil;
import me.tdjones.main.util.TimeUtil;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class RootLayoutController {

    private final BorderPane rootBorderPane;
    private ImageView currPlayThumbnail;
    private Label currPlayTitle;
    private Label currPlayTime, maxPlayTime;
    private GridPane playingList;
    private TilePane feedTilePane;
    private ProgressBar progressBar;

    private ImageView skipBackButton, skipForwardButton, playPauseButton, volumeButton;

    private static final Image skipBackIcon;
    private static final Image playIcon;
    private static final Image pauseIcon;
    private static final Image skipForwardIcon;
    private static final Image speakerIcon;
    private static final Image mutedSpeakerIcon;

    private static final int feedThumbnailWidth = 175;
    private static final int leftVBoxWidth = 175;

    static {
        skipBackIcon = new Image(RootLayoutController.class.getResourceAsStream("../resources/Skip_Back_Icon.png"));
        playIcon = new Image(RootLayoutController.class.getResourceAsStream("../resources/Play_Icon.png"));
        pauseIcon = new Image(RootLayoutController.class.getResourceAsStream("../resources/Pause_Icon.png"));
        skipForwardIcon = new Image(RootLayoutController.class.getResourceAsStream("../resources/Skip_Forward_Icon.png"));
        speakerIcon = new Image(RootLayoutController.class.getResourceAsStream("../resources/Speaker_Icon.png"));
        mutedSpeakerIcon = new Image(RootLayoutController.class.getResourceAsStream("../resources/Mute_Icon.png"));
    }

    public RootLayoutController() {
        rootBorderPane = new BorderPane();
        rootBorderPane.setCenter(createFeedScrollPane());
        rootBorderPane.setBottom(createBottomHBox());
        rootBorderPane.setLeft(createLeftVBox());
    }

    private ScrollPane createFeedScrollPane() {
        ScrollPane feedScrollPane = new ScrollPane();
        feedScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        feedScrollPane.setFitToWidth(true);
        feedScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        feedTilePane = new TilePane();
        feedTilePane.setPadding(new Insets(5, 5, 5, 5));
        feedTilePane.setHgap(5);
        feedTilePane.setVgap(10);
        feedTilePane.setTileAlignment(Pos.CENTER);
        feedTilePane.setAlignment(Pos.CENTER);

        feedScrollPane.setContent(feedTilePane);
        return feedScrollPane;
    }

    // TODO: Refactor createBottomHBox method.
    private HBox createBottomHBox() {
        currPlayThumbnail = createThumbnail(MediaPlayerUtil.getCurrPlaying().getThumbnail().getUrl(), 40);

        currPlayTitle = new Label(MediaPlayerUtil.getCurrPlaying().getTitle());
        currPlayTitle.setWrapText(true);
        currPlayTitle.setPrefWidth(leftVBoxWidth - 52.5);

        AnchorPane progressNodes = new AnchorPane();
        // TODO: Remove hard-coded margins
        progressBar = new ProgressBar();
        progressBar.setProgress(.5);
        progressBar.setOnMouseClicked(event -> handleProgressBarClick(event));

        currPlayTime = new Label("0:00");
        maxPlayTime = new Label("0:00");
        progressNodes.getChildren().addAll(progressBar, currPlayTime, maxPlayTime);

        progressNodes.setTopAnchor(progressBar, 2.0);
        progressNodes.setLeftAnchor(progressBar, 2.0);
        progressNodes.setRightAnchor(progressBar, 2.0);

        progressNodes.setBottomAnchor(currPlayTime, 2.0);
        progressNodes.setLeftAnchor(currPlayTime, 2.0);

        progressNodes.setBottomAnchor(maxPlayTime, 2.0);
        progressNodes.setRightAnchor(maxPlayTime, 2.0);

        skipBackButton = createIcon(skipBackIcon);
        skipBackButton.setOnMouseClicked(event -> handleSkipBackButton());

        playPauseButton = createIcon(playIcon);
        playPauseButton.setOnMouseClicked(event -> handlePlayPauseButton());

        skipForwardButton = createIcon(skipForwardIcon);
        skipForwardButton.setOnMouseClicked(event -> handleSkipForwardButton());

        volumeButton = createIcon(speakerIcon);
        volumeButton.setOnMouseClicked(event -> handleVolumeButtonClick());
        volumeButton.setOnMouseEntered(event -> handleVolumeButtonEnter());

        HBox hBox = new HBox(currPlayThumbnail, currPlayTitle, progressNodes, skipBackButton, playPauseButton,
                             skipForwardButton, volumeButton);
        hBox.setHgrow(progressNodes, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(6);
        hBox.setPadding(new Insets(2, 2, 2, 1.5));

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
        searchBox.setHgrow(searchField, Priority.ALWAYS);
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
        vBox.setPrefWidth(leftVBoxWidth);
        vBox.setFillWidth(true);
        return vBox;
    }

    /**
     * @param playingEpisodes List of episode objects.
     */
    public void updatePlayingList(List<Episode> playingEpisodes) {
        int i = 0;
        for (Episode episode : playingEpisodes) {
            ImageView playingListThumbnail = createThumbnail(episode.getThumbnail().getUrl(), 50);
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

            ImageView feedThumbnail = createThumbnail(feed.getThumbnail().getUrl(), feedThumbnailWidth);

            Label feedTitle = new Label(feed.getTitle());
            feedTitle.setMaxWidth(175);
            feedTitle.setWrapText(true);
            feedTitle.setAlignment(Pos.CENTER);
            feedTitle.setTextAlignment(TextAlignment.CENTER);

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
            playPauseButton.setImage(pauseIcon);
        } else {
            MediaPlayerUtil.pause();
            playPauseButton.setImage(playIcon);
        }
    }

    private void handleSkipForwardButton() {
        MediaPlayerUtil.skipForward();
    }

    private void handleVolumeButtonClick() {
        if (MediaPlayerUtil.toggleMute()) {
            volumeButton.setImage(speakerIcon);
        } else {
            volumeButton.setImage(mutedSpeakerIcon);
        }
    }

    private void handleVolumeButtonEnter() {
        // TODO: Popup a volume slider
    }

    private void handleProgressBarClick(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        Bounds bounds = progressBar.getLayoutBounds();
        double seekTime = ((mouseX - bounds.getMinX()) / bounds.getMaxX()) * 1000 * MediaPlayerUtil.getCurrPlaying().getLength();
        MediaPlayerUtil.seek(seekTime);
    }

    public void updateEpisodeNodes(){
        Episode episode = MediaPlayerUtil.getCurrPlaying();

        currPlayThumbnail = createThumbnail(episode.getThumbnail().getUrl(), 50);
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

    public BorderPane getRootBorderPane(){
        return rootBorderPane;
    }

    private ImageView createIcon(Image image){
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        imageView.setSmooth(true);
        return imageView;
    }
}
