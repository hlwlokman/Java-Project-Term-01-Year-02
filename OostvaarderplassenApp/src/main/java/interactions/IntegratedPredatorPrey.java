package interactions;

import java.util.HashMap;
import java.util.Map;

public class IntegratedPredatorPrey {
    private boolean wolvesIntroduced = false;

    public Map<String, Integer> calculatePopulation(int year, boolean wolves) {
        wolvesIntroduced = wolves;

        int cattle = 30 + (year - 2020) * 5;
        int deer = 50 + (year - 2020) * 10;
        int horses = 40 + (year - 2020) * 7;

        if (wolvesIntroduced) {
            cattle -= 10; // Wolves reduce cattle population
            deer -= 20;
            horses -= 15;
        }

        // Ensure values don't go below zero
        cattle = Math.max(cattle, 0);
        deer = Math.max(deer, 0);
        horses = Math.max(horses, 0);

        // Return populations as a map
        Map<String, Integer> population = new HashMap<>();
        population.put("Cattle", cattle);
        population.put("Deer", deer);
        population.put("Horses", horses);
        return population;
    }
}
