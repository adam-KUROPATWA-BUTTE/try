package gui.utils;

import models.theater.Theater;
import models.location.Location;
import models.clanLeader. ClanLeader;
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

    // ==================== GETTERS AND SETTERS ====================

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

    // ==================== OBSERVABLE PROPERTIES ====================

    public IntegerProperty turnNumberProperty() {
        return turnNumber;
    }

    public int getTurnNumber() {
        return turnNumber.get();
    }

    public void setTurnNumber(int value) {
        turnNumber.set(value);
    }

    /**
     * NOUVEAU : Incrémenter le numéro de tour
     */
    public void incrementTurn() {
        turnNumber.set(turnNumber.get() + 1);
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

    /**
     * NOUVEAU : Effacer le log d'événements
     */
    public void clearEventLog() {
        eventLog.clear();
    }

    // ==================== CONFIGURATION ====================

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

    // ==================== GAME LOGIC ====================

    /**
     * Calculate and update total population from all locations
     */
    public void updateTotalPopulation() {
        int total = 0;

        if (theater != null && theater.getLocations() != null) {
            for (Location location : theater.getLocations()) {
                if (location != null && location.getCharacters() != null) {
                    total += location.getCharacters().size();
                }
            }
        }

        setTotalPopulation(total);
    }

    /**
     * NOUVEAU : Réinitialiser l'état du jeu
     */
    public void reset() {
        turnNumber.set(0);
        selectedLocationName.set("");
        simulationRunning.set(false);
        totalPopulation.set(0);
        eventLog.clear();

        // Créer un nouveau théâtre vide
        this.theater = new Theater();
        this.playerClanLeader = null;

        addEvent("🔄 Jeu réinitialisé");
    }

    /**
     * NOUVEAU : Obtenir le statut actuel du jeu
     */
    public String getGameStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Tour:  ").append(getTurnNumber()).append("\n");
        status.append("Population: ").append(getTotalPopulation()).append("\n");
        status.append("Lieux:  ").append(theater != null ? theater.getLocations().size() : 0).append("\n");
        status.append("Simulation: ").append(isSimulationRunning() ? "En cours" : "En pause");
        return status.toString();
    }

    /**
     * NOUVEAU : Vérifier si le jeu est prêt à démarrer
     */
    public boolean isReadyToStart() {
        return theater != null
                && theater.getLocations() != null
                && ! theater.getLocations().isEmpty()
                && playerClanLeader != null;
    }

    /**
     * NOUVEAU :  Obtenir des statistiques du jeu
     */
    public GameStatistics getStatistics() {
        return new GameStatistics(
                getTurnNumber(),
                getTotalPopulation(),
                countGauls(),
                countRomans(),
                countLycanthropes(),
                countBattles()
        );
    }

    /**
     * NOUVEAU : Compter les Gaulois
     */
    private int countGauls() {
        int count = 0;
        if (theater != null && theater.getLocations() != null) {
            for (Location location : theater. getLocations()) {
                if (location != null && location.getCharacters() != null) {
                    for (models.people.Character character : location.getCharacters()) {
                        if (character instanceof models.people.Gaul) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * NOUVEAU : Compter les Romains
     */
    private int countRomans() {
        int count = 0;
        if (theater != null && theater.getLocations() != null) {
            for (Location location : theater.getLocations()) {
                if (location != null && location.getCharacters() != null) {
                    for (models.people.Character character : location. getCharacters()) {
                        if (character instanceof models.people. Roman) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * NOUVEAU : Compter les Lycanthropes
     */
    private int countLycanthropes() {
        int count = 0;
        if (theater != null && theater.getLocations() != null) {
            for (Location location : theater.getLocations()) {
                if (location != null && location.getCharacters() != null) {
                    for (models.people.Character character : location.getCharacters()) {
                        if (character instanceof models.people.Werewolf) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * NOUVEAU : Compter les champs de bataille
     */
    private int countBattles() {
        int count = 0;
        if (theater != null && theater.getLocations() != null) {
            for (Location location :  theater.getLocations()) {
                if (location instanceof models.location. Battlefield) {
                    count++;
                }
            }
        }
        return count;
    }

    public void initializeNewGame() {
        turnNumber.set(0);
        selectedLocationName.set("");
        simulationRunning.set(false);
    }

    /**
     * NOUVEAU : Classe interne pour les statistiques
     */
    public static class GameStatistics {
        public final int turns;
        public final int totalPopulation;
        public final int gaulCount;
        public final int romanCount;
        public final int lycanthropeCount;
        public final int battlefieldCount;

        public GameStatistics(int turns, int totalPopulation, int gaulCount,
                              int romanCount, int lycanthropeCount, int battlefieldCount) {
            this.turns = turns;
            this.totalPopulation = totalPopulation;
            this.gaulCount = gaulCount;
            this.romanCount = romanCount;
            this.lycanthropeCount = lycanthropeCount;
            this.battlefieldCount = battlefieldCount;
        }

        @Override
        public String toString() {
            return String.format(
                    "Statistics:\n" +
                            "  Tours: %d\n" +
                            "  Population totale: %d\n" +
                            "  Gaulois: %d\n" +
                            "  Romains: %d\n" +
                            "  Lycanthropes: %d\n" +
                            "  Champs de bataille: %d",
                    turns, totalPopulation, gaulCount, romanCount, lycanthropeCount, battlefieldCount
            );
        }
    }
}