package gui;

import java.util.Map;

import environment.EnvironmentalFactors;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Graphs extends BorderPane {
    private final BarChart<String, Number> barChart;
    private final XYChart.Series<String, Number> series;
    private final EnvironmentalFactors factors;
    private Label carryingCapacityLabel;
    private Label grassHeightLabel;

    public Graphs(EnvironmentalFactors factors) {
        this.factors = factors;

        // Axes for the BarChart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Species");
        yAxis.setLabel("Population");

        // Initialize the BarChart
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Ecosystem Population Over the Years");

        series = new XYChart.Series<>();
        barChart.getData().add(series);

        // Labels for carrying capacity and grass height
        carryingCapacityLabel = new Label("Carrying Capacity: N/A");
        grassHeightLabel = new Label("Grass Height: N/A");

        updateChart(2024, false); // Initialize with default year and no wolves

        this.setCenter(barChart); // Set chart as center
        this.setBottom(new VBox(carryingCapacityLabel, grassHeightLabel)); // Set labels at the bottom
    }

    public void updateYear(int year) {
        updateChart(year, false); // Pass false if wolves are not introduced by default
    }

    public void recalculateWithWolves(boolean wolves) {
        updateChart(2024, wolves); // You can adjust this to use a dynamic year from the ComboBox
    }

    private void updateChart(int year, boolean wolves) {
        // Clear previous data
        series.getData().clear();

        // Get the population for the selected year and wolves status
        Map<String, Integer> populationData = Map.of(
                "Cattle", factors.getPopulationForSpecies("Cattle", year, wolves),
                "Deer", factors.getPopulationForSpecies("Deer", year, wolves),
                "Horses", factors.getPopulationForSpecies("Horses", year, wolves)
        );

        // Add data to the chart
        series.getData().add(new XYChart.Data<>("Cattle", populationData.get("Cattle")));
        series.getData().add(new XYChart.Data<>("Deer", populationData.get("Deer")));
        series.getData().add(new XYChart.Data<>("Horses", populationData.get("Horses")));

        // Fetch the grass height and calculate carrying capacity
        double grassHeight = factors.getGrassHeight(year);
        int carryingCapacity = factors.getCarryingCapacity(10, 0.5, grassHeight); // Adjust parameters as needed

        // Update labels
        carryingCapacityLabel.setText("Carrying Capacity: " + carryingCapacity);
        grassHeightLabel.setText("Grass Height: " + grassHeight + " cm");
    }
}
