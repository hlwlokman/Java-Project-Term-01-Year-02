package environment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GrassGrowthModel {
    private static final String GRASS_DATA_FILE_PATH = "src/main/resources/grass_height.json";
    private final List<GrassRecord> grassRecords;

    public GrassGrowthModel() {
        grassRecords = loadGrassData();
    }

    private List<GrassRecord> loadGrassData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(
                new File(GRASS_DATA_FILE_PATH),
                new TypeReference<>() {}
            );
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<GrassRecord> getGrassRecords() {
        return grassRecords;
    }

    public static class GrassRecord {
        private int year;
        private double grassHeight;

        // Getters and Setters
        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public double getGrassHeight() {
            return grassHeight;
        }

        public void setGrassHeight(double grassHeight) {
            this.grassHeight = grassHeight;
        }
    }
}
