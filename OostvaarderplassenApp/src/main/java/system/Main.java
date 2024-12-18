package system;

import gui.ControlPanel;
import gui.Graphs;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, lightgreen, lightblue);");

        // Add custom logo to the bottom-right corner
        addLogo(root);

        // Create the custom menu bar (optional - can replace with `SimulationMenuBar`)
        gui.SimulationMenuBar menuBar = new gui.SimulationMenuBar();
        root.setTop(menuBar);

        // Create the graphs and control panel
        Graphs graphs = new Graphs();
        ControlPanel controlPanel = new ControlPanel(graphs);

        // Place components in the layout
        root.setCenter(graphs);  // Place graphs in the center
        root.setRight(controlPanel);  // Place control panel on the right

        // Set up the scene
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("Ecological System Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to add logo to the bottom-right corner
    private void addLogo(BorderPane root) {
        Image logoImage = new Image(getClass().getResourceAsStream("/images/Logo.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(100);
        logoImageView.setFitHeight(100);

        AnchorPane bottomRightAnchor = new AnchorPane();
        bottomRightAnchor.getChildren().add(logoImageView);

        AnchorPane.setBottomAnchor(logoImageView, 10.0);
        AnchorPane.setRightAnchor(logoImageView, 10.0);

        root.setBottom(bottomRightAnchor);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
