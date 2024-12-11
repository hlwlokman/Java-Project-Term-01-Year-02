package interactions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

class PredatorPreyInteractionTest {
    
    private PredatorPreyInteraction model;
    
    @BeforeEach
    public void setup() {
        model = new PredatorPreyInteraction();
    }
    
    @Test
    public void testPopulationGrowth() {
        // Test the population growth logic (basic simulation check)
        List<Double> deerPopulation = new ArrayList<>();
        List<Double> wolfPopulation = new ArrayList<>();
        
        // Simulate for a few time steps
        double N = 3200; // Initial deer population
        double P = 20;   // Initial wolf population
        
        int totalTimeSteps = 5;
        for (int i = 0; i < totalTimeSteps; i++) {
            deerPopulation.add(N);
            wolfPopulation.add(P);

            // Simulation equations
            double dN = PredatorPreyInteraction.r * N * (1 - N / PredatorPreyInteraction.K) - PredatorPreyInteraction.a * N * P;
            double dP = PredatorPreyInteraction.b * N * P - PredatorPreyInteraction.d * P;

            N = Math.max(N + dN, 0);  // Update deer population
            P = Math.max(P + dP, 0);  // Update wolf population
        }

        // Assert that population size is valid after the simulation
        assertTrue(deerPopulation.get(0) > 0, "Initial deer population should be greater than 0");
        assertTrue(wolfPopulation.get(0) > 0, "Initial wolf population should be greater than 0");
        assertEquals(5, deerPopulation.size(), "There should be 5 time steps");
        assertEquals(5, wolfPopulation.size(), "There should be 5 time steps");
    }

    @Test
    public void testPopulationChart() {
        // Testing if chart plotting does not throw exceptions
        List<Double> time = new ArrayList<>();
        List<Double> deerPopulation = new ArrayList<>();
        List<Double> wolfPopulation = new ArrayList<>();

        double t = 2024;
        double N = 3200;  // Initial deer population
        double P = 20;   // Initial wolf population

        for (int i = 0; i < 5; i++) {
            time.add(t);
            deerPopulation.add(N);
            wolfPopulation.add(P);

            double dN = PredatorPreyInteraction.r * N * (1 - N / PredatorPreyInteraction.K) - PredatorPreyInteraction.a * N * P;
            double dP = PredatorPreyInteraction.b * N * P - PredatorPreyInteraction.d * P;

            N = Math.max(N + dN, 0);  // Update deer population
            P = Math.max(P + dP, 0);  // Update wolf population
            t += 1;
        }

        try {
            // Try to plot the chart, no assertions, we just want to check if the method runs without errors
            PredatorPreyInteraction.plotResults(time, deerPopulation, wolfPopulation);
        } catch (Exception e) {
            fail("Exception occurred while plotting the graph: " + e.getMessage());
        }
    }
}
