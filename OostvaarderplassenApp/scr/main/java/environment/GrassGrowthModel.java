package environment;

public class GrassGrowthModel {
    private double currentGrass;
    private double growthRate;
    private double carryingCapacity;
    private double consumptionRate;

    public GrassGrowthModel(double currentGrass, double growthRate, double carryingCapacity, double consumptionRate) {
        this.currentGrass = currentGrass;
        this.growthRate = growthRate;
        this.carryingCapacity = carryingCapacity;
        this.consumptionRate = consumptionRate;
    }

    public double calculateGrass(double herbivorePopulation, double timeStep) {
        double dFdt = growthRate * currentGrass * (1 - currentGrass / carryingCapacity) - consumptionRate * herbivorePopulation;
        currentGrass += dFdt * timeStep;
        return currentGrass;
    }

    public double getCurrentGrass() {
        return currentGrass;
    }
}
