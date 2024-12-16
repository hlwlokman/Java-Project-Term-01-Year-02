package gui;

import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Graphs extends StackPane {
    private BarChart<String, Number> barChart;

    private XYChart.Series<String, Number> cattleSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> deerSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> horsesSeries = new XYChart.Series<>();

    public Graphs() {
        // Create and style axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Year");
        xAxis.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill:rgb(18, 37, 56);");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Population");
        yAxis.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: rgb(18, 37, 56);");

        // Create the BarChart using the axes
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Ecosystem Population Over the Years");
        barChart.setStyle("-fx-background-color: linear-gradient(to bottom, #f3f4f6, #dceefc);-fx-background-radius: 10");

        // Add series to the chart
        cattleSeries.setName("Cattle");
        deerSeries.setName("Deer");
        horsesSeries.setName("Horses");
        

        barChart.getData().addAll(cattleSeries, deerSeries, horsesSeries);

        // Customize the bars and legend
        customizeBars();
        customizeLegend();

        // Add chart to the layout
        getChildren().add(barChart);
    }


    // Helper method to create data points with tooltips and effects
    private XYChart.Data<String, Number> createDataWithTooltip(String x, double y) {
        XYChart.Data<String, Number> data = new XYChart.Data<>(x, y);
        Tooltip tooltip = new Tooltip("Year: " + x + "\nPopulation: " + y);
        tooltip.setShowDelay(Duration.millis(100));
        tooltip.setHideDelay(Duration.millis(200));
        Tooltip.install(data.getNode(), tooltip);

        // Add shadow and rounded corners to the bars
        data.nodeProperty().addListener((obs, oldNode, newNode) -> {
            if (newNode != null) {
                newNode.setEffect(new DropShadow(10, Color.BLACK));
                if (newNode instanceof Rectangle) {
                    ((Rectangle) newNode).setArcWidth(15);
                    ((Rectangle) newNode).setArcHeight(15);
                    ((Rectangle) newNode).setStyle("-fx-fill: linear-gradient(to top, #4caf50, #81c784);");
                }
            }
        });

        return data;
    }

    // Customize the legend appearance
    private void customizeLegend() {
        barChart.lookupAll(".chart-legend-item").forEach(node -> {
            if (node instanceof StackPane) {
                StackPane stackPane = (StackPane) node;
                stackPane.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-radius: 10;");
            }
        });
    }

    // Customize the bars for a modern look
    private void customizeBars() {
        barChart.getData().forEach(series -> {
            series.getData().forEach(data -> {
                Node bar = data.getNode();
                if (bar != null && bar instanceof Rectangle) {
                    Rectangle rectangle = (Rectangle) bar;
                    rectangle.setArcWidth(10);
                    rectangle.setArcHeight(10);
                    rectangle.setStyle("-fx-fill: linear-gradient(to top, #3b5998, #8b9dc3);");
                }
            });
        });
    }

    // Method to update the graph’s data dynamically
    public void updateData(int deerValue, int horsesValue, int cattleValue, String yearLabel) {
        cattleSeries.getData().add(createDataWithTooltip(yearLabel, cattleValue));
        deerSeries.getData().add(createDataWithTooltip(yearLabel, deerValue));
        horsesSeries.getData().add(createDataWithTooltip(yearLabel, horsesValue));
    }
}
