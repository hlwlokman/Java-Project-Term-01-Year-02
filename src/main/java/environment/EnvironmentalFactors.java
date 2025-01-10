package environment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnvironmentalFactors {
    private static final String POPULATION_FILE_PATH = "src/main/resources/animals_on_january_1st.json";
    private static final String GRASS_HEIGHT_FILE_PATH = "src/main/resources/grass_height.json";
    private static final int TOTAL_FORAGE_KG = 15000000; // Total forage in kg/year
    private static final double COMPETITION_FACTOR = 0.85; // 15% reduction for competition
    public static final double BASE_GRASS_HEIGHT = 28.79; // Grass height in 1998 (cm)

    private ObjectMapper objectMapper;
    private List<AnimalRecord> animalRecords;
    private Map<Integer, GrassRecord> grassRecordsByYear;

    public EnvironmentalFactors() {
        objectMapper = new ObjectMapper();
        loadAnimalRecords();
        loadGrassRecords();
    }

    private void loadAnimalRecords() {
        try {
            animalRecords = objectMapper.readValue(new File(POPULATION_FILE_PATH), new TypeReference<List<AnimalRecord>>() {});
            if (animalRecords == null) {
                throw new IOException("No animal records found in the file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            animalRecords = List.of(); // Initialize as empty list to avoid null pointer
        }
    }

    private void loadGrassRecords() {
        try {
            List<GrassRecord> grassRecords = objectMapper.readValue(new File(GRASS_HEIGHT_FILE_PATH), new TypeReference<List<GrassRecord>>() {});
            if (grassRecords == null) {
                throw new IOException("No grass records found in the file.");
            }
            grassRecordsByYear = grassRecords.stream().collect(Collectors.toMap(GrassRecord::getYear, record -> record));
        } catch (IOException e) {
            e.printStackTrace();
            grassRecordsByYear = Map.of(); // Initialize as empty map to avoid null pointer
        }
    }

    public int getPopulationForSpecies(String species, int year, boolean wolvesIntroduced) {
        for (AnimalRecord record : animalRecords) {
            if (record.getYear() == year) {
                int population = 0;
                switch (species.toLowerCase()) {
                    case "cattle":
                        population = record.getHeckCattle();
                        break;
                    case "deer":
                        population = record.getRedDeer();
                        break;
                    case "horses":
                        population = record.getKonikHorses();
                        break;
                }

                if (wolvesIntroduced && (species.equalsIgnoreCase("deer") || species.equalsIgnoreCase("cattle"))) {
                    population -= (population * 0.2);
                }

                return population;
            }
        }
        return 0;
    }

    public double getGrassHeight(int year) {
        GrassRecord record = grassRecordsByYear.get(year);
        return record != null ? record.getGrassHeight() : 0.0;
    }

    public static int calculateCarryingCapacity(int dailyNeedKg, double allocationPercentage, double grassHeight) {
        double relativeForageFactor = grassHeight / BASE_GRASS_HEIGHT; // Adjust by grass height
        int annualNeedPerIndividual = dailyNeedKg * 365;
        int allocatedForage = (int) (TOTAL_FORAGE_KG * allocationPercentage * relativeForageFactor);
        return (int) ((allocatedForage / (double) annualNeedPerIndividual) * COMPETITION_FACTOR);
    }

    public int getCarryingCapacity(int dailyNeedKg, double allocationPercentage, double grassHeight) {
        return calculateCarryingCapacity(dailyNeedKg, allocationPercentage, grassHeight);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AnimalRecord {
        private int year;
        private int heckCattle;
        private int konikHorses;
        private int redDeer;

        // Getters and setters
        public int getYear() { return year; }
        public void setYear(int year) { this.year = year; }
        public int getHeckCattle() { return heckCattle; }
        public void setHeckCattle(int heckCattle) { this.heckCattle = heckCattle; }
        public int getKonikHorses() { return konikHorses; }
        public void setKonikHorses(int konikHorses) { this.konikHorses = konikHorses; }
        public int getRedDeer() { return redDeer; }
        public void setRedDeer(int redDeer) { this.redDeer = redDeer; }
    }

    public static class GrassRecord {
        private int year;
        private double grassHeight;

        // Getters and setters
        public int getYear() { return year; }
        public void setYear(int year) { this.year = year; }
        public double getGrassHeight() { return grassHeight; }
        public void setGrassHeight(double grassHeight) { this.grassHeight = grassHeight; }
    }
}
