package system;

import models.HerbivorePopulation;
import system.EcologicalSystem;

public class Main {
    public static void main(String[] args) {
        EcologicalSystem system = new EcologicalSystem(1000, 500, 300);
        system.displayPopulations();
    }
}
