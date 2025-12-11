package gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx. scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene. paint.Color;
import models.location.Location;
import models.location. Battlefield;

/**
 * Visual node representing a location on the map
 */
public class LocationMapNode extends StackPane {

    private final Location location;
    private final Circle circle;
    private final Label nameLabel;
    private final Label countLabel;
    private boolean selected = false;

    public LocationMapNode(Location location) {
        this.location = location;

        // Create circle
        circle = new Circle(40);
        updateCircleColor();

        // Create labels
        nameLabel = new Label(location.getName());
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10px;");
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(70);
        nameLabel.setAlignment(Pos. CENTER);

        countLabel = new Label(String.valueOf(location. getCharactersNbr()));
        countLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        // Layout
        VBox vbox = new VBox(2);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(nameLabel, countLabel);

        getChildren().addAll(circle, vbox);

        // Styling
        setStyle("-fx-cursor: hand;");
    }

    /**
     * Update the display (character count, etc.)
     */
    public void updateDisplay() {
        countLabel.setText(String.valueOf(location.getCharactersNbr()));
        updateCircleColor();
    }

    /**
     * Set selection state
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        updateCircleColor();
    }

    /**
     * Update circle color based on location type and selection
     */
    private void updateCircleColor() {
        Color color;

        if (location instanceof Battlefield) {
            color = Color.DARKRED;
        } else {
            switch (location.getType()) {
                case GAUL_TOWN:
                    color = Color.BLUE;
                    break;
                case ROMAIN_TOWN:
                    color = Color.RED;
                    break;
                case GAUL_ROMAIN_VILLAGE:
                    color = Color.PURPLE;
                    break;
                case ENCLOS:
                    color = Color. BROWN;
                    break;
                case ROMAIN_CAMP:
                    color = Color. DARKRED;
                    break;
                default:
                    color = Color.GRAY;
            }
        }

        if (selected) {
            circle. setFill(color. brighter());
            circle.setStroke(Color. YELLOW);
            circle.setStrokeWidth(3);
        } else {
            circle.setFill(color);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(1);
        }
    }

    public Location getLocation() {
        return location;
    }
}