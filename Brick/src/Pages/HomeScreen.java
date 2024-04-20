package Pages;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class HomeScreen {

	// creating some variables 
	private final VBox root;
	private final HBox hbox;
	private final Stage primaryStage;
	
	public HomeScreen(Stage primaryStage) {
		
		// create stage and also instantiate variables, HBox and VBox
		this.primaryStage = primaryStage; // assigning the passed stage 
		hbox = new HBox(20);
		root = new VBox(20);
		root.setStyle("-fx-padding: 10px;");
		root.setAlignment(Pos.CENTER); // Align the VBox to the center
	
		root.setStyle("-fx-background-color: cornflowerblue;"); // changes the color of the background 
		
		// Create the home page label and button 
		Label MainLabel = new Label("Welcome to Brick by Brick");
		MainLabel.setStyle("-fx-font-size: 24px;"); // Increase the font size of the MainLabel
		Button createAssetCategoryButton = new Button("New Asset Category");
		Button createAssetLocationButton = new Button("New Asset Location");
		Button createAssetButton = new Button("New Asset");
		Button searchAssetButton = new Button("Search for Asset");
		
		// add the button to the HBox and align the HBox to the center
		hbox.getChildren().addAll(createAssetCategoryButton, createAssetLocationButton, createAssetButton, searchAssetButton);
		hbox.setAlignment(Pos.CENTER);
		root.getChildren().addAll(MainLabel, hbox); // add them all to the VBox
		
		
		// Give functionality to the createAssetCategoryButton
		createAssetCategoryButton.setOnAction(e -> {
			System.out.println("Welcome to the Create New Asset Category Page"); // test print 
			
			CreateCategory createAsset = new CreateCategory(); // create instance of CreateAsset class
			primaryStage.setScene(new Scene(createAsset.getRoot(), 800, 800));
			primaryStage.setTitle("Create New Asset Category");
		});
		
		// Give functionality to the createAssetLocationButton
		createAssetLocationButton.setOnAction(e -> {
			System.out.println("Welcome to the Create New Asset Location Page"); // test print 
			
			CreateLocation createLocation = new CreateLocation(); // create instance of CreateLocation class
			primaryStage.setScene(new Scene(createLocation.getRoot(), 800, 800));
			primaryStage.setTitle("Create New Asset Location");
		});
		
		// Give functionality to the createAssetButton
		createAssetButton.setOnAction(e -> {
			System.out.println("Welcome to the Create New Asset Page");
			
			CreateAsset createAsset = new CreateAsset(); // create instance of CreateAsset class
			primaryStage.setScene(new Scene(createAsset.getRoot(), 800, 800));
			primaryStage.setTitle("Create New Asset");
		});
	
		// Give functionality to the searchAssetButton
		searchAssetButton.setOnAction(e -> {
			System.out.println("Welcome to the Search Asset Page");
			
			SearchAsset searchAsset = new SearchAsset();
			primaryStage.setScene(new Scene(searchAsset.getRoot(), 800, 800)); // create instance of searchAsset class
			primaryStage.setTitle("Search Asset(s)");
		});
		
	}
	
	// function to return the VBox/stage 
	public Parent getRoot() {
        return root;
    }
	
}