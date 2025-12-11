package models.location;

import models.people.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationRestrictionTest {

    // Classes de test simulant différents types de personnages
    interface Roman {}
    interface Gaulois {}
    interface Lycanthrope {}

    static class RomanCharacter extends Character implements Roman {
        public RomanCharacter(String name, char sex, double height, int age, double strength) {
            super(name, sex, height, age, strength);
        }
        @Override
        protected int getCombatBonus() {
            return 0;
        }
    }
    static class GauloisCharacter extends Character implements Gaulois {
        public GauloisCharacter(String name, char sex, double height, int age, double strength) {
            super(name, sex, height, age, strength);
        }
        @Override
        protected int getCombatBonus() {
            return 0;
        }
    }
    static class LycanthropeCharacter extends Character implements Lycanthrope {
        public LycanthropeCharacter(String name, char sex, double height, int age, double strength) {
            super(name, sex, height, age, strength);
        }
        @Override
        protected int getCombatBonus() {
            return 0;
        }
    }
    static class NeutralCharacter extends Character {
        public NeutralCharacter(String name, char sex, double height, int age, double strength) {
            super(name, sex, height, age, strength);
        }
        @Override
        protected int getCombatBonus() {
            return 0;
        }
    }

    private Location battlefield;
    private Location gaulTown;
    private Location romainCamp;
    private Location enclosure;

    @BeforeEach
    void setUp() {
        battlefield = new Location("Battlefield", 100.0, LocationType.BATTLEFIELD);
        gaulTown = new Location("Gaul Town", 100.0, LocationType.GAUL_TOWN);
        romainCamp = new Location("Romain Camp", 100.0, LocationType.ROMAIN_CAMP);
        enclosure = new Location("Enclosure", 100.0, LocationType.ENCLOSURE);
    }

    @Test
    void constructor_createsLocationWithNameAndType() {
        assertEquals("Battlefield", battlefield.getName());
        assertEquals(LocationType.BATTLEFIELD, battlefield.getType());
    }

    @Test
    void addCharacter_nullCharacter_returnsFalse() {
        assertFalse(battlefield.addCharacter(null));
    }

    @Test
    void addCharacter_allowedCharacter_returnsTrue() {
        RomanCharacter roman = new RomanCharacter("Marcus", 'm', 1.8, 30, 80.0);
        assertTrue(battlefield.addCharacter(roman));
        assertTrue(battlefield.getCharacters().contains(roman));
    }

    @Test
    void addCharacter_notAddedCharacter_returnsFalse() {
        RomanCharacter roman = new RomanCharacter("Marcus", 'm', 1.8, 30, 80.0);
        assertFalse(battlefield.getCharacters().contains(roman));
    }

    @Test
    void addCharacter_gaulInGaulTown_addsSuccessfully() {
        GauloisCharacter gaulois = new GauloisCharacter("Asterix", 'm', 1.6, 35, 90.0);
        assertTrue(gaulTown.addCharacter(gaulois));
        assertTrue(gaulTown.getCharacters().contains(gaulois));
    }

    @Test
    void addCharacter_romanInGaulTown_returnsFalse() {
        RomanCharacter roman = new RomanCharacter("Marcus", 'm', 1.8, 30, 80.0);
        assertFalse(gaulTown.addCharacter(roman));
        assertFalse(gaulTown.getCharacters().contains(roman));
    }

    @Test
    void removeCharacter_existingCharacter_removesSuccessfully() {
        RomanCharacter roman = new RomanCharacter("Marcus", 'm', 1.8, 30, 80.0);
        romainCamp.addCharacter(roman);
        assertTrue(romainCamp.removeCharacter(roman));
        assertFalse(romainCamp.getCharacters().contains(roman));
    }

    @Test
    void removeCharacter_notInsideCharacter_returnsFalse() {
        RomanCharacter roman = new RomanCharacter("Marcus", 'm', 1.8, 30, 80.0);
        assertFalse(romainCamp.removeCharacter(roman));
    }

    @Test
    void removeCharacter_nullCharacter_returnsFalse() {
        assertFalse(battlefield.removeCharacter(null));
    }

    @Test
    void getCharacters_returnsAllCharactersInside() {
        RomanCharacter roman = new RomanCharacter("Marcus", 'm', 1.8, 30, 80.0);
        LycanthropeCharacter lycanthrope = new LycanthropeCharacter("Wolf", 'm', 1.9, 25, 100.0);

        romainCamp.addCharacter(roman);
        romainCamp.addCharacter(lycanthrope);

        assertEquals(2, romainCamp.getCharacters().size());
        assertTrue(romainCamp.getCharacters().contains(roman));
        assertTrue(romainCamp.getCharacters().contains(lycanthrope));
    }

    @Test
    void battlefield_allowsAllCharacterTypes() {
        assertTrue(battlefield.addCharacter(new RomanCharacter("Marcus", 'm', 1.8, 30, 80.0)));
        assertTrue(battlefield.addCharacter(new GauloisCharacter("Asterix", 'm', 1.6, 35, 90.0)));
        assertTrue(battlefield.addCharacter(new LycanthropeCharacter("Wolf", 'm', 1.9, 25, 100.0)));
        assertTrue(battlefield.addCharacter(new NeutralCharacter("Neutral", 'm', 1.7, 28, 70.0)));
        assertEquals(4, battlefield.getCharacters().size());
    }

    @Test
    void enclosure_allowsOnlyLycanthropes() {
        assertTrue(enclosure.addCharacter(new LycanthropeCharacter("Wolf", 'm', 1.9, 25, 100.0)));
        assertFalse(enclosure.addCharacter(new RomanCharacter("Marcus", 'm', 1.8, 30, 80.0)));
        assertFalse(enclosure.addCharacter(new GauloisCharacter("Asterix", 'm', 1.6, 35, 90.0)));
        assertEquals(1, enclosure.getCharacters().size());
    }
}
