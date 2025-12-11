package gui;

import gui.utils.GameState;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main JavaFX Application for Armorique Simulation
 * Entry point for the GUI
 */
public class ArmoriqueApp extends Application {
    
    private static Stage primaryStage;
    private static GameState gameState;
    
    public static final int WINDOW_WIDTH = 1400;
    public static final int WINDOW_HEIGHT = 900;
    
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        gameState = new GameState();
        
        primaryStage.setTitle("Armorique: Invasion & Lycanthropes");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(800);
        
        // Load and show main menu
        showMainMenu();
        
        primaryStage.show();
    }
    
    /**
     * Show the main menu screen
     */
    public static void showMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(ArmoriqueApp.class.getResource("/fxml/MainMenu.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            scene.getStylesheets().add(ArmoriqueApp.class.getResource("/css/style.css").toExternalForm());
            
            primaryStage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Error loading MainMenu: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Show the setup screen
     */
    public static void showSetupScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(ArmoriqueApp.class.getResource("/fxml/SetupScreen.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            scene.getStylesheets().add(ArmoriqueApp.class.getResource("/css/style.css").toExternalForm());
            
            primaryStage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Error loading SetupScreen: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Show the main game view
     */
    public static void showGameView() {
        try {
            FXMLLoader loader = new FXMLLoader(ArmoriqueApp.class.getResource("/fxml/GameView.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            scene.getStylesheets().add(ArmoriqueApp.class.getResource("/css/style.css").toExternalForm());
            
            primaryStage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Error loading GameView: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get the primary stage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    /**
     * Get the game state
     */
    public static GameState getGameState() {
        return gameState;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
