package system;

import gui.ControlPanel;
import gui.Graphs;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Ensure the default system menu bar is not added
        root.setTop(null); // This removes any existing system menu bar

        // Set background gradient
        root.setStyle(" -fx-background-color: linear-gradient(from 0% 0% to 100% 100%, lightgreen, lightblue);");

        
        // Load the image
        Image logoImage = new Image(getClass().getResourceAsStream("/images/Logo.png")); // Make sure the path is correct
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(100);  // Set width
        logoImageView.setFitHeight(100);  // Set height

        // Create an AnchorPane to position the image
        AnchorPane bottomRightAnchor = new AnchorPane();
        bottomRightAnchor.getChildren().add(logoImageView);

        // Position the image at the bottom-right corner of the AnchorPane
        AnchorPane.setBottomAnchor(logoImageView, 1.0); // 10 pixels from the bottom
        AnchorPane.setRightAnchor(logoImageView, 1.0);  // 10 pixels from the right

        // Add the AnchorPane to the BorderPane's bottom
        root.setBottom(bottomRightAnchor);

        // Create custom MenuBar
        MenuBar menuBar = new MenuBar();

        // Create File and Edit menus
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");

        // Create MenuItems for File menu
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        fileMenu.getItems().addAll(openItem, saveItem);

        // Create MenuItems for Edit menu
        MenuItem cutItem = new MenuItem("Cut");
        MenuItem copyItem = new MenuItem("Copy");
        editMenu.getItems().addAll(cutItem, copyItem);

        // Add menus to the MenuBar
        menuBar.getMenus().addAll(fileMenu, editMenu);

        // Apply custom style to the MenuBar
        menuBar.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, lightgreen, lightblue); -fx-font-size: 15px; -fx-text-fill: white;");

        // Set custom menu bar to top of the BorderPane
        root.setTop(menuBar);

        // Create an instance of Graphs
        Graphs graphs = new Graphs();
        
        // Optionally, load data from Excel file
        // graphs.loadDataFromExcel("path/to/your/data.xlsx");

        // Add the ControlPanel with the Graphs instance
        root.setRight(new ControlPanel(graphs));  // Control panel will have access to the graphs instance
        root.setCenter(graphs);  // Place the graph in the center of the window

        // Set up the scene
        Scene scene = new Scene(root, 1000, 600);

        // Set the scene and show the window
        primaryStage.setTitle("Ecological System Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
