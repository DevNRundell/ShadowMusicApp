package controller;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import artist.AlbumData;
import artist.AlbumTableModel;
import artist.ArtistData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import music.MusicPlayer;

public class MainViewController implements Initializable {

	private Stage primaryStage;
    @FXML
    private Button reverseButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button forwardButton;
    @FXML
    private Slider durationSlider;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Button searchButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private Button searchBoxButton;
    @FXML
    private ProgressBar trackDurationPB;
    @FXML
    private ProgressBar volumePB;
    @FXML
    private ImageView volumeImageView;
    @FXML
	private AnchorPane musicContentAnchorPane;
    @FXML
    private ImageView artistImageView;
    @FXML
    private Label artistNameLabel;
    @FXML
    private Label currentSongLabel;
    @FXML
    private TableView<AlbumTableModel> queueTable;
    @FXML
    private TableColumn<AlbumTableModel, String> queueArtistCol;
    @FXML
    private TableColumn<AlbumTableModel, String> queueAlbumCol;
    @FXML
    private TableColumn<AlbumTableModel, Integer> queueTrackCol;
    @FXML
    private TableColumn<AlbumTableModel, String> queueTrackLengthCol;
    @FXML
    private TableView<AlbumTableModel> albumTable;
    @FXML
    private TableColumn<AlbumTableModel, Integer> trackNumberCol;
    @FXML
    private TableColumn<AlbumTableModel, String> trackNameCol;
    @FXML
    private TableColumn<AlbumTableModel, String> durationCol;
    @FXML
    private ScrollPane albumScrollPane;
    @FXML
    private TextField searchTF;
    @FXML
    private TextArea artistBioTA;
    @FXML
    private Hyperlink queueHL;
    @FXML 
    private Hyperlink removeHL;
    @FXML
    private Hyperlink clearHL;
	private MusicPlayer player = MusicPlayer.getInstance();
	private int currentTrackIndex  = 0;
	private boolean isQueuedTrack = false;
    
    //------------------------------------------------------------------------------------------------------------------
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		initActions();
		initTableColumns();
		
		UIUpdateController.setAlbumTableView(albumTable);
		UIUpdateController.setAlbumScrollPane(albumScrollPane);
		UIUpdateController.setDurationPB(trackDurationPB);
		UIUpdateController.setDurationSlider(durationSlider);
		UIUpdateController.setVolumePB(volumePB);
		UIUpdateController.setVolumeSlider(volumeSlider);
		UIUpdateController.setVolumeImageView(volumeImageView);
		
