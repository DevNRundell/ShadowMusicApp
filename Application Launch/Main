package application;

	

import controller.MainViewController;

import javafx.application.Application;

import javafx.event.EventHandler;

import javafx.fxml.FXMLLoader;

import javafx.stage.Stage;

import javafx.stage.StageStyle;

import javafx.scene.Scene;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;





public class Main extends Application {



	public static void main(String[] args) {

		launch(args);

	}

	

	@Override

	public void start(Stage primaryStage) {

		

		try {

			

			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml\\Main_View.fxml"));

			Pane root = loader.load();

			Scene scene = new Scene(root);			

			primaryStage.initStyle(StageStyle.TRANSPARENT); 

			primaryStage.setScene(scene);

			primaryStage.setTitle("Shadow Music");

			primaryStage.setResizable(false);

			primaryStage.show();

			

			MainViewController controller= loader.getController();

			controller.setPrimaryStage(primaryStage);

			

			root.setOnMousePressed(new EventHandler<MouseEvent>() {

				

				  @Override public void handle(MouseEvent mouseEvent) {

					  

					double dragDeltaX;

					double dragDeltaY;

					  

					 //records a delta distance for the drag and drop operation.

					 dragDeltaX = primaryStage.getX() - mouseEvent.getScreenX();

					 dragDeltaY = primaryStage.getY() - mouseEvent.getScreenY();

					 

					 root.setOnMouseDragged(new EventHandler<MouseEvent>() {

							

						  @Override public void handle(MouseEvent mouseEvent) {

							  

							  primaryStage.setX(mouseEvent.getScreenX() + dragDeltaX);

							  primaryStage.setY(mouseEvent.getScreenY() + dragDeltaY);

						  }

					});

				  }

				});

		

		} catch(Exception e) {

				e.printStackTrace();

		}

	}

}
