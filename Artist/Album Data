package artist;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import controller.UIUpdateController;
import database.DBConnect;
import database.DBUtils;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

/* 
 * Gets artist album information from the database.
 * Creates a new Album View for each album found to be displayed on the main 
 * window. Controls x and y locations to ensure correct alignment of the album views.
 */

public class AlbumData {
	
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private int layoutX = 0;
	private int layoutY = 0;
	private ScrollPane albumScrollPane = UIUpdateController.getAlbumScrollPane();

	//------------------------------------------------------------------------------------------------------------------
	public void RetrieveData(int artistID) {
		
		Pane albumContentPane = new Pane();
		albumContentPane.setStyle("-fx-background-color: #4d4f4f");
		albumContentPane.setPrefSize(albumScrollPane.getPrefWidth(), albumScrollPane.getPrefHeight());

		String queryData = "select * from Album where ArtistID = ? order by ReleaseDate";
		
		try {
			
			connection = DBConnect.getConnection();
			preparedStatement = connection.prepareStatement(queryData);
			preparedStatement.setInt(1, artistID);
			resultSet = preparedStatement.executeQuery();
		
			while(resultSet.next()) {

				AlbumView albumView = new AlbumView();
				albumView.setAlbumID(resultSet.getInt("AlbumID"));
				albumView.setAlbumName(resultSet.getString("AlbumName"));
				albumView.setAmtOfTracks(resultSet.getInt("AmtOfTracks"));
				albumView.setReleaseDate(resultSet.getDate("ReleaseDate"));
				
				File albumImage = new File(resultSet.getString("AlbumPhotoPath"));
				
				try {
					albumView.setAlbumPhotoPath(albumImage.toURI().toURL().toString());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				
				if(layoutX > albumContentPane.getPrefWidth() - 200) {
					layoutX = 0;
					layoutY += 270;
				}
				
				albumContentPane.getChildren().add(albumView.createAlbumView(layoutX, layoutY));
				
				layoutX += 230;
			}
			
			albumScrollPane.setContent(albumContentPane);
			
			DBUtils.closeConn(connection);
			DBUtils.closeStatement(preparedStatement);
			DBUtils.closeResultSet(resultSet);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
