package music;

import controller.UIUpdateController;
import errors.ErrorDisplay;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

/* MusicPlayer handles interactions with the current media that was selected.
 * Updates the GUI components in the Main Controller class based on track duration,
 * volume and seek.
 */

public class MusicPlayer {
	
	private static MusicPlayer instance;
	private Media media;
	private MediaPlayer mediaPlayer;
	private String mediaURL;
	
	//------------------------------------------------------------------------------------------------------------------
	static {
		instance = new MusicPlayer();
	}
	
	//------------------------------------------------------------------------------------------------------------------
	public void playTrack() {
		
		if(mediaPlayer != null) {
			
			switch (getStatus()) {

				case DISPOSED:
				case STOPPED:
				case PLAYING:
				
					destroyPlayer();
					createPlayer();
					mediaPlayer.play();			
					break;				
					
				case PAUSED:
				
					mediaPlayer.play();				
					break;
					
				default:
					
					destroyPlayer();				
				break;
					
			}
			
		} else {
			
			createPlayer();
			mediaPlayer.play();
		}	
		
		initMusicPlayerSeek();
		initDurationTracking();
		mediaPlayer.setVolume(UIUpdateController.getVolumeSlider().getValue() / 100);
		
	}
	
	//------------------------------------------------------------------------------------------------------------------
	public void stopTrack() {
		
		if(mediaPlayer != null) {
			if(getStatus() == Status.PLAYING) {
				mediaPlayer.stop();
			}
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------
	public void pauseTrack() {
	
		if(mediaPlayer != null) {
			if(getStatus() == Status.PLAYING) {
				mediaPlayer.pause();
			}
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------
	private void createPlayer() {	
		
		try {
			
			media = new Media(mediaURL);
			mediaPlayer = new MediaPlayer(media);	
			
		} catch (MediaException e) {
			e.printStackTrace();
			ErrorDisplay.mediaUnavailable();
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------
	private void destroyPlayer() {	
		if(mediaPlayer != null) {
			mediaPlayer.dispose();
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------
	public Status getStatus() {
		return mediaPlayer.getStatus();
	}
	
	//------------------------------------------------------------------------------------------------------------------
	public boolean isMediaPlayerNull() {
		
		if(mediaPlayer == null) {
			return true;
		}	
		return false;
	}
	
	//------------------------------------------------------------------------------------------------------------------
	public double getCurrentTime() {
		return mediaPlayer.getCurrentTime().toSeconds();
	}
	
	//------------------------------------------------------------------------------------------------------------------
	private void initDurationTracking() {

		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
		
				mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {					
					
					UIUpdateController.getDurationSlider().setValue((newValue.toSeconds() / mediaPlayer.getCycleDuration().toSeconds()) * 100);	
					UIUpdateController.getDurationPB().setProgress(newValue.toSeconds() / mediaPlayer.getCycleDuration().toSeconds());
					
					if(UIUpdateController.getDurationPB().getProgress() > .999) {
						UIUpdateController.getDurationSlider().setValue(0);
						UIUpdateController.getDurationPB().setProgress(0);
					}			
				});
			}
		});
	}
	
	//------------------------------------------------------------------------------------------------------------------
	private void initMusicPlayerSeek() {
				
		UIUpdateController.getDurationSlider().valueProperty().addListener((observable, oldValue, newValue) -> {
		
			if(UIUpdateController.getDurationSlider().isValueChanging() || UIUpdateController.getDurationSlider().isPressed()) {		
				mediaPlayer.seek(mediaPlayer.getCycleDuration().multiply(UIUpdateController.getDurationSlider().getValue() / 100));
				
			}
		});
	}

	//------------------------------------------------------------------------------------------------------------------
	public void initVolume() {

		UIUpdateController.getVolumeSlider().valueProperty().addListener((observable, oldValue, newValue) -> {
			
			if(UIUpdateController.getVolumeSlider().isValueChanging() || UIUpdateController.getVolumeSlider().isPressed()) {
				
				if(mediaPlayer != null) {
					mediaPlayer.setVolume(newValue.doubleValue() / 100);
				}
				
				UIUpdateController.getVolumePB().setProgress(newValue.doubleValue() / 100);
				
				if(newValue.doubleValue() == 0) {
					UIUpdateController.getVolumeImageView().setImage(new Image("\\picture\\mute_volume_icon.png"));
				} else {
					UIUpdateController.getVolumeImageView().setImage(new Image("\\picture\\unmute_volume_icon.png"));
				}
			}	
		});
	}

	//------------------------------------------------------------------------------------------------------------------
	public void restartTrack() {
		mediaPlayer.seek(mediaPlayer.getStartTime());
	}

	//------------------------------------------------------------------------------------------------------------------
	public void setMediaURL(String mediaURL) {
		this.mediaURL = mediaURL;
	}
	
	//------------------------------------------------------------------------------------------------------------------
	public static MusicPlayer getInstance() {
		return instance;
	}
}
