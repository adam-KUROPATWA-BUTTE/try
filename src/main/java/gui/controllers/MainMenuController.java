package gui.controllers;

import gui.ArmoriqueApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Controller for the Main Menu screen
 */
public class MainMenuController {
    
    @FXML
    private void onNewGame() {
        ArmoriqueApp.showSetupScreen();
    }
    
    @FXML
    private void onSettings() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Settings");
        alert.setHeaderText("Settings");
        alert.setContentText("Settings screen coming soon!");
        alert.showAndWait();
    }
    
    @FXML
    private void onExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit Game");
        alert.setContentText("Are you sure you want to exit?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        });
    }
}
