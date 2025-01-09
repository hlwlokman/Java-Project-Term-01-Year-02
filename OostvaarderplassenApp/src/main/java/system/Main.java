package system;

import environment.EnvironmentalFactors;
import gui.ControlPanel;
import gui.Graphs;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    private EnvironmentalFactors factors;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        factors = new EnvironmentalFactors();

        // UI Layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Graphs
        Graphs graphs = new Graphs(factors);
        root.setCenter(graphs);

        // Control Panel
        ControlPanel controlPanel = new ControlPanel(graphs);
        root.setRight(controlPanel);

        // Scene and Stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Ecosystem Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
