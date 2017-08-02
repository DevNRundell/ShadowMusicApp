package artist;

/*
 * Model for the Album and Queue Table
 */

public class AlbumTableModel {
	
	private int trackNum;
	private String trackName;
	private String trackLength;
	private String trackPath;
	private String albumName;
	private String artistName;
	
    public AlbumTableModel() {}
	
	public AlbumTableModel(int trackNum, String trackName, String trackLength, String trackPath, String albumName, String artistName) {
		
		this.trackNum = trackNum;
		this.trackName = trackName;
		this.trackLength = trackLength;
		this.trackPath = trackPath;
		this.albumName = albumName;
		this.artistName = artistName;
	}

	//------------------------------------------------------------------------------------------------------------------
	public int getTrackNum() {
		return trackNum;
	}
	public void setTrackNum(int trackNum) {
		this.trackNum = trackNum;
	}	
	public String getTrackPath() {
		return trackPath;
	}
	public void setTrackPath(String trackPath) {
		this.trackPath = trackPath;
	}
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	public String getTrackLength() {
		return trackLength;
	}
	public void setTrackLength(String trackLength) {
		this.trackLength = trackLength;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	
}
