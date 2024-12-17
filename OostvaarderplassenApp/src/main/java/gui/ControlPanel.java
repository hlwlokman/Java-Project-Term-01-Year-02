package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;




public class ControlPanel extends VBox {

    private Graphs graphs;

    private int deerPopulation= 100;
    private int horsesPopulation = 50;
    private int cattlePopulation= 30;
    private int currentYear = 2024; 

    public ControlPanel(Graphs graphs) {
        this.graphs = graphs;
        // Imagini
        Image image1 = new Image(getClass().getResourceAsStream("/images/deer4.png"));
        Image image2 = new Image(getClass().getResourceAsStream("/images/horse2.png"));
        Image image3 = new Image(getClass().getResourceAsStream("/images/cattle12.png"));
        Image image4 = new Image(getClass().getResourceAsStream("/images/wolf.png"));

        ImageView iv1 = new ImageView(image1);
        ImageView iv2 = new ImageView(image2);
        ImageView iv3 = new ImageView(image3);
        ImageView iv4 = new ImageView(image4);

        iv1.setFitWidth(50); // dimensions
        iv1.setPreserveRatio(true);
        iv2.setFitWidth(50);
        iv2.setPreserveRatio(true);
        iv3.setFitWidth(50);
        iv3.setPreserveRatio(true);
        iv4.setFitWidth(50);
        iv4.setPreserveRatio(true);

        // Casete de bifat
        CheckBox cattle = new CheckBox("Cattle");
        CheckBox deer = new CheckBox("Deer");
        CheckBox horses = new CheckBox("Horses");

        // Stilizare casete
        String fontStyle = "-fx-font-size: 20px";
        deer.setStyle(fontStyle);
        horses.setStyle(fontStyle);
        cattle.setStyle(fontStyle);

        // HBox pentru fiecare opțiune
        HBox deerOption = new HBox(10, deer, iv1);
        HBox horsesOption = new HBox(10, horses, iv2);
        HBox cattleOption = new HBox(10, cattle, iv3);

        deerOption.setPadding(new Insets(5));
        horsesOption.setPadding(new Insets(5));
        cattleOption.setPadding(new Insets(5));

        //wolf button
        Button wolvesButton = new Button("Wolves");
        wolvesButton.setGraphic(iv4);
        wolvesButton.setLayoutX(30);
        wolvesButton.setLayoutY(20);
        wolvesButton.setStyle("-fx-background-color: red; -fx-background-radius: 20; -fx-text-fill: white; -fx-font-size: 15px;");
        
        wolvesButton.setOnAction(e -> System.out.println("Wolves are released!"));

        // Adăugăm opțiunile în VBox
        this.setSpacing(10);
        this.setPadding(new Insets(40, 50, 10, 10));
        this.getChildren().addAll(cattleOption, deerOption, horsesOption, wolvesButton);

        deer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateGraph(deer.isSelected(), horses.isSelected(), cattle.isSelected());
            }
        });

        horses.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateGraph(deer.isSelected(), horses.isSelected(), cattle.isSelected());
            }
        });

        cattle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateGraph(deer.isSelected(), horses.isSelected(), cattle.isSelected());
            }
        });

}
        private void updateGraph(boolean deerSelected, boolean horsesSelected, boolean cattleSelected) {
            // Set values based on whether the checkboxes are selected
            int deerValue = deerSelected ? deerPopulation : 0;
            int horsesValue = horsesSelected ? horsesPopulation : 0;
            int cattleValue = cattleSelected ? cattlePopulation : 0;

            // Update the graph with the selected values
            graphs.updateData(deerValue, horsesValue, cattleValue, "Year " + currentYear);
}
}  