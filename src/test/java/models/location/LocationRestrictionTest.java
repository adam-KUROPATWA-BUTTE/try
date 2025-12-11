package models.location;

import models.people.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    // Classes de test simulant différents types de personnages
    interface Roman {}
    interface Gaulois {}
    interface Lycanthrope {}

    static class RomanCharacter extends Character<?, ?> implements Roman {
        public RomanCharacter(String name, char sex, double height, int age, double strength) {
            super(name, sex, height, age, strength);
        }
    }
    static class GauloisCharacter extends Character<?, ?> implements Gaulois {
        public GauloisCharacter(String name, char sex, double height, int age, double strength) {
            super(name, sex, height, age, strength);
        }
    }
    static class LycanthropeCharacter extends Character<?, ?> implements Lycanthrope {
        public LycanthropeCharacter(String name, char sex, double height, int age, double strength) {
            super(name, sex, height, age, strength);
        }
    }
    static class NeutralCharacter extends Character<?, ?> {
        public NeutralCharacter(String name, char sex, double height, int age, double strength) {
            super(name, sex, height, age, strength);
        }
    }

    private Location battlefield;
    private Location gaulTown;
    private Location romainCamp;
    private Location enclosure;

    @BeforeEach
    void setUp() {
        battlefield = new Location("Battlefield", LocationType.BATTLEFIELD);
        gaulTown = new Location("Gaul Town", LocationType.GAUL_TOWN);
        romainCamp = new Location("Romain Camp", LocationType.ROMAIN_CAMP);
        enclosure = new Location("Enclosure", LocationType.ENCLOSURE);
    }

    @Test
    void constructor_createsLocationWithNameAndType() {
        assertEquals("Battlefield", battlefield.getName());
        assertEquals(LocationType.BATTLEFIELD, battlefield.getLocationType());
    }

    @Test
    void isInside_nullCharacter_returnsFalse() {
        assertFalse(battlefield.isInside(null));
    }

    @Test
    void isInside_allowedCharacter_returnsTrue() {
        RomanCharacter roman = new RomanCharacter();
        battlefield.addCharacter(roman);
        assertTrue(battlefield.isInside(roman));
    }

    @Test
    void isInside_notAddedCharacter_returnsFalse() {
        RomanCharacter roman = new RomanCharacter();
        assertFalse(battlefield.isInside(roman));
    }

    @Test
    void addCharacter_allowedCharacter_addsSuccessfully() {
        GauloisCharacter gaulois = new GauloisCharacter();
        assertTrue(gaulTown.addCharacter(gaulois));
        assertTrue(gaulTown.isInside(gaulois));
    }

    @Test
    void addCharacter_notAllowedCharacter_returnsFalse() {
        RomanCharacter roman = new RomanCharacter();
        assertFalse(gaulTown.addCharacter(roman));
        assertFalse(gaulTown.isInside(roman));
    }

    @Test
    void addCharacter_nullCharacter_returnsFalse() {
        assertFalse(battlefield.addCharacter(null));
    }

    @Test
    void addCharacter_alreadyInside_returnsFalse() {
        GauloisCharacter gaulois = new GauloisCharacter();
        assertTrue(gaulTown.addCharacter(gaulois));
        assertFalse(gaulTown.addCharacter(gaulois));
    }

    @Test
    void removeCharacter_existingCharacter_removesSuccessfully() {
        RomanCharacter roman = new RomanCharacter();
        romainCamp.addCharacter(roman);
        assertTrue(romainCamp.removeCharacter(roman));
        assertFalse(romainCamp.isInside(roman));
    }

    @Test
    void removeCharacter_notInsideCharacter_returnsFalse() {
        RomanCharacter roman = new RomanCharacter();
        assertFalse(romainCamp.removeCharacter(roman));
    }

    @Test
    void removeCharacter_nullCharacter_returnsFalse() {
        assertFalse(battlefield.removeCharacter(null));
    }

    @Test
    void getCharacters_returnsAllCharactersInside() {
        RomanCharacter roman = new RomanCharacter();
        LycanthropeCharacter lycanthrope = new LycanthropeCharacter();

        romainCamp.addCharacter(roman);
        romainCamp.addCharacter(lycanthrope);

        assertEquals(2, romainCamp.getCharacters().size());
        assertTrue(romainCamp.getCharacters().contains(roman));
        assertTrue(romainCamp.getCharacters().contains(lycanthrope));
    }

    @Test
    void battlefield_allowsAllCharacterTypes() {
        assertTrue(battlefield.addCharacter(new RomanCharacter()));
        assertTrue(battlefield.addCharacter(new GauloisCharacter()));
        assertTrue(battlefield.addCharacter(new LycanthropeCharacter()));
        assertTrue(battlefield.addCharacter(new NeutralCharacter()));
        assertEquals(4, battlefield.getCharacters().size());
    }

    @Test
    void enclosure_allowsOnlyLycanthropes() {
        assertTrue(enclosure.addCharacter(new LycanthropeCharacter()));
        assertFalse(enclosure.addCharacter(new RomanCharacter()));
        assertFalse(enclosure.addCharacter(new GauloisCharacter()));
        assertEquals(1, enclosure.getCharacters().size());
    }
}
