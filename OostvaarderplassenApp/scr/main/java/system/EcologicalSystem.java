package system;

import models.HerbivorePopulation;

public class EcologicalSystem {
    private HerbivorePopulation heckCattle;
    private HerbivorePopulation konikHorses;
    private HerbivorePopulation redDeer;

    public EcologicalSystem(int cattlePopulation, int horsePopulation, int deerPopulation) {
        heckCattle = new HerbivorePopulation("Heck Cattle", cattlePopulation);
        konikHorses = new HerbivorePopulation("Konik Horses", horsePopulation);
        redDeer = new HerbivorePopulation("Red Deer", deerPopulation);
    }

    public void updateYear(int cattleBirths, int cattleMortality,
                           int horseBirths, int horseMortality,
                           int deerBirths, int deerMortality) {
        heckCattle.updatePopulation(cattleBirths, cattleMortality);
        konikHorses.updatePopulation(horseBirths, horseMortality);
        redDeer.updatePopulation(deerBirths, deerMortality);
    }

    public void displayPopulations() {
        System.out.println("Heck Cattle Population: " + heckCattle.getPopulation());
        System.out.println("Konik Horses Population: " + konikHorses.getPopulation());
        System.out.println("Red Deer Population: " + redDeer.getPopulation());
    }
}
