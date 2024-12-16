package environment;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EnvironmentalFactors {
    private static final int TOTAL_FORAGE_KG = 15000000; // Total forage in kg/year
    private static final double COMPETITION_FACTOR = 0.85; // 15% reduction for competition
    public static final double BASE_GRASS_HEIGHT = 28.79; // Grass height in 1998 (cm)

    public static double loadGrassHeight(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            return Double.parseDouble(content.trim());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading grass height from file: " + filePath);
            return 0.0; // Return a default value
            
        }
    }

    // Calculate the carrying capacity
    public static int calculateCarryingCapacity(int dailyNeedKg, double allocationPercentage, double grassHeight) {
        double relativeForageFactor = grassHeight / BASE_GRASS_HEIGHT; // Adjust by grass height
        int annualNeedPerIndividual = dailyNeedKg * 365;
        int allocatedForage = (int) (TOTAL_FORAGE_KG * allocationPercentage * relativeForageFactor);
        return (int) ((allocatedForage / (double) annualNeedPerIndividual) * COMPETITION_FACTOR);
    }

    // Logistic growth model for future population prediction
    public static int predictFuturePopulation(int currentPopulation, double intrinsicGrowthRate, int carryingCapacity, int years) {
        int predictedPopulation = currentPopulation;
        for (int t = 1; t <= years; t++) {
            predictedPopulation += intrinsicGrowthRate * predictedPopulation * 
                    (1 - (double) predictedPopulation / carryingCapacity);
        }
        return predictedPopulation;
    }

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

            // Check if the year was found
            if (selectedYearData == null) {
                System.out.println("Year " + targetYear + " not found in the data.");
                return;
            }

            // Extract data for the target year
            double grassHeight = (double) selectedYearData.get("grass_height");
            Map<String, Integer> populations = (Map<String, Integer>) selectedYearData.get("populations");

            // Calculate carrying capacities
            int horseK = calculateCarryingCapacity(20, 0.4, grassHeight); // Horses need 20kg/day
            int deerK = calculateCarryingCapacity(10, 0.5, grassHeight);  // Deer need 10kg/day
            int cattleK = calculateCarryingCapacity(30, 0.1, grassHeight); // Cattle need 30kg/day

            // Predict future populations
            System.out.print("Enter number of years into the future to predict: ");
            int futureYears = scanner.nextInt();
            double intrinsicGrowthRate = 0.1; // Example intrinsic growth rate (10% annual growth)

            int futureHorses = predictFuturePopulation(populations.get("konik_horses"), intrinsicGrowthRate, horseK, futureYears);
            int futureDeer = predictFuturePopulation(populations.get("red_deer"), intrinsicGrowthRate, deerK, futureYears);
            int futureCattle = predictFuturePopulation(populations.get("heck_cattle"), intrinsicGrowthRate, cattleK, futureYears);

            // Output results
            System.out.println("Results for the year " + targetYear + ":");
            System.out.println("Grass Height: " + grassHeight + " cm");
            System.out.println("Current Population:");
            System.out.println("Konik Horses: " + populations.get("konik_horses"));
            System.out.println("Red Deer: " + populations.get("red_deer"));
            System.out.println("Heck Cattle: " + populations.get("heck_cattle"));
            System.out.println("Calculated Carrying Capacity:");
            System.out.println("Konik Horses: " + horseK);
            System.out.println("Red Deer: " + deerK);
            System.out.println("Heck Cattle: " + cattleK);
            System.out.println("Predicted Future Population after " + futureYears + " years:");
            System.out.println("Konik Horses: " + futureHorses);
            System.out.println("Red Deer: " + futureDeer);
            System.out.println("Heck Cattle: " + futureCattle);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while processing the data.");
        }
    }
}
