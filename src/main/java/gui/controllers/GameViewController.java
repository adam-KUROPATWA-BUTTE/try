package gui.controllers;

import gui.ArmoriqueApp;
import gui.components.LocationMapNode;
import gui.utils.GameState;
import javafx.application.Platform;
import javafx. collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml. Initializable;
import javafx. scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input. Dragboard;
import javafx. scene.input.TransferMode;
import javafx. scene.layout. Pane;
import javafx. scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import models.clanLeader.ClanLeader;
import models.food.Food;
import models.location. Battlefield;
import models.location. Location;
import models.people.Character;
import models.theater.Theater;

import java.net.URL;
import java.util. List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import java.util. Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

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
    @FXML private Button startFightButton; // NOUVEAU
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

    // NOUVEAUX états pour gérer le combat
    private boolean gameStarted = false;
    private boolean fightInProgress = false;
    private Timer fightTimer;

    private Map<Location, LocationMapNode> locationNodeMap = new HashMap<>();
    private Map<String, Character> characterIdMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameState = ArmoriqueApp.getGameState();

        // Bind UI elements to game state
        turnLabel.textProperty().bind(gameState.turnNumberProperty().asString());
        populationLabel.textProperty().bind(gameState.totalPopulationProperty().asString());
        eventLogList.setItems(gameState.getEventLog());

        // NOUVEAU : Désactiver le bouton de combat au démarrage
        if (startFightButton != null) {
            startFightButton.setDisable(true);
            startFightButton.setText("Démarrer Combat");
        }

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
            locationNode.setLayoutX(x - 40);
            locationNode.setLayoutY(y - 40);

            locationNode.setOnMouseClicked(event -> {
                selectLocation(location);
            });

            mapPane.getChildren().add(locationNode);
            locationNodeMap.put(location, locationNode);

            if (location instanceof Battlefield) {
                setupBattlefieldDropTarget(locationNode, (Battlefield) location);
            }
        }
    }

    /**
     * Setup drag and drop for character transfer
     */
    private void setupCharacterDragAndDrop() {
        characterList.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                }
            };

            cell.setOnDragDetected(event -> {
                if (! cell.isEmpty()) {
                    Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(cell. getItem());
                    db. setContent(content);
                    event.consume();
                }
            });

            return cell;
        });
    }

    /**
     * Setup a battlefield location node as a drop target
     */
    private void setupBattlefieldDropTarget(LocationMapNode battlefieldNode, Battlefield battlefield) {
        battlefieldNode. setOnDragOver(event -> {
            if (event.getGestureSource() != battlefieldNode && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        battlefieldNode. setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasString()) {
                String characterInfo = db.getString();
                Character character = getCharacterFromListItem(characterInfo);

                if (character != null && selectedLocation != null) {
                    String characterName = character.getName();
                    if (selectedLocation.removeCharacter(character)) {
                        battlefield.addCharacter(character);
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

        for (Character c : location. getCharacters()) {
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

        String[] parts = listItem.split(" \\(");
        if (parts.length > 0) {
            String name = parts[0];
            return getCharacterFromLocation(selectedLocation, name);
        }
        return null;
    }

    /**
     * Update the map visualization
     */
    private void updateMapVisualization() {
        for (Map.Entry<Location, LocationMapNode> entry : locationNodeMap.entrySet()) {
            entry.getValue().updateDisplay();
        }
    }

    /**
     * Select a location and display its details
     */
    private void selectLocation(Location location) {
        selectedLocation = location;
        updateLocationDetails();

        for (Map.Entry<Location, LocationMapNode> entry : locationNodeMap.entrySet()) {
            entry.getValue().setSelected(entry.getKey() == location);
        }
    }

    /**
     * Update the location details panel
     */
    private void updateLocationDetails() {
        if (selectedLocation == null) {
            locationNameLabel.setText("No location selected");
            locationTypeLabel. setText("");
            locationAreaLabel.setText("");
            characterList.setItems(FXCollections.observableArrayList());
            foodList.setItems(FXCollections.observableArrayList());
            hideBattlefieldControls();
            return;
        }

        locationNameLabel.setText(selectedLocation. getName());
        locationTypeLabel. setText("Type: " + selectedLocation.getType());
        locationAreaLabel. setText("Area: " + selectedLocation.getSuperficie());

        ObservableList<String> characters = FXCollections.observableArrayList();
        for (Character c : selectedLocation.getCharacters()) {
            String charInfo = String.format("%s (Health: %. 1f, Hungry: %s)",
                    c.getName(), c.getHealth(), c.isHungry() ? "Yes" : "No");
            characters.add(charInfo);
        }
        characterList.setItems(characters);

        ObservableList<String> foods = FXCollections. observableArrayList();
        for (Food f : selectedLocation.getFoods()) {
            foods. add(f.toString());
        }
        foodList.setItems(foods);

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
            if (locationDetailsPanel == null) {
                return;
            }
            battlefieldControlsBox = new VBox(10);
            battlefieldControlsBox.setPadding(new Insets(10));
            locationDetailsPanel. getChildren().add(battlefieldControlsBox);
        }

        battlefieldControlsBox.getChildren().clear();
        battlefieldControlsBox. setVisible(true);
        battlefieldControlsBox.setManaged(true);

        Label statusLabel = new Label("Status:  " + battlefield.getBattleStatus());
        statusLabel.setStyle("-fx-font-weight: bold;");

        // NOUVEAU : Afficher le statut du combat
        long aliveCount = battlefield.getCharacters().stream()
                .filter(c -> c.getHealth() > 0)
                .count();

        Label combatStatusLabel = new Label("Combattants vivants: " + aliveCount);
        combatStatusLabel. setStyle("-fx-font-size: 12px;");

        Button startBattleBtn = new Button("Start Battle");
        startBattleBtn.setDisable(! battlefield.canStartBattle());
        startBattleBtn.setOnAction(e -> {
            battlefield.startBattle();
            gameState.addEvent("Battle started at " + battlefield.getName());
            gameState.addEvent("Battle ended with " + battlefield.getCharactersNbr() + " survivors");
            updateLocationDetails();
            updateMapVisualization();
        });

        Button returnAllBtn = new Button("Return All to Origins");
        returnAllBtn.setDisable(battlefield.getCharactersNbr() == 0);
        returnAllBtn.setOnAction(e -> {
            int count = battlefield.getCharactersNbr();
            battlefield.returnSurvivorsToOrigins();
            gameState.addEvent("Returned " + count + " characters to their origins from " + battlefield.getName());
            updateLocationDetails();
            updateMapVisualization();
        });

        Label infoLabel = new Label();
        if (battlefield.canStartBattle()) {
            infoLabel.setText("✓ Ready for battle!");
            infoLabel.setStyle("-fx-text-fill: green;");
        } else if (battlefield.getCharactersNbr() == 0) {
            infoLabel.setText("Drag characters here to stage a battle");
            infoLabel.setStyle("-fx-text-fill: gray;");
        } else if (! battlefield.hasOpposingFactions()) {
            infoLabel. setText("⚠ Need opposing factions (Gauls vs Romans)");
            infoLabel.setStyle("-fx-text-fill:  orange;");
        } else {
            infoLabel. setText("⚠ Need at least 2 characters");
            infoLabel.setStyle("-fx-text-fill: orange;");
        }

        battlefieldControlsBox. getChildren().addAll(statusLabel, combatStatusLabel, infoLabel, startBattleBtn, returnAllBtn);
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
        Theater theater = gameState.getTheater();

        int gaulCount = 0;
        int romanCount = 0;
        int lycanthropeCount = 0;

        for (Location location : theater.getLocations()) {
            for (Character character : location.getCharacters()) {
                if (character instanceof models.people.Gaul) {
                    gaulCount++;
                } else if (character instanceof models.people.Roman) {
                    romanCount++;
                } else if (character instanceof models.people.Werewolf) {
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

    // ==================== NOUVELLES MÉTHODES POUR LE CONTRÔLE DU JEU ====================

    /**
     * NOUVEAU : Gérer Play/Pause avec activation du bouton de combat
     */
    @FXML
    private void onPlayPause() {
        if (!gameStarted) {
            // Premier clic sur Play - démarrer le jeu
            gameStarted = true;
            isRunning = true;
            playPauseButton.setText("Pause");

            // ACTIVER le bouton de combat maintenant
            if (startFightButton != null) {
                startFightButton.setDisable(false);
            }

            startSimulation();
            gameState.addEvent("🎮 Partie démarrée !");

        } else if (isRunning) {
            // Mettre en pause
            isRunning = false;
            playPauseButton.setText("Play");

            // Arrêter le combat en cours
            if (fightInProgress) {
                stopFight();
            }

            stopSimulation();
            gameState. addEvent("⏸️ Partie en pause");

        } else {
            // Reprendre
            isRunning = true;
            playPauseButton.setText("Pause");
            startSimulation();
            gameState. addEvent("▶️ Partie reprise");
        }
    }

    /**
     * NOUVEAU : Démarrer le combat continu
     */
    @FXML
    private void onStartFight() {
        // Vérifier que le jeu est lancé
        if (!gameStarted) {
            showAlert("Erreur", "Vous devez d'abord lancer la partie avec Play !");
            return;
        }

        // Vérifier qu'il y a un champ de bataille sélectionné
        if (selectedLocation == null || !(selectedLocation instanceof Battlefield)) {
            showAlert("Erreur", "Sélectionnez un champ de bataille pour lancer un combat !");
            return;
        }

        Battlefield battlefield = (Battlefield) selectedLocation;

        // Vérifier qu'il y a des combattants
        if (battlefield. getCharacters().isEmpty()) {
            showAlert("Info", "Aucun combattant sur ce champ de bataille !");
            return;
        }

        // Démarrer le combat continu
        fightInProgress = true;
        startFightButton.setText("Combat en cours.. .");
        startFightButton.setDisable(true);

        gameState.addEvent("⚔️ Combat lancé sur " + battlefield.getName() + " !");

        // Lancer le timer de combat
        startContinuousFight(battlefield);
    }

    /**
     * NOUVEAU :  Système de combat continu
     */
    private void startContinuousFight(Battlefield battlefield) {
        fightTimer = new Timer(true);

        fightTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    // Vérifier si le jeu est en pause
                    if (!isRunning) {
                        stopFight();
                        return;
                    }

                    // Vérifier s'il reste des combattants vivants
                    List<Character> aliveCombatants = battlefield. getCharacters().stream()
                            .filter(c -> c. getHealth() > 0)
                            .collect(Collectors. toList());

                    if (aliveCombatants.size() < 2) {
                        // Plus assez de combattants
                        stopFight();
                        gameState.addEvent("🏁 Combat terminé - " + aliveCombatants.size() + " survivant(s)");

                        // Renvoyer les survivants à leur lieu d'origine
                        returnSurvivorsToOrigin(battlefield);
                        return;
                    }

                    // Effectuer un round de combat
                    performFightRound(battlefield, aliveCombatants);

                    // Mettre à jour l'affichage
                    updateLocationDetails();
                });
            }
        }, 0, 2000); // Combat toutes les 2 secondes
    }

    /**
     * NOUVEAU : Arrêter le combat
     */
    private void stopFight() {
        if (fightTimer != null) {
            fightTimer.cancel();
            fightTimer = null;
        }

        fightInProgress = false;

        if (startFightButton != null) {
            startFightButton. setText("Démarrer Combat");
            startFightButton. setDisable(false);
        }
    }

    /**
     * NOUVEAU : Effectuer un round de combat
     */
    private void performFightRound(Battlefield battlefield, List<Character> combatants) {
        Random random = new Random();

        if (combatants.size() >= 2) {
            Character fighter1 = combatants.get(random.nextInt(combatants.size()));
            Character fighter2 = combatants.get(random.nextInt(combatants.size()));

            if (fighter1 != fighter2) {
                // Combat entre les deux
                fighter1.fight(fighter2);

                gameState.addEvent(String.format("⚔️ %s attaque %s (HP: %.0f)",
                        fighter1.getName(),
                        fighter2.getName(),
                        fighter2.getHealth()));

                // Vérifier si quelqu'un est mort
                if (fighter2.getHealth() <= 0) {
                    gameState.addEvent("💀 " + fighter2.getName() + " est tombé au combat !");
                }
            }
        }
    }

    /**
     * NOUVEAU : Renvoyer les survivants à leur lieu d'origine
     */
    private void returnSurvivorsToOrigin(Battlefield battlefield) {
        List<Character> survivors = battlefield.getCharacters().stream()
                .filter(c -> c.getHealth() > 0)
                .collect(Collectors.toList());

        for (Character survivor : survivors) {
            gameState.addEvent("🏠 " + survivor.getName() + " retourne à sa base");
        }

        battlefield.returnSurvivorsToOrigins();
        updateDisplay();
        updateMapVisualization();
    }

    // ==================== FIN DES NOUVELLES MÉTHODES ====================

    @FXML
    private void onFastForward() {
        // Fast forward functionality
    }

    @FXML
    private void onPause() {
        if (isRunning) {
            onPlayPause();
        }
    }

    private void startSimulation() {
        if (simulationTimer != null) {
            simulationTimer.cancel();
        }

        simulationTimer = new Timer(true);
        simulationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform. runLater(() -> {
                    gameState.incrementTurn();
                    gameState.getTheater().simulationStep();
                    updateDisplay();
                    updateMapVisualization();
                });
            }
        }, 0, 5000);
    }

    /**
     * MODIFIÉ : Arrêter aussi le combat lors de la pause
     */
    private void stopSimulation() {
        if (simulationTimer != null) {
            simulationTimer.cancel();
            simulationTimer = null;
        }

        // NOUVEAU : Arrêter aussi le combat
        if (fightInProgress) {
            stopFight();
            gameState.addEvent("⏸️ Combat interrompu (pause)");
        }
    }

    @FXML
    private void onMenu() {
        ArmoriqueApp.showMainMenu();
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
            updateMapVisualization();
        }
    }

    @FXML
    private void onHealAll() {
        if (selectedLocation == null) {
            showAlert("No Location Selected", "Please select a location first.");
            return;
        }

        ClanLeader chief = gameState. getPlayerClanLeader();
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
            gameState. addEvent("All characters fed at " + selectedLocation.getName());
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
        ClanLeader chief = gameState. getPlayerClanLeader();
        if (chief != null && chief.getLocation() != null) {
            String info = chief.scanLocation();
            showAlert("Location Examination", info);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType. INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}