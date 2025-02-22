package application;
	
import Pages.HomeScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// Create an instance of the homeScreen class to make a new scene
			HomeScreen homescreen = new HomeScreen(primaryStage);
			
			primaryStage.setScene(new Scene(homescreen.getRoot(), 800, 800));
			primaryStage.setTitle("Brick by Brick"); // Title of the stage
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}