		volumeSlider.setValue(25);
		volumePB.setProgress(volumeSlider.getValue() / 100);
		player.initVolume();
		
	}
	
	//------------------------------------------------------------------------------------------------------------------
	private void initActions() {
		
		searchButton.setOnAction(event -> searchTrack());
		
		playButton.setOnAction(event -> {
			
			if(!player.isMediaPlayerNull()) {
				
				if(player.getStatus() == Status.PAUSED || player.getStatus() == Status.STOPPED) {
					player.playTrack();
				} 
			}
		});
		
		stopButton.setOnAction(event -> player.stopTrack());
		pauseButton.setOnAction(event -> player.pauseTrack());	
		minimizeButton.setOnAction(event -> primaryStage.setIconified(true));
		exitButton.setOnAction(event -> Platform.exit());
		
		forwardButton.setOnAction(event -> {
			
			if(isQueuedTrack) {
				nextTrack(queueTable);
			} else if(!isQueuedTrack) {
				nextTrack(albumTable);
			}

		});
		
		reverseButton.setOnAction(event -> {		
			
			if(isQueuedTrack) {
				previousTrack(queueTable);
			} else if(!isQueuedTrack) {
				previousTrack(albumTable);
			}	

		});	
		
		searchTF.setOnKeyPressed(event -> {
			
			if(event.getCode() == KeyCode.ENTER) {
				
				if(!searchTF.getText().isEmpty()) {
					getArtistData(searchTF.getText().trim());
					currentTrackIndex = 0;
				}
			}
		});
		
		searchBoxButton.setOnAction(event -> {
			if(!searchTF.getText().isEmpty()) {
				getArtistData(searchTF.getText().trim());
				currentTrackIndex = 0;
			}
		});
		
		albumTable.setOnMouseClicked(event -> {
			
			if(event.getClickCount() == 2) {
				getTableTrack(albumTable);
				isQueuedTrack = false;
			}
		});
		
		trackDurationPB.progressProperty().addListener((observable, oldValue, newValue) -> {
			
			if(newValue.doubleValue() > .999) {	
								
				if(isQueuedTrack) {		
					nextQueuedTrack();	
				} else if(!isQueuedTrack) {
					nextTrack(albumTable);
				}
		
			}
		});
		
		queueHL.setOnAction(event -> {
			
			AlbumTableModel queueTrack = albumTable.getSelectionModel().getSelectedItem();
			
			if(queueTrack != null) {
						
				queueTable.getItems().add(new AlbumTableModel(
						queueTable.getItems().size(), 
						queueTrack.getTrackName(), 
						queueTrack.getTrackLength(),
						queueTrack.getTrackPath(),
					    queueTrack.getAlbumName(),
					    artistNameLabel.getText()));
				
			}			
		});
		
		removeHL.setOnAction(event -> {
			int remove = queueTable.getSelectionModel().getSelectedIndex();
			queueTable.getItems().remove(remove);
		});
		
		clearHL.setOnAction(event -> queueTable.getItems().clear());
		
		queueTable.setOnMouseClicked(event -> {
			
			if(event.getClickCount() == 2) {
				getTableTrack(queueTable);
				isQueuedTrack = true;
			}
		});	
	}
	
	//------------------------------------------------------------------------------------------------------------------
	private void getTableTrack(TableView<AlbumTableModel> table) {
		
		AlbumTableModel track = table.getSelectionModel().getSelectedItem();
		
		if(track != null) {
			playSelectedTrack(new File(track.getTrackPath()));
			currentTrackIndex = table.getSelectionModel().getSelectedIndex();
		}
		
	}
	
	//------------------------------------------------------------------------------------------------------------------
	private void initTableColumns() {
		
		trackNumberCol.setCellValueFactory(new PropertyValueFactory<>("trackNum"));
		trackNameCol.setCellValueFactory(new PropertyValueFactory<>("trackName"));
		durationCol.setCellValueFactory(new PropertyValueFactory<>("trackLength"));
		
		queueArtistCol.setCellValueFactory(new PropertyValueFactory<>("artistName"));
		queueAlbumCol.setCellValueFactory(new PropertyValueFactory<>("albumName"));
		queueTrackCol.setCellValueFactory(new PropertyValueFactory<>("trackName"));
		queueTrackLengthCol.setCellValueFactory(new PropertyValueFactory<>("trackLength"));

	}
	
	//------------------------------------------------------------------------------------------------------------------
	private void getArtistData(String artistName) {
		
		albumTable.getItems().clear();
		artistNameLabel.setText("");
		artistBioTA.setText("");
		albumScrollPane.setContent(null);
		
		ArtistData data = new ArtistData();
		data.RetrieveData(artistName);
		
		File artistImage = new File(data.getArtistPhotoPath());
		
		try {
			
			artistImageView.setImage(new Image(artistImage.toURI().toURL().toString()));
			artistNameLabel.setText(data.getArtistName());
			artistBioTA.setText(data.getArtistBio());
			
			AlbumData albumData = new AlbumData();
			albumData.RetrieveData(data.getArtistID());
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------
	private void searchTrack() {
		
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Audio Files(.mp3)", "*.mp3", "*.m4a");
		FileChooser songChooser = new FileChooser();
		songChooser.getExtensionFilters().add(filter);
		
		File searchedSong = songChooser.showOpenDialog(null);
		
		if(searchedSong != null) {
			playSelectedTrack(searchedSong);
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------
	private void playSelectedTrack(File song) {
				
		String songTitle = song.getName().toString();
		
		player.setMediaURL(song.toURI().toString());
		currentSongLabel.setText(songTitle.substring(0, songTitle.indexOf('.')));
		player.playTrack();
		
	}
	
	//------------------------------------------------------------------------------------------------------------------
	//
	private void nextQueuedTrack() {
		
		if(queueTable.getItems().size() > 1) {
		
			if(currentTrackIndex == (queueTable.getItems().size() - 1)) {
				
				//If the track is the last one in the table it removes recently ended song
				//and travels in reverse up the table from end to beginning
				int tempIndex = currentTrackIndex;
				currentTrackIndex--;
				queueTable.getItems().remove(tempIndex);
				
				playSelectedTrack(new File(queueTable.getItems().get(currentTrackIndex).getTrackPath()));	
				
			} else {
				queueTable.getItems().remove(currentTrackIndex);
				playSelectedTrack(new File(queueTable.getItems().get(currentTrackIndex).getTrackPath()));						
				currentTrackIndex++;
			}

		} else {
			queueTable.getItems().clear();
			currentSongLabel.setText("");
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------
	//Next track is selected when forward button is pressed
	private void nextTrack(TableView<AlbumTableModel> tableView) {
				
			currentTrackIndex++;
			
			if(currentTrackIndex  >= tableView.getItems().size()) {
				currentTrackIndex = 0;
			}
			
			if(!tableView.getItems().isEmpty()) {
				playSelectedTrack(new File(tableView.getItems().get(currentTrackIndex).getTrackPath()));	
			}		
		}
	
	//------------------------------------------------------------------------------------------------------------------
	private void previousTrack(TableView<AlbumTableModel> tableView) {
		
		//If track time played is less than 5 seconds go to previous song in the table				
		if(player.getCurrentTime() < 5) {
			
			currentTrackIndex--;
		
			if(currentTrackIndex < 0) {
				currentTrackIndex = tableView.getItems().size() - 1;
			}
			
			if(!tableView.getItems().isEmpty()) {
				playSelectedTrack(new File(tableView.getItems().get(currentTrackIndex).getTrackPath()));
			}
			
		} else {
			player.restartTrack();
		}	
	}
	
	//-------------------------------------------------------------------------------------------------------------------
	public void setPrimaryStage(Stage stage) {this.primaryStage = stage;}

}
