package Pages;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateAsset {
    private final VBox assetInputs;
    private final HBox buttons2;
    private final Stage primaryStage = new Stage();

    public CreateAsset() {
        assetInputs = new VBox(20);
        assetInputs.setStyle("-fx-padding: 10px;");
        assetInputs.setStyle("-fx-background-color: cornflowerblue;"); // changes the color of the background 

        buttons2 = new HBox(20);
        buttons2.setAlignment(Pos.CENTER);

        // Create label and input fields
        Label assetLbl = new Label("Create a New Asset");
        assetLbl.setStyle("-fx-font-size: 24px;"); // Change the font size of assetLbl

        Label assetNameLbl = new Label("Name of Asset *Required*");
        TextField assetName = new TextField();

        Label assetCategoryLabel = new Label("Select the Asset Category *Required*");
        ComboBox<String> assetCategoryDropdown = new ComboBox<>();
        ObservableList<String> categories = readFromFile("category_data.txt");
        assetCategoryDropdown.setItems(categories);

        Label assetLocationLabel = new Label("Select the Asset Location *Required*");
        ComboBox<String> assetLocationDropdown = new ComboBox<>();
        ObservableList<String> locations = readFromFile("location_data.txt");
        assetLocationDropdown.setItems(locations);

        Label purchaseDate = new Label("Select the Date of Purchase");
        DatePicker purchaseDatePicker = new DatePicker();
        
        Label assetDescriptionLabel = new Label("Enter Asset Description");
        TextArea assetDescriptionArea = new TextArea();
        
        Label assetPurchaseValue = new Label("Enter the Purchase Value of Asset");
        TextField assetPurchaseValueText = new TextField();
        
        Label warrantyDate = new Label("Select the Date of Warranty End");
        DatePicker warrantyDatePicker = new DatePicker();
        
        // Create buttons and give functionality
        Button homePageButton3 = new Button("Return to Home page");

        homePageButton3.setOnAction(e -> {
            System.out.println("Back to the Homepage");

            // Return to the home page through button click
            Stage primaryStage = (Stage) homePageButton3.getScene().getWindow();
            HomeScreen homeScreen = new HomeScreen(primaryStage);
            primaryStage.setScene(new Scene(homeScreen.getRoot(), 800, 800));
            primaryStage.setTitle("Home Page");
        });

        Button createAssetButton = new Button("Create Asset");

        // Button functionality to create asset
        createAssetButton.setOnAction(e -> {
            String assetNameData = assetName.getText();
            String assetCategoryData = assetCategoryDropdown.getValue();
            String assetLocationData = assetLocationDropdown.getValue();
            String assetDescriptionData = assetDescriptionArea.getText();
            String assetPurchaseValueData = assetPurchaseValueText.getText();
            String purchaseDateData = purchaseDatePicker.getValue() != null ? purchaseDatePicker.getValue().toString() : "";
            String warrantyDateData = warrantyDatePicker.getValue() != null ? warrantyDatePicker.getValue().toString() : "";

            if (assetNameData.isEmpty() || assetCategoryData == null || assetLocationData == null) {
                // Display error message if required fields are empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all required fields (Asset Name, Asset Category, Asset Location).");
                alert.showAndWait();
            } else {
                // Write data to CSV file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("asset_data.csv", true))) {
                    // Format the data as CSV row
                    String rowData = String.format("%s,%s,%s,%s,%s,%s,%s%n", assetNameData, assetCategoryData, assetLocationData, purchaseDateData, assetDescriptionData, assetPurchaseValueData, warrantyDateData);
                    writer.write(rowData);
                    writer.flush();

                    // Clear input fields after successful write
                    assetName.clear();
                    assetCategoryDropdown.getSelectionModel().clearSelection();
                    assetLocationDropdown.getSelectionModel().clearSelection();
                    assetDescriptionArea.clear();
                    assetPurchaseValueText.clear();
                    purchaseDatePicker.getEditor().clear();
                    warrantyDatePicker.getEditor().clear();

                    // Show success message
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Asset Created Successfully and data has been written to the file.");
                    alert.showAndWait();
                    
                    // Return to the home page after creating asset 
                    Stage primaryStage = (Stage) homePageButton3.getScene().getWindow();
                    HomeScreen homeScreen = new HomeScreen(primaryStage);
                    primaryStage.setScene(new Scene(homeScreen.getRoot(), 800, 800));
                    primaryStage.setTitle("Home Page");
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // Display error message if writing fails
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("An error occurred while writing to the file.");
                    alert.showAndWait();
                }
            }
        });



        buttons2.getChildren().addAll(homePageButton3, createAssetButton);

        assetInputs.getChildren().addAll(assetLbl, assetNameLbl, assetName, assetCategoryLabel, assetCategoryDropdown, assetLocationLabel, assetLocationDropdown, purchaseDate, purchaseDatePicker, assetDescriptionLabel, assetDescriptionArea, assetPurchaseValue, assetPurchaseValueText, warrantyDate, warrantyDatePicker, buttons2);
    }

    // Read from file and return the contents as an ObservableList
    private ObservableList<String> readFromFile(String filename) {
        ObservableList<String> items = FXCollections.observableArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                items.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    // return the VBox/stage
    public Parent getRoot() {
        return assetInputs;
    }
}
