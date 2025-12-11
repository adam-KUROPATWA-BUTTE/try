package gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import models.location.Location;
import models.location.LocationType;

/**
 * Visual component representing a location on the map
 */
public class LocationMapNode extends StackPane {
    
    private final Location location;
    private final Circle circle;
    private final Label nameLabel;
    private final Label populationLabel;
    
    public LocationMapNode(Location location) {
        this.location = location;
        
        // Create visual elements
        circle = new Circle(40);
        circle.setFill(getColorForLocationType(location.getType()));
        circle.setStroke(Color.web("#8b7355"));
        circle.setStrokeWidth(2);
        
        VBox infoBox = new VBox(2);
        infoBox.setAlignment(Pos.CENTER);
        
        nameLabel = new Label(location.getName());
        nameLabel.setFont(Font.font("Serif", 12));
        nameLabel.setTextFill(Color.web("#d4c5a9"));
        nameLabel.setStyle("-fx-font-weight: bold;");
        
        populationLabel = new Label(String.valueOf(location.getCharactersNbr()));
        populationLabel.setFont(Font.font("Serif", 14));
        populationLabel.setTextFill(Color.WHITE);
        populationLabel.setStyle("-fx-font-weight: bold;");
        
        infoBox.getChildren().addAll(nameLabel, populationLabel);
        
        getChildren().addAll(circle, infoBox);
        
        // Add hover effect
        setOnMouseEntered(e -> {
            circle.setStrokeWidth(3);
            circle.setStroke(Color.web("#b89968"));
            setScaleX(1.1);
            setScaleY(1.1);
        });
        
        setOnMouseExited(e -> {
            circle.setStrokeWidth(2);
            circle.setStroke(Color.web("#8b7355"));
            setScaleX(1.0);
            setScaleY(1.0);
        });
        
        getStyleClass().add("location-node");
    }
    
    /**
     * Get color based on location type
     */
    private Color getColorForLocationType(LocationType type) {
        switch (type) {
            case GAUL_TOWN:
                return Color.web("#2d5016");
            case ROMAIN_CAMP:
            case ROMAIN_TOWN:
                return Color.web("#8b0000");
            case ENCLOSURE:
                return Color.web("#4a5568");
            case BATTLEFIELD:
                return Color.web("#6b5a3a");
            case GAUL_ROMAIN_VILLAGE:
                return Color.web("#5a4a2a");
            default:
                return Color.web("#4a3f2a");
        }
    }
    
    /**
     * Update the display (call this to refresh)
     */
    public void update() {
        populationLabel.setText(String.valueOf(location.getCharactersNbr()));
    }
    
    public Location getLocation() {
        return location;
    }
}
