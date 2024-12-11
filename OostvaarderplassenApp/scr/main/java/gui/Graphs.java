package gui;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Graphs {
    public static void main(String[] args) {
        String fisierExcel = "C:/Users/giuli/OneDrive/Desktop/Inholland/Year 2/Project Dev/ecological-system-simulator/Number of large herbivores in the Oostvaardersplassen 1990 - 2022.xlsx";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (FileInputStream fis = new FileInputStream(new File(fisierExcel));
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                // Presupunem că datele sunt în prima și a doua coloană
                Cell cellX = row.getCell(0); // Axa X
                Cell cellY = row.getCell(1);
                Cell cellZ = row.getCell(2);
                Cell cellW = row.getCell(3);

                if (cellX != null && cellY != null && cellZ != null && cellW != null) {
                    double year = cellx.getNumericCellValue();
                    double cattleValue= cellY.getNumericCellValue();
                    double horsesValue = cellZ.getNumericCellValue();
                    double deerValue = cellW.getNumericCellValue();

                    dataset.addValue(cattleValue, "Cattle", String.valueOf(1));
                    dataset.addValue(horsesValue, "Horses", String.valueOf(2));
                    dataset.addValue(deerValue, "Deer", String.valueOf(3));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Creează graficul
        JFreeChart barChart = ChartFactory.createBarChart(
                "Ecosystem plot",        
                "Animal",           
                "Year",             
                dataset
        );

        // Afișează graficul într-o fereastră
        JFrame frame = new JFrame("Grafic din Excel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new ChartPanel(barChart));
        frame.pack();
        frame.setVisible(true);
    }
}
