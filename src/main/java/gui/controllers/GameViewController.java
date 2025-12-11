package gui.controllers;

import gui.ArmoriqueApp;
import gui.components.LocationMapNode;
import gui.utils.GameState;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import models.clanLeader.ClanLeader;
import models.food.Food;
import models.location.Battlefield;
import models.location.Location;
import models.people.Character;
import models.theater.Theater;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller for the Main Game View
 */
public class GameViewController implements Initializable {
    
    @FXML private Label turnLabel;
    @FXML private Label populationLabel;
    @FXML private Label gaulCountLabel;
    @FXML private Label romanCountLabel;
    @FXML private Label lycanthropeCountLabel;
    @FXML private Button playPauseButton;
    @FXML private ListView<String> eventLogList;
    @FXML private Pane mapPane;
    @FXML private VBox locationDetailsPanel;
    @FXML private Label locationNameLabel;
    @FXML private Label locationTypeLabel;
    @FXML private Label locationAreaLabel;
    @FXML private ListView<String> characterList;
    @FXML private ListView<String> foodList;
    @FXML private Label clanChiefNameLabel;
    @FXML private Label clanChiefLocationLabel;
    @FXML private VBox battlefieldControlsBox;
    
    private GameState gameState;
    private Location selectedLocation;
    private Timer simulationTimer;
    private boolean isRunning = false;
    
    private Map<Location, LocationMapNode> locationNodeMap = new HashMap<>();
    private Map<String, Character> characterIdMap = new HashMap<>();
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameState = ArmoriqueApp.getGameState();
        
        // Bind UI elements to game state
        turnLabel.textProperty().bind(gameState.turnNumberProperty().asString());
        populationLabel.textProperty().bind(gameState.totalPopulationProperty().asString());
        eventLogList.setItems(gameState.getEventLog());
        
        // Initialize the view
        updateDisplay();
        createMapVisualization();
        updateClanChiefInfo();
        
