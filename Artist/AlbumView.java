package artist;

import java.sql.Date;

import controller.UIUpdateController;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/* 
 * For each album found in the database, an Album View is created.
 * The Album View displays the album name, photo, release date and track count.
 */

public class AlbumView extends UIUpdateController {
	
	private Pane albumPane;
	private ImageView albumCoverImage;
	private Hyperlink albumNameHL;
	private Label releaseDateLabel;
	private Label amtOfTracksLabel;
	private String albumPhotoPath;
	private String albumName;
	private Date releaseDate;
	private int amtOfTracks;
	private int albumID;

	//------------------------------------------------------------------------------------------------------------------
	public Pane createAlbumView(double layoutX, double layoutY) {
		
		albumPane = new Pane();		
		albumPane.setPrefSize(260, 260);
		albumPane.setLayoutX(layoutX);
		albumPane.setLayoutY(layoutY);
		
		albumCoverImage = new ImageView();
		albumCoverImage.setImage(new Image(albumPhotoPath));
		albumCoverImage.setLayoutX(20);
		albumCoverImage.setLayoutY(30);
		albumCoverImage.setFitHeight(200);
		albumCoverImage.setFitWidth(200);		
		albumCoverImage.setOnMouseClicked(event -> {
			AlbumTableData data = new AlbumTableData();
			getAlbumTableView().setItems(data.getData(albumName, albumID));	
		});
		
		albumNameHL = new Hyperlink(albumName.substring(albumName.indexOf("_") + 1, albumName.length()));
		albumNameHL.setStyle("-fx-text-fill: white");
		albumNameHL.setLayoutX(20);
		albumNameHL.setLayoutY(0);
		albumNameHL.setOnAction(event -> {			
			AlbumTableData data = new AlbumTableData();
			getAlbumTableView().setItems(data.getData(albumName, albumID));		
		});
		
		releaseDateLabel = new Label("Release Date: " + String.valueOf(releaseDate));
		releaseDateLabel.setLayoutX(20);
		releaseDateLabel.setLayoutY(227);
		
		amtOfTracksLabel = new Label("Track Count: " + String.valueOf(amtOfTracks));
		amtOfTracksLabel.setLayoutX(20);
		amtOfTracksLabel.setLayoutY(245);
		
		albumPane.getChildren().addAll(albumCoverImage, albumNameHL, releaseDateLabel, amtOfTracksLabel);
		
		return albumPane;
	}

	//------------------------------------------------------------------------------------------------------------------
	public String getAlbumPhotoPath() {
		return albumPhotoPath;
	}

	public void setAlbumPhotoPath(String albumPhotoPath) {
		this.albumPhotoPath = albumPhotoPath;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getAmtOfTracks() {
		return amtOfTracks;
	}

	public void setAmtOfTracks(int amtOfTracks) {
		this.amtOfTracks = amtOfTracks;
	}

	public int getAlbumID() {
		return albumID;
	}

	public void setAlbumID(int albumID) {
		this.albumID = albumID;
	}

}
