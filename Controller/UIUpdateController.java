package controller;

import artist.AlbumTableModel;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

/*
 * Helper Class
 * Provides other classes with an easy way of updating the main display.
 */

public class UIUpdateController {
	
	private static TableView<AlbumTableModel> albumTableView;
	private static ScrollPane albumScrollPane;
	private static Slider durationSlider;
	private static ProgressBar durationPB;
	private static Slider volumeSlider;
	private static ProgressBar volumePB;
	private static ImageView volumeImageView;
		
	public static ImageView getVolumeImageView() {return volumeImageView;}
	public static void setVolumeImageView(ImageView volumeImageView) {UIUpdateController.volumeImageView = volumeImageView;}
	public static Slider getVolumeSlider() {return volumeSlider;}
	public static void setVolumeSlider(Slider volumeSlider) {UIUpdateController.volumeSlider = volumeSlider;}
	public static ProgressBar getVolumePB() {return volumePB;}
	public static void setVolumePB(ProgressBar volumePB) {	UIUpdateController.volumePB = volumePB;}
	public static Slider getDurationSlider() {return durationSlider;}
	public static void setDurationSlider(Slider durationSlider) {UIUpdateController.durationSlider = durationSlider;}
	public static ProgressBar getDurationPB() {return durationPB;}
	public static void setDurationPB(ProgressBar durationPB) {UIUpdateController.durationPB = durationPB;}
	public static void setAlbumScrollPane(ScrollPane scrollPane) {albumScrollPane = scrollPane;}
	public static ScrollPane getAlbumScrollPane() {return albumScrollPane;}
	public static TableView<AlbumTableModel> getAlbumTableView() {return albumTableView;}
	public static void setAlbumTableView(TableView<AlbumTableModel> tableView) {albumTableView = tableView;}

}
