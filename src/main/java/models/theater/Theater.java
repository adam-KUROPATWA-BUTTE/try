package models.theater;

import models.location. Battlefield;
import models.location.Location;
import models.people.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Theater class that manages all locations in the simulation.
 */
public class Theater {
    private final List<Location> locations;

    public Theater() {
        this.locations = new ArrayList<>();
    }

    /**
     * Get all locations in the theater
     * @return list of locations
     */
    public List<Location> getLocations() {
        return new ArrayList<>(locations);
    }

    /**
     * Add a location to the theater
     * @param location the location to add
     * @return true if added successfully
     */
    public boolean addLocation(Location location) {
        if (location == null) return false;
        return locations.add(location);
    }

    /**
     * Remove a location from the theater
     * @param location the location to remove
     * @return true if removed successfully
     */
    public boolean removeLocation(Location location) {
        if (location == null) return false;
        return locations.remove(location);
    }

    /**
     * Get a location by name
     * @param name the name of the location
     * @return the location or null if not found
     */
    public Location getLocationByName(String name) {
        for (Location location : locations) {
            if (location.getName().equals(name)) {
                return location;
            }
        }
        return null;
    }

    /**
     * Transfer a character from their current location to a battlefield
     * @param character the character to transfer
     * @param origin the origin location where the character currently is
     * @param battlefield the battlefield destination
     * @return true if transfer was successful
     */
    public boolean transferCharacterToBattlefield(Character character, Location origin, Battlefield battlefield) {
        if (character == null || origin == null || battlefield == null) {
            return false;
        }

        // Remove from origin
        if (!origin.removeCharacter(character)) {
            return false;
        }

        // Add to battlefield with origin tracking
        if (!battlefield.addCharacterFromOrigin(character, origin)) {
            // If adding to battlefield fails, put character back to origin
            origin.addCharacter(character);
            return false;
        }

        return true;
    }

    /**
     * Get all battlefields in the theater
     * @return list of battlefields
     */
    public List<Battlefield> getBattlefields() {
        return locations.stream()
                .filter(loc -> loc instanceof Battlefield)
                .map(loc -> (Battlefield) loc)
                .collect(Collectors. toList());
    }

    /**
     * NOUVEAU : Effectuer une étape de simulation
     */
    public void simulationStep() {
        // Faire apparaître des herbes médicinales
        for (Location location : locations) {
            location.spawnHerbsIfNeeded();
        }

        // Faire vieillir certains personnages aléatoirement
        for (Location location : locations) {
            for (Character character : location.getCharacters()) {
                // Diminuer légèrement la santé si affamé
                if (character.isHungry()) {
                    character.takeDamage(2.0);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Theater with ").append(locations.size()).append(" locations:\n");
        for (Location location : locations) {
            sb.append(" - ").append(location.getName())
                    .append(" (").append(location.getType()).append(")")
                    .append(" - ").append(location.getCharactersNbr()).append(" characters\n");
        }
        return sb.toString();
    }
}