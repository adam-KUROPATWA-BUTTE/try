package gui.utils;

import models.theater.Theater;
import models.location.Location;
import models.clanLeader.ClanLeader;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * GameState manages the state of the simulation and provides observable properties
 * for UI binding
 */
public class GameState {
    
    private Theater theater;
    private ClanLeader playerClanLeader;
    
    // Observable properties for UI binding
    private final IntegerProperty turnNumber = new SimpleIntegerProperty(0);
    private final StringProperty selectedLocationName = new SimpleStringProperty("");
    private final BooleanProperty simulationRunning = new SimpleBooleanProperty(false);
    private final IntegerProperty totalPopulation = new SimpleIntegerProperty(0);
    private final ObservableList<String> eventLog = FXCollections.observableArrayList();
    
    // Game configuration
    private String theaterName = "Armorique";
    private int mapSize = 5;
    private double simulationSpeed = 1.0;
    
    public GameState() {
        this.theater = new Theater();
    }
    
    // Getters and setters
    
    public Theater getTheater() {
        return theater;
    }
    
    public void setTheater(Theater theater) {
        this.theater = theater;
    }
    
    public ClanLeader getPlayerClanLeader() {
        return playerClanLeader;
    }
    
    public void setPlayerClanLeader(ClanLeader clanLeader) {
        this.playerClanLeader = clanLeader;
    }
    
    // Observable properties
    
    public IntegerProperty turnNumberProperty() {
        return turnNumber;
    }
    
    public int getTurnNumber() {
        return turnNumber.get();
    }
    
    public void setTurnNumber(int value) {
        turnNumber.set(value);
    }
    
    public StringProperty selectedLocationNameProperty() {
        return selectedLocationName;
    }
    
    public String getSelectedLocationName() {
        return selectedLocationName.get();
    }
    
    public void setSelectedLocationName(String value) {
        selectedLocationName.set(value);
    }
    
    public BooleanProperty simulationRunningProperty() {
        return simulationRunning;
    }
    
    public boolean isSimulationRunning() {
        return simulationRunning.get();
    }
    
    public void setSimulationRunning(boolean value) {
        simulationRunning.set(value);
    }
    
    public IntegerProperty totalPopulationProperty() {
        return totalPopulation;
    }
    
    public int getTotalPopulation() {
        return totalPopulation.get();
    }
    
    public void setTotalPopulation(int value) {
        totalPopulation.set(value);
    }
    
    public ObservableList<String> getEventLog() {
        return eventLog;
    }
    
    public void addEvent(String event) {
        eventLog.add(0, event); // Add to beginning
        if (eventLog.size() > 100) {
            eventLog.remove(100, eventLog.size()); // Keep only last 100 events
        }
    }
    
    // Configuration
    
    public String getTheaterName() {
        return theaterName;
    }
    
    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }
    
    public int getMapSize() {
        return mapSize;
    }
    
    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }
    
    public double getSimulationSpeed() {
        return simulationSpeed;
    }
    
    public void setSimulationSpeed(double simulationSpeed) {
        this.simulationSpeed = simulationSpeed;
    }
    
    /**
     * Calculate and update total population from all locations
     */
    public void updateTotalPopulation() {
        int total = 0;
        for (Location loc : theater.getLocations()) {
            total += loc.getCharactersNbr();
        }
        setTotalPopulation(total);
    }
    
    /**
     * Initialize a new game
     */
    public void initializeNewGame() {
        turnNumber.set(0);
        eventLog.clear();
        simulationRunning.set(false);
        addEvent("Game initialized");
    }
}
