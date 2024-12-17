package models;

public class PopulationModel {
    private int population;
    private int births;
    private int mortality;

    public PopulationModel(int initialPopulation) {
        this.population = initialPopulation;
    }

    public void updatePopulation(int births, int mortality) {
        this.births = births;
        this.mortality = mortality;
        this.population = population + births - mortality;
    }

    public int getPopulation() {
        return population;
    }

    public double calculateGrowthRate(int previousPopulation) {
        if (previousPopulation == 0) return 0;
        return ((double)(population - previousPopulation) / previousPopulation) * 100;
    }
}