        // Setup drag and drop for character transfer
        setupCharacterDragAndDrop();
    }
    
    /**
     * Create visual representation of locations on the map
     */
    private void createMapVisualization() {
        mapPane.getChildren().clear();
        locationNodeMap.clear();
        
        Theater theater = gameState.getTheater();
        List<Location> locations = theater.getLocations();
        
        if (locations.isEmpty()) {
            return;
        }
        
        // Arrange locations in a circular pattern
        double centerX = 400;
        double centerY = 300;
        double radius = 200;
        
        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            
            double angle = 2 * Math.PI * i / locations.size();
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            
            LocationMapNode locationNode = new LocationMapNode(location);
            locationNode.setLayoutX(x - 40); // Center the node
            locationNode.setLayoutY(y - 40);
            
            locationNode.setOnMouseClicked(event -> {
                selectLocation(location);
            });
            
            // Setup drop target for battlefields
            if (location instanceof Battlefield) {
                setupBattlefieldDropTarget(locationNode, (Battlefield) location);
            }
            
            mapPane.getChildren().add(locationNode);
            locationNodeMap.put(location, locationNode);
        }
    }
    
    /**
     * Setup drag and drop for character transfers
     */
    private void setupCharacterDragAndDrop() {
        characterList.setOnDragDetected(event -> {
            int selectedIndex = characterList.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0 && selectedLocation != null) {
                String selectedItem = characterList.getItems().get(selectedIndex);
                // Extract character ID from the list item
                Character character = getCharacterFromListItem(selectedItem);
                
                if (character != null) {
                    Dragboard db = characterList.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(character.getName());
                    db.setContent(content);
                    event.consume();
                }
            }
        });
    }
    
    /**
     * Setup a battlefield location node as a drop target
     */
    private void setupBattlefieldDropTarget(LocationMapNode battlefieldNode, Battlefield battlefield) {
        battlefieldNode.setOnDragOver(event -> {
            if (event.getDragboard().hasString() && selectedLocation != null) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        
        battlefieldNode.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            
            if (db.hasString()) {
                String characterName = db.getString();
                Character character = getCharacterFromLocation(selectedLocation, characterName);
                
                if (character != null) {
                    // Transfer character to battlefield
                    boolean transferred = gameState.getTheater().transferCharacterToBattlefield(
                        character, selectedLocation, battlefield);
                    
                    if (transferred) {
                        gameState.addEvent("Transferred " + characterName + " to " + battlefield.getName());
                        updateDisplay();
                        updateMapVisualization();
                        success = true;
                    }
                }
            }
            
            event.setDropCompleted(success);
            event.consume();
        });
    }
    
    /**
     * Get a character from the location by name
     */
    private Character getCharacterFromLocation(Location location, String name) {
        if (location == null) return null;
        
        for (Character c : location.getCharacters()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }
    
    /**
     * Extract character from a list item string
     */
    private Character getCharacterFromListItem(String listItem) {
        if (selectedLocation == null || listItem == null) return null;
        
        // List item format: "Name (Health: X, Hungry: Y)"
        String[] parts = listItem.split(" \\(");
        if (parts.length > 0) {
            String name = parts[0];
            return getCharacterFromLocation(selectedLocation, name);
        }
        return null;
    }
    
    /**
     * Update existing map nodes instead of recreating
     */
    private void updateMapVisualization() {
        for (Map.Entry<Location, LocationMapNode> entry : locationNodeMap.entrySet()) {
            entry.getValue().update();
        }
    }
    
    /**
     * Select a location and display its details
     */
    private void selectLocation(Location location) {
        selectedLocation = location;
        gameState.setSelectedLocationName(location.getName());
        updateLocationDetails();
    }
    
    /**
     * Update the location details panel
     */
    private void updateLocationDetails() {
        if (selectedLocation == null) {
            locationNameLabel.setText("No location selected");
            locationTypeLabel.setText("");
            locationAreaLabel.setText("");
            characterList.setItems(FXCollections.observableArrayList());
            foodList.setItems(FXCollections.observableArrayList());
            hideBattlefieldControls();
            return;
        }
        
        locationNameLabel.setText(selectedLocation.getName());
        locationTypeLabel.setText("Type: " + selectedLocation.getType());
        locationAreaLabel.setText("Area: " + selectedLocation.getSuperficie());
        
        // Update character list
        ObservableList<String> characters = FXCollections.observableArrayList();
        for (Character c : selectedLocation.getCharacters()) {
            String charInfo = String.format("%s (Health: %.1f, Hungry: %s)", 
                c.getName(), c.getHealth(), c.isHungry() ? "Yes" : "No");
            characters.add(charInfo);
        }
        characterList.setItems(characters);
        
        // Update food list
        ObservableList<String> foods = FXCollections.observableArrayList();
        for (Food f : selectedLocation.getFoods()) {
            foods.add(f.toString());
        }
        foodList.setItems(foods);
        
        // Show battlefield controls if this is a battlefield
        if (selectedLocation instanceof Battlefield) {
            showBattlefieldControls((Battlefield) selectedLocation);
        } else {
            hideBattlefieldControls();
        }
    }
    
    /**
     * Show battlefield-specific controls
     */
    private void showBattlefieldControls(Battlefield battlefield) {
        if (battlefieldControlsBox == null) {
            // Create controls dynamically if not in FXML
            battlefieldControlsBox = new VBox(10);
            battlefieldControlsBox.setPadding(new Insets(10));
            locationDetailsPanel.getChildren().add(battlefieldControlsBox);
        }
        
        battlefieldControlsBox.getChildren().clear();
        battlefieldControlsBox.setVisible(true);
        battlefieldControlsBox.setManaged(true);
        
        // Status label
        Label statusLabel = new Label("Status: " + battlefield.getBattleStatus());
        statusLabel.setStyle("-fx-font-weight: bold;");
        
        // Start Battle button
        Button startBattleBtn = new Button("Start Battle");
        startBattleBtn.setDisable(!battlefield.canStartBattle());
        startBattleBtn.setOnAction(e -> {
            battlefield.startBattle();
            gameState.addEvent("Battle started at " + battlefield.getName());
            gameState.addEvent("Battle ended with " + battlefield.getCharactersNbr() + " survivors");
            updateLocationDetails();
            updateMapVisualization();
        });
        
        // Return All to Origins button
        Button returnAllBtn = new Button("Return All to Origins");
        returnAllBtn.setDisable(battlefield.getCharactersNbr() == 0);
        returnAllBtn.setOnAction(e -> {
            int count = battlefield.getCharactersNbr();
            battlefield.returnSurvivorsToOrigins();
            gameState.addEvent("Returned " + count + " characters to their origins from " + battlefield.getName());
            updateLocationDetails();
            updateMapVisualization();
        });
        
        // Info label
        Label infoLabel = new Label();
        if (battlefield.canStartBattle()) {
            infoLabel.setText("✓ Ready for battle!");
            infoLabel.setStyle("-fx-text-fill: green;");
        } else if (battlefield.getCharactersNbr() == 0) {
            infoLabel.setText("Drag characters here to stage a battle");
            infoLabel.setStyle("-fx-text-fill: gray;");
        } else if (!battlefield.hasOpposingFactions()) {
            infoLabel.setText("⚠ Need opposing factions (Gauls vs Romans)");
            infoLabel.setStyle("-fx-text-fill: orange;");
        } else {
            infoLabel.setText("⚠ Need at least 2 characters");
            infoLabel.setStyle("-fx-text-fill: orange;");
        }
        
        battlefieldControlsBox.getChildren().addAll(statusLabel, infoLabel, startBattleBtn, returnAllBtn);
    }
    
    /**
     * Hide battlefield controls
     */
    private void hideBattlefieldControls() {
        if (battlefieldControlsBox != null) {
            battlefieldControlsBox.setVisible(false);
            battlefieldControlsBox.setManaged(false);
        }
    }
    
    /**
     * Update all display elements
     */
    private void updateDisplay() {
        gameState.updateTotalPopulation();
        updateStatistics();
        
        if (selectedLocation != null) {
            updateLocationDetails();
        }
    }
    
    /**
     * Update statistics labels
     */
    private void updateStatistics() {
        int gaulCount = 0;
        int romanCount = 0;
        int lycanthropeCount = 0;
        
        Theater theater = gameState.getTheater();
        for (Location location : theater.getLocations()) {
            for (Character c : location.getCharacters()) {
                String className = c.getClass().getSimpleName();
                if (className.equals("Druid") || className.equals("Blacksmith") || 
                    className.equals("Merchant") || className.equals("InnKeeper")) {
                    gaulCount++;
                } else if (className.equals("Legionary") || className.equals("Prefect") || 
                          className.equals("General")) {
                    romanCount++;
                } else if (className.equals("Werewolf")) {
                    lycanthropeCount++;
                }
            }
        }
        
        gaulCountLabel.setText(String.valueOf(gaulCount));
        romanCountLabel.setText(String.valueOf(romanCount));
        lycanthropeCountLabel.setText(String.valueOf(lycanthropeCount));
    }
    
    /**
     * Update clan chief information
     */
    private void updateClanChiefInfo() {
        ClanLeader chief = gameState.getPlayerClanLeader();
        if (chief != null) {
            clanChiefNameLabel.setText(chief.getName());
            if (chief.getLocation() != null) {
                clanChiefLocationLabel.setText(chief.getLocation().getName());
            }
        }
    }
    
    @FXML
    private void onPlayPause() {
        if (isRunning) {
            pauseSimulation();
        } else {
            startSimulation();
        }
    }
    
    @FXML
    private void onFastForward() {
        startSimulation();
        // Could increase speed here
    }
    
    @FXML
    private void onPause() {
        pauseSimulation();
    }
    
    private void startSimulation() {
        if (isRunning) return;
        
        isRunning = true;
        playPauseButton.setText("⏸ Pause");
        
        simulationTimer = new Timer(true);
        simulationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    // Simulate one turn
                    gameState.setTurnNumber(gameState.getTurnNumber() + 1);
                    gameState.addEvent("Turn " + gameState.getTurnNumber() + " completed");
                    
                    // Update displays
                    updateDisplay();
                    updateMapVisualization(); // Use update instead of recreate
                });
            }
        }, 0, (long)(2000 / gameState.getSimulationSpeed()));
    }
    
    private void pauseSimulation() {
        if (!isRunning) return;
        
        isRunning = false;
        playPauseButton.setText("▶ Play");
        
        if (simulationTimer != null) {
            simulationTimer.cancel();
            simulationTimer = null;
        }
    }
    
    @FXML
    private void onMenu() {
        pauseSimulation();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Return to Menu");
        alert.setHeaderText("Return to Main Menu?");
        alert.setContentText("Are you sure? Current game will be lost.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                ArmoriqueApp.showMainMenu();
            }
        });
    }
    
    @FXML
    private void onCreateCharacter() {
        if (selectedLocation == null) {
            showAlert("No Location Selected", "Please select a location first.");
            return;
        }
        
        ClanLeader chief = gameState.getPlayerClanLeader();
        if (chief != null) {
            chief.setLocation(selectedLocation);
            chief.createCharacter(chief.randomCharacterData());
            gameState.addEvent("Character created at " + selectedLocation.getName());
            updateDisplay();
            updateMapVisualization(); // Update instead of recreate
        }
    }
    
    @FXML
    private void onHealAll() {
        if (selectedLocation == null) {
            showAlert("No Location Selected", "Please select a location first.");
            return;
        }
        
        ClanLeader chief = gameState.getPlayerClanLeader();
        if (chief != null) {
            chief.setLocation(selectedLocation);
            chief.healCharacters();
            gameState.addEvent("All characters healed at " + selectedLocation.getName());
            updateLocationDetails();
        }
    }
    
    @FXML
    private void onFeedAll() {
        if (selectedLocation == null) {
            showAlert("No Location Selected", "Please select a location first.");
            return;
        }
        
        ClanLeader chief = gameState.getPlayerClanLeader();
        if (chief != null) {
            chief.setLocation(selectedLocation);
            chief.feedCharacters();
            gameState.addEvent("All characters fed at " + selectedLocation.getName());
            updateLocationDetails();
        }
    }
    
    @FXML
    private void onPreparePotion() {
        if (selectedLocation == null) {
            showAlert("No Location Selected", "Please select a location first.");
            return;
        }
        
        ClanLeader chief = gameState.getPlayerClanLeader();
        if (chief != null) {
            chief.setLocation(selectedLocation);
            boolean success = chief.preparePotion();
            if (success) {
                gameState.addEvent("Potion prepared at " + selectedLocation.getName());
                showAlert("Success", "Magic potion prepared successfully!");
            } else {
                gameState.addEvent("Failed to prepare potion at " + selectedLocation.getName());
                showAlert("Failed", "Could not prepare potion. Need druid and ingredients.");
            }
            updateLocationDetails();
        }
    }
    
    @FXML
    private void onExamineLocation() {
        ClanLeader chief = gameState.getPlayerClanLeader();
        if (chief != null && chief.getLocation() != null) {
            String info = chief.scanLocation();
            showAlert("Location Examination", info);
        }
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
