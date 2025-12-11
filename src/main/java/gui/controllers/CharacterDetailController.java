package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import models.people.Character;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Character Detail view
 */
public class CharacterDetailController implements Initializable {
    
    @FXML private Label characterNameLabel;
    @FXML private Label characterTypeLabel;
    @FXML private ProgressBar healthBar;
    @FXML private Label healthLabel;
    @FXML private ProgressBar strengthBar;
    @FXML private Label strengthLabel;
    @FXML private Label heightLabel;
    @FXML private Label ageLabel;
    @FXML private Label genderLabel;
    @FXML private Label hungryLabel;
    @FXML private Label locationLabel;
    
    private Character character;
    private Stage dialogStage;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization will happen when setCharacter is called
    }
    
    /**
     * Set the character to display
     */
    public void setCharacter(Character character) {
        this.character = character;
        updateDisplay();
    }
    
    /**
     * Set the dialog stage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /**
     * Update the display with character information
     */
    private void updateDisplay() {
        if (character == null) return;
        
        characterNameLabel.setText(character.getName());
        characterTypeLabel.setText(character.getClass().getSimpleName());
        
        // Health
        double health = character.getHealth();
        healthBar.setProgress(health / 100.0);
        healthLabel.setText(String.format("%.1f", health));
        
        // Strength
        double strength = character.getStrength();
        strengthBar.setProgress(strength);
        strengthLabel.setText(String.format("%.2f", strength));
        
        // Other attributes
        heightLabel.setText(String.format("%.2fm", character.getHeight()));
        ageLabel.setText(String.valueOf(character.getAge()));
        genderLabel.setText(character.getSex() == 'm' ? "Male" : "Female");
        
        // Status
        hungryLabel.setText(character.isHungry() ? "Yes" : "No");
        hungryLabel.setStyle(character.isHungry() ? 
            "-fx-text-fill: #8b0000; -fx-font-weight: bold;" : 
            "-fx-text-fill: #2d5016;");
    }
    
    @FXML
    private void onHeal() {
        if (character != null) {
            character.heal(20.0);
            updateDisplay();
        }
    }
    
    @FXML
    private void onFeed() {
        if (character != null) {
            // Would need food reference here
            System.out.println("Feed functionality - needs food reference");
        }
    }
    
    @FXML
    private void onViewDetails() {
        if (character != null) {
            System.out.println(character.toString());
        }
    }
    
    @FXML
    private void onClose() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }
}
