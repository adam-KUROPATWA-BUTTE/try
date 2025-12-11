package models.location;

import models.people.Character;
import models.people.Gaul;
import models.people.Roman;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Battlefield class
 */
class BattlefieldTest {

    // Test character classes implementing faction interfaces
    static class RomanCharacter extends Character implements Roman {
        public RomanCharacter(String name, char sex, double height, int age, double strength) {
            super(name, sex, height, age, strength);
        }
    }
    
    static class GaulCharacter extends Character implements Gaul {
        public GaulCharacter(String name, char sex, double height, int age, double strength) {
            super(name, sex, height, age, strength);
        }
    }
    
    static class NeutralCharacter extends Character {
        public NeutralCharacter(String name, char sex, double height, int age, double strength) {
            super(name, sex, height, age, strength);
        }
    }

    private Battlefield battlefield;
    private Location gaulVillage;
    private Location romanCamp;

    @BeforeEach
    void setUp() {
        battlefield = new Battlefield("Test Battlefield", 500.0);
        gaulVillage = new Location("Gaul Village", 100.0, LocationType.GAUL_TOWN);
        romanCamp = new Location("Roman Camp", 100.0, LocationType.ROMAIN_CAMP);
    }

    @Test
    void constructor_createsBattlefield() {
        assertEquals("Test Battlefield", battlefield.getName());
        assertEquals(LocationType.BATTLEFIELD, battlefield.getType());
        assertEquals(Battlefield.BattleStatus.WAITING, battlefield.getBattleStatus());
    }

    @Test
    void battlefieldStartsEmpty() {
        assertEquals(0, battlefield.getCharactersNbr());
    }

    @Test
    void addCharacterFromOrigin_tracksOrigin() {
        GaulCharacter gaul = new GaulCharacter("Asterix", 'm', 1.6, 35, 0.9);
        
        assertTrue(battlefield.addCharacterFromOrigin(gaul, gaulVillage));
        assertEquals(1, battlefield.getCharactersNbr());
        assertEquals(gaulVillage, battlefield.getOriginLocation(gaul));
    }

    @Test
    void addCharacterFromOrigin_multipleCharacters_tracksAllOrigins() {
        GaulCharacter gaul = new GaulCharacter("Asterix", 'm', 1.6, 35, 0.9);
        RomanCharacter roman = new RomanCharacter("Marcus", 'm', 1.8, 30, 0.8);
        
        battlefield.addCharacterFromOrigin(gaul, gaulVillage);
        battlefield.addCharacterFromOrigin(roman, romanCamp);
        
        assertEquals(2, battlefield.getCharactersNbr());
        assertEquals(gaulVillage, battlefield.getOriginLocation(gaul));
        assertEquals(romanCamp, battlefield.getOriginLocation(roman));
    }

    @Test
    void hasOpposingFactions_noCharacters_returnsFalse() {
        assertFalse(battlefield.hasOpposingFactions());
    }

    @Test
    void hasOpposingFactions_onlyGauls_returnsFalse() {
        battlefield.addCharacterFromOrigin(
            new GaulCharacter("Asterix", 'm', 1.6, 35, 0.9), gaulVillage);
        battlefield.addCharacterFromOrigin(
            new GaulCharacter("Obelix", 'm', 2.0, 35, 0.95), gaulVillage);
        
        assertFalse(battlefield.hasOpposingFactions());
    }

    @Test
    void hasOpposingFactions_onlyRomans_returnsFalse() {
        battlefield.addCharacterFromOrigin(
            new RomanCharacter("Marcus", 'm', 1.8, 30, 0.8), romanCamp);
        battlefield.addCharacterFromOrigin(
            new RomanCharacter("Julius", 'm', 1.85, 45, 0.85), romanCamp);
        
        assertFalse(battlefield.hasOpposingFactions());
    }

    @Test
    void hasOpposingFactions_gaulsAndRomans_returnsTrue() {
        battlefield.addCharacterFromOrigin(
            new GaulCharacter("Asterix", 'm', 1.6, 35, 0.9), gaulVillage);
        battlefield.addCharacterFromOrigin(
            new RomanCharacter("Marcus", 'm', 1.8, 30, 0.8), romanCamp);
        
        assertTrue(battlefield.hasOpposingFactions());
    }

    @Test
    void canStartBattle_notEnoughCharacters_returnsFalse() {
        battlefield.addCharacterFromOrigin(
            new GaulCharacter("Asterix", 'm', 1.6, 35, 0.9), gaulVillage);
        
        assertFalse(battlefield.canStartBattle());
    }

