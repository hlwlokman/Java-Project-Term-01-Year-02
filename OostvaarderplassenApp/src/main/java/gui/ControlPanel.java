package gui;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControlPanel extends VBox {
    private final Graphs graphs;
    private boolean wolvesIntroduced = false;

    private Label carryingCapacityLabel;
    private Label grassHeightLabel;

    public ControlPanel(Graphs graphs) {
        this.graphs = graphs;
        setSpacing(10);

        // Year Selector
        ComboBox<Integer> yearSelector = new ComboBox<>();
        yearSelector.getItems().addAll(2020, 2021, 2022, 2023, 2024);
        yearSelector.setValue(2024);
        yearSelector.setOnAction(e -> {
            int selectedYear = yearSelector.getValue();
            graphs.updateYear(selectedYear);
        });

        // Wolf Button
        Button wolfButton = new Button("Introduce Wolves");
        wolfButton.setOnAction(e -> {
            wolvesIntroduced = !wolvesIntroduced;
            graphs.recalculateWithWolves(wolvesIntroduced);
            wolfButton.setText(wolvesIntroduced ? "Remove Wolves" : "Introduce Wolves");
        });

        // Carrying Capacity and Grass Height Labels
        carryingCapacityLabel = new Label("Carrying Capacity: N/A");
        grassHeightLabel = new Label("Grass Height: N/A");

        getChildren().addAll(yearSelector, wolfButton, carryingCapacityLabel, grassHeightLabel);
    }

    // Update simulation values dynamically
    public void updateSimulationValues(double carryingCapacity, double grassHeight) {
        carryingCapacityLabel.setText("Carrying Capacity: " + carryingCapacity);
        grassHeightLabel.setText("Grass Height: " + grassHeight + " cm");
    }
}
