package artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DBConnect;
import database.DBUtils;
import errors.ErrorDisplay;

 /*
  * Gets the artists name, ID, photo path and bio to update the main display
  */

public class ArtistData {
	
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private int artistID;
	private String artistName;
	private String artistPhotoPath;
	private String artistBio;

	//------------------------------------------------------------------------------------------------------------------
	public void RetrieveData(String artistName) {
		
		String queryData = "select * from Artist where ArtistName = ?";

		try {
			
			connection = DBConnect.getConnection();
			preparedStatement = connection.prepareStatement(queryData);
			preparedStatement.setString(1, artistName);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				setArtistID(resultSet.getInt("ArtistID"));
				setArtistName(resultSet.getString("ArtistName"));
				setArtistPhotoPath(resultSet.getString("ArtistPhotoPath"));
				setArtistBio(resultSet.getString("ArtistBio"));
				
			} else {
				ErrorDisplay.artistUnknown();					
			}
			
			DBUtils.closeConn(connection);
			DBUtils.closeStatement(preparedStatement);
			DBUtils.closeResultSet(resultSet);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	public void setArtistID(int artistID) {
		this.artistID = artistID;
	}	
	public int getArtistID() {
		return artistID;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getArtistPhotoPath() {
		return artistPhotoPath;
	}
	public void setArtistPhotoPath(String artistPhotoPath) {
		this.artistPhotoPath = artistPhotoPath;
	}
	public String getArtistBio() {
		return artistBio;
	}
	public void setArtistBio(String artistBio) {
		this.artistBio = artistBio;
	}
}
