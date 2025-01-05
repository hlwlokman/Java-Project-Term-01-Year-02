package interactions;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import static environment.EnvironmentalFactors.calculateCarryingCapacity;

public class PreditorPreyInteraction {
    static double rDeer = 0.1737;   // Deer growth rate
    static double rHorse = 0.15; // Horse growth rate
    static double rCattle = 0.1; // Cattle growth rate

    // Calculate carrying capacities
    static int horseK = calculateCarryingCapacity(20, 0.4, 28); // Horses need 20kg/day
    static int deerK = calculateCarryingCapacity(10, 0.5, 28);  // Deer need 10kg/day
    static int cattleK = calculateCarryingCapacity(30, 0.1, 28); // Cattle need 30kg/day

    static double a = 0.01;  // Predation rate (wolves on prey)
    static double b = 0.001; // Conversion efficiency (prey to wolves)
    static double d = 0.5;   // Wolf death rate

    // Competition coefficients between prey species
    static double alphaDH = 0.5; // Effect of horses on deer
    static double alphaDC = 0.3; // Effect of cattle on deer
    static double alphaHD = 0.4; // Effect of deer on horses
    static double alphaHC = 0.2; // Effect of cattle on horses
    static double alphaCD = 0.3; // Effect of deer on cattle
    static double alphaCH = 0.4; // Effect of horses on cattle

    // Initial conditions
    static double NDeer = 3200; // Initial deer population
    static double NHorse = 1250; // Initial horse population
    static double NCattle = 250; // Initial cattle population
    static double NWolves = 5;  // Initial wolf population
    static int totalTimeSteps = 7; // Number of time steps (e.g., years)

    public static void predictPreyPredatorPopulation() {
        // Time and population lists
        List<Integer> time = new ArrayList<>();
        List<Integer> deerPopulation = new ArrayList<>();
        List<Integer> horsePopulation = new ArrayList<>();
        List<Integer> cattlePopulation = new ArrayList<>();
        List<Integer> wolfPopulation = new ArrayList<>();

        // Initial values
        int t = 2024;
        double N = NDeer;
        double H = NHorse;
        double C = NCattle;
        double P = NWolves;

        System.out.println("Carrying capacity Deer/Horse/Cattle: " + deerK + " " + horseK + " " + cattleK);

        // Simulate using discrete-time difference equations
        for (int i = 0; i < totalTimeSteps; i++) {
            time.add(t);
            deerPopulation.add((int) N);
            horsePopulation.add((int) H);
            cattlePopulation.add((int) C);
            wolfPopulation.add((int) P);

            // Lotka-Volterra competition for prey species
            double dN = rDeer * N * (1 - (N / deerK + alphaDH * H / horseK + alphaDC * C / cattleK)) - a * N * P;
            double dH = rHorse * H * (1 - (H / horseK + alphaHD * N / deerK + alphaHC * C / cattleK));
            double dC = rCattle * C * (1 - (C / cattleK + alphaCD * N / deerK + alphaCH * H / horseK));

            // Predator dynamics (wolves)
            double dP = Math.min(b * (N + H + C) * P - d * P, 3); // Wolves consume all prey species

            // Update populations using the discrete-time equations
            N = Math.max(N + dN, 0);  // Update deer population
            H = Math.max(H + dH, 0);  // Update horse population
            C = Math.max(C + dC, 0);  // Update cattle population
            P = Math.min(Math.max(P + dP, 0), 8); // Update wolf population

            t += 1;
            System.out.println("Year: " + t + " ===> " + "Deer: " + deerPopulation.get(i) + ", Horses: " + horsePopulation.get(i) +
                               ", Cattle: " + cattlePopulation.get(i) + ", Wolves: " + wolfPopulation.get(i));
        }

        // Plot the results
        plotResults(time, deerPopulation, horsePopulation, cattlePopulation, wolfPopulation);
    }

    // Plot the results using JFreeChart
    // Plot the results using JFreeChart
    public static void plotResults(List<Integer> time, List<Integer> deer, List<Integer> horses, 
                                   List<Integer> cattle, List<Integer> wolves) {
        XYSeries deerSeries = new XYSeries("Deer Population");
        XYSeries horseSeries = new XYSeries("Horse Population");
        XYSeries cattleSeries = new XYSeries("Cattle Population");
        XYSeries wolfSeries = new XYSeries("Wolf Population");

        for (int i = 0; i < time.size(); i++) {
        deerSeries.add(time.get(i), deer.get(i));
        horseSeries.add(time.get(i), horses.get(i));
        cattleSeries.add(time.get(i), cattle.get(i));
        wolfSeries.add(time.get(i), wolves.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(deerSeries);
        dataset.addSeries(horseSeries);
        dataset.addSeries(cattleSeries);
        dataset.addSeries(wolfSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
        "Predator-Prey and Competition Dynamics",
        "Time (Years)",
        "Population",
        dataset,
        PlotOrientation.VERTICAL,
        true,
        true,
        false
        );

        // Ensure X-axis (time) displays only integer values
        org.jfree.chart.axis.NumberAxis domainAxis = (org.jfree.chart.axis.NumberAxis) chart.getXYPlot().getDomainAxis();
        domainAxis.setStandardTickUnits(org.jfree.chart.axis.NumberAxis.createIntegerTickUnits());
        domainAxis.setNumberFormatOverride(new java.text.DecimalFormat("####")); // No decimals

        // Ensure Y-axis (population) displays only integer values without decimals
        org.jfree.chart.axis.NumberAxis rangeAxis = (org.jfree.chart.axis.NumberAxis) chart.getXYPlot().getRangeAxis();
        rangeAxis.setStandardTickUnits(org.jfree.chart.axis.NumberAxis.createIntegerTickUnits());
        rangeAxis.setNumberFormatOverride(new java.text.DecimalFormat("####")); // No decimals for Y-axis

        JFrame frame = new JFrame("Lotka-Volterra Competition Model");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new java.awt.BorderLayout());
        frame.add(new ChartPanel(chart), java.awt.BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

}
