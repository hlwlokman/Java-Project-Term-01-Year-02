package interactions;

import java.awt.BorderLayout;
import java.io.File;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.fasterxml.jackson.databind.ObjectMapper;

import environment.EnvironmentalFactors;

public class IntegratedPredatorPrey {

    // Default values for predator-prey dynamics
    static double r = 1; // Deer growth rate
    static double a = 0.015; // Predation rate
    static double b = 0.005; // Conversion efficiency
    static double d = 0.15; // Wolf death rate

    // Initial conditions
    static double P0 = 20; // Initial wolf population
    static int totalTimeSteps = 10; // Number of time steps (years)

    public static void main(String[] args) {
        try {
            // Load data from the JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("C:\\Git Projects\\oostvaardersplassen\\OostvaarderplassenApp\\animals_on_january_1st.json");
            Map<String, Object> data = objectMapper.readValue(file, Map.class);

            // Prompt user to select a target year
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the target year: ");
            int targetYear = scanner.nextInt();

            // Find the data for the selected year
            Map<String, Object> selectedYearData = null;
            for (Map<String, Object> yearData : (Iterable<Map<String, Object>>) data.get("animals_on_january_1")) {
                if ((int) yearData.get("year") == targetYear) {
                    selectedYearData = yearData;
                    break;
                }
            }

            if (selectedYearData == null) {
                System.out.println("Year " + targetYear + " not found in the data.");
                return;
            }

            // Extract grass height and population data
            double grassHeight = (double) selectedYearData.get("grass_height");
            Map<String, Integer> populations = (Map<String, Integer>) selectedYearData.get("populations");

            int initialDeerPopulation = populations.get("red_deer");

            // Calculate carrying capacity for deer
            int deerCarryingCapacity = EnvironmentalFactors.calculateCarryingCapacity(10, 0.5, grassHeight);

            // Simulate population dynamics without predators
            double[] deerPopulationWithoutPredators = simulateDeerPopulation(initialDeerPopulation, r, deerCarryingCapacity, totalTimeSteps);

            // Create the chart
            XYSeries series = new XYSeries("Deer Population Without Predators");
            for (int i = 0; i < totalTimeSteps; i++) {
                series.add(targetYear + i, deerPopulationWithoutPredators[i]);
            }

            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(series);

            JFreeChart chart = ChartFactory.createXYLineChart(
                    "Deer Population Dynamics",
                    "Year",
                    "Deer Population",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            // Create the frame and add the chart
            JFrame frame = new JFrame("Integrated Predator-Prey Model");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            ChartPanel chartPanel = new ChartPanel(chart);
            frame.add(chartPanel, BorderLayout.CENTER);

            // Add button to recalculate with predators
            JButton predatorButton = new JButton("Simulate with Predators");
            predatorButton.addActionListener(e -> {
                double[] deerPopulationWithPredators = simulateWithPredators(initialDeerPopulation, deerCarryingCapacity, grassHeight);
                XYSeries predatorSeries = new XYSeries("Deer Population With Predators");

                for (int i = 0; i < totalTimeSteps; i++) {
                    predatorSeries.add(targetYear + i, deerPopulationWithPredators[i]);
                }

                dataset.addSeries(predatorSeries);
                chart.fireChartChanged();
            });

            frame.add(predatorButton, BorderLayout.SOUTH);
            frame.pack();
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while processing the data.");
        }
    }

    // Simulate deer population without predators
    public static double[] simulateDeerPopulation(int initialPopulation, double r, int K, int years) {
        double[] populationOverTime = new double[years];
        populationOverTime[0] = initialPopulation;

        for (int t = 1; t < years; t++) {
            double currentPopulation = populationOverTime[t - 1];
            double growth = r * currentPopulation * (1 - currentPopulation / K);
            populationOverTime[t] = Math.max(currentPopulation + growth, 0);
        }
        return populationOverTime;
    }

    // Simulate deer population with predators
    public static double[] simulateWithPredators(int initialDeerPopulation, int carryingCapacity, double grassHeight) {
        double N = initialDeerPopulation; // Deer population
        double P = P0; // Wolf population
    
        double[] deerPopulation = new double[totalTimeSteps];
    
        for (int t = 0; t < totalTimeSteps; t++) {
            deerPopulation[t] = N;
    
            double dN = r * N * (1 - N / carryingCapacity) - a * N * P;
            double dP = b * N * P - d * P;
    
            // Ensure populations do not drop below a minimum threshold
            N = Math.max(N + dN, 50);  // Set a minimum deer population
            P = Math.max(P + dP, 5);   // Set a minimum wolf population
        }
    
        return deerPopulation;
    }
}
