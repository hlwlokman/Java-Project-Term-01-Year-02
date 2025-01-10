//package models;
//
//public class PredatorPopulation extends PopulationModel {
//    private double predationRate;
//    private double predatorEfficiency;
//    private double mortalityRate;
//    private double preyPopulation;
//
//    public PredatorPopulation(double currentPopulation, double growthRate, double carryingCapacity, 
//                              double predationRate, double predatorEfficiency, double mortalityRate, double preyPopulation) {
//        super(currentPopulation, growthRate, carryingCapacity);
//        this.predationRate = predationRate;
//        this.predatorEfficiency = predatorEfficiency;
//        this.mortalityRate = mortalityRate;
//        this.preyPopulation = preyPopulation;
//    }
//
//    @Override
//    public double calculatePopulation(double timeStep) {
//        double dPdt = predatorEfficiency * predationRate * preyPopulation * currentPopulation - mortalityRate * currentPopulation;
//        currentPopulation += dPdt * timeStep;
//        return currentPopulation;
//    }
//}
