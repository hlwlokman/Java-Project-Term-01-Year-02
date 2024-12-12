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

public class PreditorPreyInteraction {
    static double r = 1;  // Deer growth rate
    static double K = 10000; // Carrying capacity, deer population
    static double a = 0.015;  // Predation rate
    static double b = 0.005; // Conversion efficiency
    static double d = 0.15;  // Wolf death rate

    // Initial conditions
    static double N0 = 3200; // Initial deer population
    static double P0 = 20;   // Initial wolf population
    static int totalTimeSteps = 5;  // Number of time steps (e.g., years)

    public static void main(String[] args) {
        // Time and population lists
        List<Double> time = new ArrayList<>();
        List<Double> deerPopulation = new ArrayList<>();
        List<Double> wolfPopulation = new ArrayList<>();

        // Initial values
        double t = 2024;
        double N = N0;
        double P = P0;

        // Simulate using discrete-time difference equations
        for (int i = 0; i < totalTimeSteps; i++) {
            time.add(t);
            deerPopulation.add(N);
            wolfPopulation.add(P);

            double dN = r * N * (1 - N / K) - a * N * P;
            double dP = b * N * P - d * P;

            // Update populations using the discrete-time equations
            N = Math.max(N + dN, 0);  // Update deer population
            P = Math.max(P + dP, 0);                // Update wolf population

            t += 1;
            System.out.println("Deer: "+ deerPopulation.get(i)+" Wolves: "+wolfPopulation.get(i)+" Year: "+ time.get(i));
            System.out.println(dN + " " + dP);
        }

        // Plot the results
        plotResults(time, deerPopulation, wolfPopulation);
    }

    // Plot the results using JFreeChart
    public static void plotResults(List<Double> time, List<Double> deer, List<Double> wolves) {
        XYSeries deerSeries = new XYSeries("Deer Population");
        XYSeries wolfSeries = new XYSeries("Wolf Population");

        for (int i = 0; i < time.size(); i++) {
            deerSeries.add(time.get(i), deer.get(i));
            wolfSeries.add(time.get(i), wolves.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(deerSeries);
        dataset.addSeries(wolfSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Discrete Predator-Prey Dynamics",
                "Time (Years)",
                "Population",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        JFrame frame = new JFrame("Discrete Predator-Prey Model");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new java.awt.BorderLayout());
        frame.add(new ChartPanel(chart), java.awt.BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
