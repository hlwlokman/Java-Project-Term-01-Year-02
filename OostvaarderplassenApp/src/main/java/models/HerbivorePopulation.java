package models;

public class HerbivorePopulation extends PopulationModel {
    private String species;

    public HerbivorePopulation(String species, int initialPopulation) {
        super(initialPopulation);
        this.species = species;
    }

    public String getSpecies() {
        return species;
    }
}
