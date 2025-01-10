package gui;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import environment.EnvironmentalFactors;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Graphs extends BorderPane {
    private final LineChart<String, Number> lineChart;
    private final XYChart.Series<String, Number> series;
    private final XYChart.Series<String, Number> secondarySeries; 
    private final EnvironmentalFactors factors;
    private Map<Integer, Double> grassHeightData; // Map to store grass height data by year
    public Label carryingCapacityLabel;
    public Label grassHeightLabel;

    public Graphs(EnvironmentalFactors factors) {
        this.factors = factors;
        // Load grass height data from JSON
        loadGrassHeightData();

        // Axes for the linechart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxisLeft = new NumberAxis();
        NumberAxis yAxisRight = new NumberAxis();  // Right Y axis
        yAxisRight.setLabel("Carrying Capacity");
        xAxis.setLabel("Species");
        yAxisLeft.setLabel("Population");

        // Initialize the linechart
        this.lineChart = new LineChart<>(xAxis, yAxisLeft);
        this.lineChart.setTitle("Ecosystem Population Over the Years");

        this.series = new XYChart.Series<>();
        this.lineChart.getData().add(series);

        this.secondarySeries = new XYChart.Series<>();
        this.lineChart.getData().add(secondarySeries);

        // Labels for carrying capacity and grass height
        this.carryingCapacityLabel = new Label("Carrying Capacity: N/A");
        this.grassHeightLabel = new Label("Grass Height: N/A");

        // Set up initial chart and labels
        this.updateChart(2024, false); // Initialize with default year and no wolves

        this.setCenter(this.lineChart); // Set chart as center
        this.setBottom(new VBox(carryingCapacityLabel, grassHeightLabel)); // Set labels at the bottom
    }

    private void loadGrassHeightData() {
        grassHeightData = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Use ClassLoader to load the file from the resources directory
            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = new File(classLoader.getResource("grass_height.json").getFile());
            
            // Parse the JSON
            JsonNode root = mapper.readTree(file);
            for (JsonNode node : root.get("grass_height")) {
                int year = node.get("Year").asInt();
                double height = node.get("Grass height (cm) on August 1st").asDouble();
                grassHeightData.put(year, height);
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Failed to load grass height data: " + e.getMessage());
        }
    } 

    public void updateYear(int year) {
        this.updateChart(year, false); // Pass false if wolves are not introduced by default
    }

    public void recalculateWithWolves(int year, boolean wolves) {
        this.updateChart(year, wolves); // Recalculate with or without wolves based on user input
    }

    private void updateChart(int year, boolean wolves) {
        // Clear previous data
        this.series.getData().clear();
        this.secondarySeries.getData().clear();

        // Get the population for the selected year and wolves status
        Map<String, Integer> populationData = Map.of(
                "Cattle", factors.getPopulationForSpecies("Cattle", year, wolves),
                "Deer", factors.getPopulationForSpecies("Deer", year, wolves),
                "Horses", factors.getPopulationForSpecies("Horses", year, wolves)
        );

        // Add data to the chart
        this.series.getData().add(new XYChart.Data<>("Cattle", populationData.get("Cattle")));
        this.series.getData().add(new XYChart.Data<>("Deer", populationData.get("Deer")));
        this.series.getData().add(new XYChart.Data<>("Horses", populationData.get("Horses")));

        // Fetch grass height for the year
        Double grassHeight = this.grassHeightData.getOrDefault(year, null);

        // Calculate carrying capacity if grass height is available
        int carryingCapacity = (grassHeight != null)
                ? factors.getCarryingCapacity(10, 0.5, grassHeight)
                : 0;

        this.secondarySeries.getData().add(new XYChart.Data<>("Carrying capacity", carryingCapacity));        
        // Update labels
        this.carryingCapacityLabel.setText("Carrying Capacity: " + (grassHeight != null ? carryingCapacity : "N/A"));
        this.grassHeightLabel.setText("Grass Height: " + (grassHeight != null ? grassHeight + " cm" : "N/A"));
    }

    public Label getCarryingCapacityLabel() {
        return this.carryingCapacityLabel;
    }

    public Label getGrassHeightLabel() {
        return this.grassHeightLabel;
    }
}
