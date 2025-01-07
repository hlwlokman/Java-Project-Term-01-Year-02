package gui;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControlPanel extends VBox {
    private final Graphs graphs;
    private boolean wolvesIntroduced = false;

    private Label wolfStatusLabel;

    // In the constructor
    public ControlPanel(Graphs graphs) {
        this.graphs = graphs;
        setSpacing(10);
    
        // Year Selector
        ComboBox<Integer> yearSelector = new ComboBox<>();
        for (int year = 1990; year <= 2030; year++) {
            yearSelector.getItems().add(year);
        }
        yearSelector.setValue(2024);
        yearSelector.setOnAction(e -> {
            int selectedYear = yearSelector.getValue();
            graphs.updateYear(selectedYear); // Update the graphs with the new year
        });
    
        // Wolf Button and Status Label
        Button wolfButton = new Button("Introduce Wolves");
        wolfStatusLabel = new Label(); // Initialize the label
        wolfButton.setOnAction(e -> {
            int selectedYear = yearSelector.getValue(); // Get the currently selected year
            wolvesIntroduced = !wolvesIntroduced;
            graphs.recalculateWithWolves(selectedYear, wolvesIntroduced);
            wolfButton.setText(wolvesIntroduced ? "Remove Wolves" : "Introduce Wolves");
            wolfStatusLabel.setText(wolvesIntroduced
                    ? "Wolves are introduced in " + selectedYear
                    : "");
        });
    
        // Add controls and labels to the layout
        getChildren().addAll(
            yearSelector, 
            wolfButton, 
            wolfStatusLabel, 
            graphs.getCarryingCapacityLabel(), 
            graphs.getGrassHeightLabel()
        );
    }
}
