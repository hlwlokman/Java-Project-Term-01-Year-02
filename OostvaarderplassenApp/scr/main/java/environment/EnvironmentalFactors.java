package environment;

public class EnvironmentalFactors {
    private static final int TOTAL_FORAGE_KG = 15000000; // Total forage in kg/year
    private static final double COMPETITION_FACTOR = 0.85; // 15% reduction for competition
    private static final double BASE_GRASS_HEIGHT = 28.79; // Grass height in 1998 (cm)

    public static int calculateCarryingCapacity(int dailyNeedKg, double allocationPercentage, double grassHeight) {
        double relativeForageFactor = grassHeight / BASE_GRASS_HEIGHT; // Adjust by grass height
        int annualNeedPerIndividual = dailyNeedKg * 365;
        int allocatedForage = (int)(TOTAL_FORAGE_KG * allocationPercentage * relativeForageFactor);
        return (int)((allocatedForage / (double)annualNeedPerIndividual) * COMPETITION_FACTOR);
    }

    public static void main(String[] args) {
        // Example: Using grass height for 2022 (15.01 cm)
        double grassHeight2022 = 15.01;

        int horseK = calculateCarryingCapacity(20, 0.4, grassHeight2022); // Horses need 20kg/day
        int deerK = calculateCarryingCapacity(10, 0.5, grassHeight2022);  // Deer need 10kg/day
        int cattleK = calculateCarryingCapacity(30, 0.1, grassHeight2022); // Cattle need 30kg/day

        System.out.println("Carrying Capacity for 2022:");
        System.out.println("Konik Horses: " + horseK);
        System.out.println("Red Deer: " + deerK);
        System.out.println("Heck Cattle: " + cattleK);
    }
}
