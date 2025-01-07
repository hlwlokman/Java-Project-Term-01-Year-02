package gui;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private Map<Integer, Double> grassHeightData; // Map to store grass height data by year
    public Label carryingCapacityLabel;
    public Label grassHeightLabel;

    public Graphs(EnvironmentalFactors factors) {
        this.factors = factors;

        // Load grass height data from JSON
        loadGrassHeightData();

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

        // Set up initial chart and labels
        updateChart(2024, false); // Initialize with default year and no wolves

        this.setCenter(barChart); // Set chart as center
        this.setBottom(new VBox(carryingCapacityLabel, grassHeightLabel)); // Set labels at the bottom
    }

    private void loadGrassHeightData() {
        grassHeightData = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Use ClassLoader to load the file from the resources directory
            ClassLoader classLoader = getClass().getClassLoader();
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
        updateChart(year, false); // Pass false if wolves are not introduced by default
    }

    public void recalculateWithWolves(int year, boolean wolves) {
        updateChart(year, wolves); // Recalculate with or without wolves based on user input
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

        // Fetch grass height for the year
        Double grassHeight = grassHeightData.getOrDefault(year, null);

        // Calculate carrying capacity if grass height is available
        int carryingCapacity = (grassHeight != null)
                ? factors.getCarryingCapacity(10, 0.5, grassHeight)
                : 0;

        // Update labels
        carryingCapacityLabel.setText("Carrying Capacity: " + (grassHeight != null ? carryingCapacity : "N/A"));
        grassHeightLabel.setText("Grass Height: " + (grassHeight != null ? grassHeight + " cm" : "N/A"));
    }

    public Label getCarryingCapacityLabel() {
        return carryingCapacityLabel;
    }

    public Label getGrassHeightLabel() {
        return grassHeightLabel;
    }
}
