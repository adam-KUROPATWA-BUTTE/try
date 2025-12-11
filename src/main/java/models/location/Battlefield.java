package models.location;

import models.people.Character;
import models.people.Gaul;
import models.people.Roman;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Battlefield location where combat takes place
 */
public class Battlefield extends Location {

    private final Map<Character, Location> characterOrigins = new HashMap<>();
    private String battleStatus = "Waiting";
    private final Random random = new Random();

    public Battlefield(String name, double superficie) {
        super(name, superficie, LocationType.BATTLEFIELD);
    }

    /**
     * Add a character from an origin location
     */
    public boolean addCharacterFromOrigin(Character character, Location origin) {
        if (addCharacter(character)) {
            characterOrigins.put(character, origin);
            return true;
        }
        return false;
    }

    /**
     * Check if there are opposing factions
     */
    public boolean hasOpposingFactions() {
        boolean hasGauls = false;
        boolean hasRomans = false;

        for (Character c : getCharacters()) {
            if (c instanceof Gaul) hasGauls = true;
            if (c instanceof Roman) hasRomans = true;
        }

        return hasGauls && hasRomans;
    }

    /**
     * Check if battle can start
     */
    public boolean canStartBattle() {
        return getCharactersNbr() >= 2 && hasOpposingFactions();
    }

    /**
     * Get battle status
     */
    public String getBattleStatus() {
        return battleStatus;
    }

    /**
     * Start a battle
     */
    public void startBattle() {
        if (! canStartBattle()) {
            battleStatus = "Cannot start battle";
            return;
        }

        battleStatus = "Battle in progress";

        // Simple combat simulation
        while (getCharactersNbr() > 1 && hasOpposingFactions()) {
            performCombatRound();
        }

        battleStatus = "Battle ended";
    }

    /**
     * Perform one round of combat
     */
    private void performCombatRound() {
        var characters = getCharacters();
        if (characters.size() < 2) return;

        // Pick two random characters
        Character fighter1 = characters.get(random. nextInt(characters.size()));
        Character fighter2 = characters. get(random.nextInt(characters.size()));

        if (fighter1 != fighter2) {
            fighter1.fight(fighter2);

            // Remove dead characters
            if (fighter2.getHealth() <= 0) {
                removeCharacter(fighter2);
                characterOrigins.remove(fighter2);
            }
        }
    }

    /**
     * Return survivors to their origin locations
     */
    public void returnSurvivorsToOrigins() {
        var survivors = getCharacters();

        for (Character survivor : survivors) {
            Location origin = characterOrigins.get(survivor);
            if (origin != null) {
                removeCharacter(survivor);
                origin.addCharacter(survivor);
            }
        }

        characterOrigins.clear();
        battleStatus = "Cleared";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("Battle Status: ").append(battleStatus).append("\n");
        sb.append("Has Opposing Factions: ").append(hasOpposingFactions()).append("\n");
        return sb.toString();
    }
}