package gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.people.Character;

/**
 * Card component for displaying character information
 */
public class CharacterCard extends VBox {
    
    private final Character character;
    private final Label nameLabel;
    private final Label typeLabel;
    private final HealthBar healthBar;
    private final Label statusLabel;
    
    public CharacterCard(Character character) {
        super(8);
        this.character = character;
        
        setAlignment(Pos.TOP_LEFT);
        setPadding(new Insets(10));
        getStyleClass().add("character-card");
        setMaxWidth(200);
        
        // Character name
        nameLabel = new Label(character.getName());
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        // Character type
        typeLabel = new Label(character.getClass().getSimpleName());
        typeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #8b7355;");
        
        // Health bar
        healthBar = new HealthBar();
        healthBar.setHealth(character.getHealth(), 100.0);
        
        // Status
        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 11px;");
        updateStatus();
        
        getChildren().addAll(nameLabel, typeLabel, healthBar, statusLabel);
        
        // Make clickable
        setOnMouseClicked(e -> {
            // Could trigger character detail view
            System.out.println("Clicked: " + character.getName());
        });
    }
    
    /**
     * Update the status label
     */
    private void updateStatus() {
        StringBuilder status = new StringBuilder();
        
        if (character.isHungry()) {
            status.append("🍖 Hungry ");
        }
        
        if (character.getHealth() < 50) {
            status.append("💔 Injured ");
        }
        
        statusLabel.setText(status.toString());
    }
    
    /**
     * Refresh the card display
     */
    public void refresh() {
        healthBar.setHealth(character.getHealth(), 100.0);
        updateStatus();
    }
    
    public Character getCharacter() {
        return character;
    }
}
