package models.location;

import models.people.Character;
import models.people.Gaul;
import models.people.Roman;

import java.util.HashMap;
import java.util.Map;

/**
 * Battlefield location that provides manual control over character transfers and combat.
 * Battlefields start empty and combat only begins when explicitly triggered by the player.
 * 
 * @author Project Team
 */
public class Battlefield extends Location {
    
    /**
     * Battlefield state enumeration
     */
    public enum BattleStatus {
        WAITING,      // No combat, staging characters
        IN_COMBAT,    // Battle in progress
        AFTERMATH     // Battle finished
    }
    
    private BattleStatus battleStatus;
    private final Map<Character, Location> originLocations;
    
    /**
     * Create a new Battlefield
     * @param name the name of the battlefield
     * @param superficie the surface area
     */
    public Battlefield(String name, double superficie) {
        super(name, superficie, LocationType.BATTLEFIELD);
        this.battleStatus = BattleStatus.WAITING;
        this.originLocations = new HashMap<>();
    }
    
    /**
     * Add a character to this battlefield and track their origin location
     * @param character the character to add
     * @param origin the location where the character came from
     * @return true if added successfully
     */
    public boolean addCharacterFromOrigin(Character character, Location origin) {
        if (super.addCharacter(character)) {
            originLocations.put(character, origin);
            return true;
        }
        return false;
    }
    
    /**
     * Get the origin location of a character
     * @param character the character
     * @return the origin location or null if not tracked
     */
    public Location getOriginLocation(Character character) {
        return originLocations.get(character);
    }
    
    /**
     * Get current battle status
     * @return the battle status
     */
    public BattleStatus getBattleStatus() {
        return battleStatus;
    }
    
    /**
     * Check if there are opposing factions present (Gauls vs Romans)
     * @return true if both Gauls and Romans are present
     */
    public boolean hasOpposingFactions() {
        boolean hasGaul = false;
        boolean hasRoman = false;
        
        for (Character character : getCharacters()) {
            if (character instanceof Gaul) {
                hasGaul = true;
            }
            if (character instanceof Roman) {
                hasRoman = true;
            }
            
            if (hasGaul && hasRoman) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Check if battle can start (requires at least 2 characters with opposing factions)
     * @return true if battle can start
     */
    public boolean canStartBattle() {
        return getCharactersNbr() >= 2 && hasOpposingFactions();
    }
    
    /**
     * Manually trigger combat on this battlefield.
     * Combat only happens when explicitly called by the player.
     */
    public void startBattle() {
        if (!canStartBattle()) {
            return;
        }
        
        battleStatus = BattleStatus.IN_COMBAT;
        
        // Execute combat simulation
        executeCombat();
        
        battleStatus = BattleStatus.AFTERMATH;
    }
    
    /**
     * Execute the combat simulation between characters
     */
    private void executeCombat() {
        // Basic combat simulation - each character fights another
        // This is a simplified version; can be enhanced later
        var characters = getCharacters();
        if (characters.size() < 2) return;
        
        // Simple pairwise combat
        for (int i = 0; i < characters.size() - 1; i++) {
            Character c1 = characters.get(i);
            Character c2 = characters.get(i + 1);
            
            // Check if they are opposing factions
            boolean c1IsGaul = c1 instanceof Gaul;
            boolean c1IsRoman = c1 instanceof Roman;
            boolean c2IsGaul = c2 instanceof Gaul;
            boolean c2IsRoman = c2 instanceof Roman;
            
            if ((c1IsGaul && c2IsRoman) || (c1IsRoman && c2IsGaul)) {
                c1.fight(c2);
                c2.fight(c1);
            }
        }
    }
    
    /**
     * Return all surviving characters to their original locations
     */
    public void returnSurvivorsToOrigins() {
        var charactersToReturn = getCharacters();
        
        for (Character character : charactersToReturn) {
            Location origin = originLocations.get(character);
            if (origin != null && !character.isDead()) {
                removeCharacter(character);
                origin.addCharacter(character);
            }
        }
        
        // Clear tracking for returned characters
        originLocations.clear();
        battleStatus = BattleStatus.WAITING;
    }
    
    /**
     * Remove dead characters from the battlefield and origin tracking
     */
    public void removeDeadCharacters() {
        var characters = getCharacters();
        
        for (Character character : characters) {
            if (character.isDead()) {
                removeCharacter(character);
                originLocations.remove(character);
            }
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Battlefield: ").append(getName()).append("\n");
        sb.append("Status: ").append(battleStatus).append("\n");
        sb.append("Characters: ").append(getCharactersNbr()).append("\n");
        
        int gaulCount = 0;
        int romanCount = 0;
        
        for (Character c : getCharacters()) {
            if (c instanceof Gaul) gaulCount++;
            if (c instanceof Roman) romanCount++;
        }
        
        sb.append("Gauls: ").append(gaulCount).append("\n");
        sb.append("Romans: ").append(romanCount).append("\n");
        sb.append("Can start battle: ").append(canStartBattle()).append("\n");
        
        return sb.toString();
    }
}
