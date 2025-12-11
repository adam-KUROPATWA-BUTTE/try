package models.theater;

import models.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Theater class that manages all locations in the simulation.
 * @author Mada
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
