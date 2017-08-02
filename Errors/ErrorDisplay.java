package errors;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/*
 * Error boxes for various errors.
 */

public class ErrorDisplay {
	
	public static void mediaUnavailable() {		
		showErrorDisplay("Media playback unavailable. Please try again later.");
	}

	public static void artistUnknown() {
		showErrorDisplay("Artist is unknown. Try another artist.");
	}
	
	private static void showErrorDisplay(String message) {
		
		Alert errorAlert = new Alert(AlertType.ERROR, message);
		errorAlert.show();
	}
}
