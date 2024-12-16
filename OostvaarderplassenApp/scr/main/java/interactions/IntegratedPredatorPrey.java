package interactions;

public class IntegratedPredatorPrey {

    // Default values for predator-prey dynamics
    public static double r = 1; // Deer growth rate
    public static double a = 0.015; // Predation rate
    public static double b = 0.005; // Conversion efficiency
    public static double d = 0.15; // Wolf death rate

    // Initial conditions
    public static double P0 = 20; // Initial wolf population
    public static int totalTimeSteps = 10; // Number of time steps (years)

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
            N = Math.max(N + dN, 50);  // Minimum deer population
            P = Math.max(P + dP, 5);   // Minimum wolf population
        }

        return deerPopulation;
    }
}
