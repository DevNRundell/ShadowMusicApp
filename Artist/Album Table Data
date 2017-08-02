package artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DBConnect;
import database.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/* 
 * Once an Album View is clicked on, AlbumTableData loads the tracks stored with that album
 * into the table that is numbered starting at track 1.
 */

public class AlbumTableData {
	
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private ObservableList<AlbumTableModel> albumData = FXCollections.observableArrayList();
	
	//------------------------------------------------------------------------------------------------------------------
	public ObservableList<AlbumTableModel> getData(String albumName, int albumID) {

		try {
						
			connection = DBConnect.getConnection();
			connection.setAutoCommit(false);
			
			String queryAlbumData = "select * from " + albumName.replaceAll("\\s+", "") + " where AlbumID = ? order by trackNum";

			preparedStatement = connection.prepareStatement(queryAlbumData);
			preparedStatement.setInt(1, albumID);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				AlbumTableModel data = new AlbumTableModel();
				
				data.setTrackNum(resultSet.getInt("TrackNum"));
				data.setTrackLength(resultSet.getString("TrackLength"));
				data.setTrackName(resultSet.getString("TrackName"));
				data.setTrackPath(resultSet.getString("TrackPath"));		
							
				String queryAlbumName = "select AlbumName from Album where AlbumID = ?";
				
				Connection connection = DBConnect.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryAlbumName);
				preparedStatement.setInt(1,  albumID);
				ResultSet resultSet = preparedStatement.executeQuery();
				
				if(resultSet.next()) {				
					String albumNameDB = resultSet.getString("AlbumName");
					data.setAlbumName(albumNameDB.substring(albumNameDB.indexOf('_') + 1));
				}
				
				albumData.add(data);
	
			}
			
			connection.commit();
			
		} catch (SQLException e) {
			 e.printStackTrace();
             try {
                 connection.rollback();
             } catch (SQLException e1) {
                 e.printStackTrace();
             }
		} finally {		
			DBUtils.closeConn(connection);
			DBUtils.closeStatement(preparedStatement);
			DBUtils.closeResultSet(resultSet);
		}
		
		return albumData;
	}
}
