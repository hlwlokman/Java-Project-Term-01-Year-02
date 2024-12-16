package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SimulationArea extends Pane {
    private Canvas canvas;

    public SimulationArea() {

        canvas = new Canvas(700, 600);
        getChildren().add(canvas);
        drawInitialGrid(canvas.getGraphicsContext2D());
    }

    private void drawInitialGrid(GraphicsContext gc) {
        gc.setStroke(Color.LIGHTGRAY);
        for (int i = 0; i < 800; i += 50) {
            gc.strokeLine(i, 0, i, 600); // Vertical lines
            gc.strokeLine(0, i, 800, i); // Horizontal lines
        }
    }

    public void updateSimulation(double herbivorePop, double predatorPop) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 800, 600);
        gc.setFill(Color.GREEN);
        gc.fillOval(100, 300 - herbivorePop, 20, 20);
        gc.setFill(Color.RED);
        gc.fillOval(300, 300 - predatorPop, 20, 20);
    }
}
