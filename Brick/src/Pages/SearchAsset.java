package Pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.Arrays;

public class SearchAsset {
    private final VBox searchAssetInputs;
    private final HBox buttons3;
    private final Stage primaryStage = new Stage();
    private final ListView<String> searchResultsListView;

    public SearchAsset() {
        searchAssetInputs = new VBox(20);
        searchAssetInputs.setStyle("-fx-padding: 10px;");
        searchAssetInputs.setStyle("-fx-background-color: cornflowerblue;"); // changes the color of the background 

        buttons3 = new HBox(20);
        buttons3.setAlignment(Pos.CENTER);

        // Create label and input fields
        Label SearchAssetLbl = new Label("Search Asset(s)");
        SearchAssetLbl.setStyle("-fx-font-size: 24px;"); // Change the font size of assetLbl

        TextField searchField = new TextField();

        searchResultsListView = new ListView<>();
        searchResultsListView.setPrefSize(400, 300);

        // Create buttons and give functionality
        Button homePageButton4 = new Button("Return to Home page");

        homePageButton4.setOnAction(e -> {
            System.out.println("Back to the Homepage");

            // Return to the home page through a button click 
            Stage primaryStage = (Stage) homePageButton4.getScene().getWindow();
            HomeScreen homeScreen = new HomeScreen(primaryStage);
            primaryStage.setScene(new Scene(homeScreen.getRoot(), 800, 800));
            primaryStage.setTitle("Home Page");
        });

        Button searchAssetButton = new Button("Search");

        searchAssetButton.setOnAction(e -> {
            String searchSubstring = searchField.getText().trim();
            performSearch(searchSubstring);
        });

        buttons3.getChildren().addAll(homePageButton4, searchAssetButton);

        searchAssetInputs.getChildren().addAll(SearchAssetLbl, searchField, buttons3, searchResultsListView);
    }

    // Function to perform the search for the asset using a substring
    private void performSearch(String searchSubstring) {
        ObservableList<String> matchingAssets = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader("asset_data.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Assuming the asset name is the first column, change index accordingly if not
                String assetName = parts[0];
                // Check if asset name contains the search substring
                if (assetName.contains(searchSubstring)) {
                    matchingAssets.add(line); // Add entire line (asset information) to matching assets
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display search results in ListView
        searchResultsListView.setItems(matchingAssets);

        // Add edit button to each item
        searchResultsListView.setCellFactory(param -> new AssetListCell());
    }

    
    private void editAsset(String asset) {
        // Check if an item is selected
        if (searchResultsListView.getSelectionModel().getSelectedIndex() == -1) {
            System.out.println("No asset selected for editing.");
            return;
        }

        // Split the asset string into parts
        String[] parts = asset.split(",");

        // Open a new window for editing
        Stage editStage = new Stage();
        VBox editLayout = new VBox(10);
        editLayout.setAlignment(Pos.CENTER);

        // Create text fields for editing each part of the asset
        TextField assetNameField = new TextField();
        TextField assetCategoryField = new TextField();
        TextField assetLocationField = new TextField();
        TextField assetDescriptionField = new TextField();
        TextField assetPurchaseValueField = new TextField();
        TextField purchaseDateField = new TextField();
        TextField warrantyDateField = new TextField();

        // Set text if the part exists
        if (parts.length >= 1) assetNameField.setText(parts[0]);
        if (parts.length >= 2) assetCategoryField.setText(parts[1]);
        if (parts.length >= 3) assetLocationField.setText(parts[2]);
        if (parts.length >= 4) assetDescriptionField.setText(parts[3]);
        if (parts.length >= 5) assetPurchaseValueField.setText(parts[4]);
        if (parts.length >= 6) purchaseDateField.setText(parts[5]);
        if (parts.length >= 7) warrantyDateField.setText(parts[6]);

        editLayout.getChildren().addAll(
            new Label("Asset Name: "), assetNameField,
            new Label("Asset Category: "), assetCategoryField,
            new Label("Asset Location: "), assetLocationField,
            new Label("Asset Description: "), assetDescriptionField,
            new Label("Asset Purchase Value: "), assetPurchaseValueField,
            new Label("Purchase Date: "), purchaseDateField,
            new Label("Warranty Date: "), warrantyDateField
        );

        // Save button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // Get edited information from text fields
            String editedAsset = String.join(",", 
                assetNameField.getText(),
                assetCategoryField.getText(),
                assetLocationField.getText(),
                assetDescriptionField.getText(),
                assetPurchaseValueField.getText(),
                purchaseDateField.getText(),
                warrantyDateField.getText()
            );

            // Replace the old asset with the edited one in the ListView
            int selectedIndex = searchResultsListView.getSelectionModel().getSelectedIndex();
            searchResultsListView.getItems().set(selectedIndex, editedAsset);

            // Update the asset in the file
            updateAsset(asset, editedAsset);

            // Close the edit window
            editStage.close();
        });

        editLayout.getChildren().add(saveButton);

        // Set up the scene and show the stage
        Scene editScene = new Scene(editLayout, 600, 600);
        editStage.setScene(editScene);
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.showAndWait();
    }

    // function to delete asset from file
    private void deleteAsset(String asset) {
        try {
            File inputFile = new File("asset_data.csv");
            File tempFile = new File("temp.csv");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.equals(asset)) {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }
            writer.close();
            reader.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // function to update the asset in file
    private void updateAsset(String oldAsset, String newAsset) {
        try {
            File inputFile = new File("asset_data.csv");
            File tempFile = new File("temp.csv");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(oldAsset)) {
                    writer.write(newAsset + System.getProperty("line.separator"));
                } else {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }
            writer.close();
            reader.close();

            // Delete original file
            if (!inputFile.delete()) {
                throw new IOException("Could not delete original file");
            }

            // Rename temp file to original file
            if (!tempFile.renameTo(inputFile)) {
                throw new IOException("Could not rename temp file to original file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Return the VBox/stage
    public Parent getRoot() {
        return searchAssetInputs;
    }

    // ListCell with delete and edit buttons
    private class AssetListCell extends javafx.scene.control.ListCell<String> {
        private final Button deleteButton = new Button("Delete");
        private final Button editButton = new Button("Edit");

        public AssetListCell() {
            deleteButton.setOnAction(event -> {
                String asset = getItem();
                getListView().getItems().remove(asset);
                deleteAsset(asset);
            });

            editButton.setOnAction(event -> {
                String asset = getItem();
                editAsset(asset);
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(item);
            if (empty) {
                setGraphic(null);
            } else {
                HBox buttons = new HBox(10);
                buttons.getChildren().addAll(editButton, deleteButton);
                setGraphic(buttons);
            }
        }
    }
}
