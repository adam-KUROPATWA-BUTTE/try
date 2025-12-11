package gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

/**
 * Custom health bar component
 */
public class HealthBar extends VBox {
    
    private final ProgressBar progressBar;
    private final Label valueLabel;
    
    public HealthBar() {
        super(2);
        setAlignment(Pos.CENTER);
        
        progressBar = new ProgressBar(1.0);
        progressBar.setPrefWidth(100);
        progressBar.setPrefHeight(15);
        progressBar.setStyle("-fx-accent: #2d5016;");
        
        valueLabel = new Label("100/100");
        valueLabel.setStyle("-fx-font-size: 10px;");
        
        getChildren().addAll(progressBar, valueLabel);
    }
    
    /**
     * Set health value (0.0 to 1.0)
     */
    public void setHealth(double current, double max) {
        double ratio = Math.max(0, Math.min(1, current / max));
        progressBar.setProgress(ratio);
        valueLabel.setText(String.format("%.0f/%.0f", current, max));
        
        // Color based on health level
        if (ratio > 0.6) {
            progressBar.setStyle("-fx-accent: #2d5016;"); // Green
        } else if (ratio > 0.3) {
            progressBar.setStyle("-fx-accent: #d4a506;"); // Yellow
        } else {
            progressBar.setStyle("-fx-accent: #8b0000;"); // Red
        }
    }
}