    @Test
    void canStartBattle_noOpposingFactions_returnsFalse() {
        battlefield.addCharacterFromOrigin(
            new GaulCharacter("Asterix", 'm', 1.6, 35, 0.9), gaulVillage);
        battlefield.addCharacterFromOrigin(
            new GaulCharacter("Obelix", 'm', 2.0, 35, 0.95), gaulVillage);
        
        assertFalse(battlefield.canStartBattle());
    }

    @Test
    void canStartBattle_enoughCharactersWithOpposingFactions_returnsTrue() {
        battlefield.addCharacterFromOrigin(
            new GaulCharacter("Asterix", 'm', 1.6, 35, 0.9), gaulVillage);
        battlefield.addCharacterFromOrigin(
            new RomanCharacter("Marcus", 'm', 1.8, 30, 0.8), romanCamp);
        
        assertTrue(battlefield.canStartBattle());
    }

    @Test
    void startBattle_changesStatusToAftermath() {
        battlefield.addCharacterFromOrigin(
            new GaulCharacter("Asterix", 'm', 1.6, 35, 0.9), gaulVillage);
        battlefield.addCharacterFromOrigin(
            new RomanCharacter("Marcus", 'm', 1.8, 30, 0.8), romanCamp);
        
        assertEquals(Battlefield.BattleStatus.WAITING, battlefield.getBattleStatus());
        
        battlefield.startBattle();
        
        assertEquals(Battlefield.BattleStatus.AFTERMATH, battlefield.getBattleStatus());
    }

    @Test
    void startBattle_cannotStartWithoutOpposingFactions_statusStaysWaiting() {
        battlefield.addCharacterFromOrigin(
            new GaulCharacter("Asterix", 'm', 1.6, 35, 0.9), gaulVillage);
        battlefield.addCharacterFromOrigin(
            new GaulCharacter("Obelix", 'm', 2.0, 35, 0.95), gaulVillage);
        
        battlefield.startBattle();
        
        assertEquals(Battlefield.BattleStatus.WAITING, battlefield.getBattleStatus());
    }

    @Test
    void returnSurvivorsToOrigins_returnsCharactersToOrigin() {
        GaulCharacter gaul = new GaulCharacter("Asterix", 'm', 1.6, 35, 0.9);
        RomanCharacter roman = new RomanCharacter("Marcus", 'm', 1.8, 30, 0.8);
        
        // Add characters to origins first
        gaulVillage.addCharacter(gaul);
        romanCamp.addCharacter(roman);
        
        // Remove and transfer to battlefield
        gaulVillage.removeCharacter(gaul);
        romanCamp.removeCharacter(roman);
        battlefield.addCharacterFromOrigin(gaul, gaulVillage);
        battlefield.addCharacterFromOrigin(roman, romanCamp);
        
        assertEquals(2, battlefield.getCharactersNbr());
        assertEquals(0, gaulVillage.getCharactersNbr());
        assertEquals(0, romanCamp.getCharactersNbr());
        
        battlefield.returnSurvivorsToOrigins();
        
        assertEquals(0, battlefield.getCharactersNbr());
        assertEquals(1, gaulVillage.getCharactersNbr());
        assertEquals(1, romanCamp.getCharactersNbr());
        assertTrue(gaulVillage.getCharacters().contains(gaul));
        assertTrue(romanCamp.getCharacters().contains(roman));
    }

    @Test
    void returnSurvivorsToOrigins_resetsStatusToWaiting() {
        GaulCharacter gaul = new GaulCharacter("Asterix", 'm', 1.6, 35, 0.9);
        RomanCharacter roman = new RomanCharacter("Marcus", 'm', 1.8, 30, 0.8);
        
        battlefield.addCharacterFromOrigin(gaul, gaulVillage);
        battlefield.addCharacterFromOrigin(roman, romanCamp);
        
        battlefield.startBattle();
        assertEquals(Battlefield.BattleStatus.AFTERMATH, battlefield.getBattleStatus());
        
        battlefield.returnSurvivorsToOrigins();
        assertEquals(Battlefield.BattleStatus.WAITING, battlefield.getBattleStatus());
    }

    @Test
    void toString_containsBattlefieldInfo() {
        GaulCharacter gaul = new GaulCharacter("Asterix", 'm', 1.6, 35, 0.9);
        RomanCharacter roman = new RomanCharacter("Marcus", 'm', 1.8, 30, 0.8);
        
        battlefield.addCharacterFromOrigin(gaul, gaulVillage);
        battlefield.addCharacterFromOrigin(roman, romanCamp);
        
        String result = battlefield.toString();
        
        assertTrue(result.contains("Test Battlefield"));
        assertTrue(result.contains("WAITING"));
        assertTrue(result.contains("Gauls: 1"));
        assertTrue(result.contains("Romans: 1"));
        assertTrue(result.contains("Can start battle: true"));
    }
}
