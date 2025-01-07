package environment;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrassHeightData {

    @JsonProperty("grass_height")
    private List<GrassRecord> grassRecords;

    public List<GrassRecord> getGrassRecords() {
        return grassRecords;
    }

    public void setGrassRecords(List<GrassRecord> grassRecords) {
        this.grassRecords = grassRecords;
    }

    public static class GrassRecord {
        private int year;
        @JsonProperty("Grass height (cm) on August 1st")
        private double grassHeight;

        // Getters and setters
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
