package models.clanLeader;

import models.location.Location;
import models.location.LocationType;
import models.potion.MagicPotion;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ClanLeaderTest {

    ClanLeader clanLeader = new ClanLeader("Timothée", 'd', 21, new Location("bleu", 12.5, LocationType.GAUL_TOWN));

    @Test
    void scanLocation() {
        assertEquals("Location: bleu (GAUL_TOWN)\n" +
                "Superficie: 12.5\n" +
                "Chef: aucun\n" +
                "Personnages (0):\n" +
                "Aliments (0):\n", clanLeader.scanLocation());
    }

    @Test
    void createCharacter() {
    }

    @Test
    void healCharacters() {
    }

    @Test
    void feedCharacters() {
    }

    @Test
    void preparePotion() {
    }

    @Test
    void makeCharacterDrink() {
    }

    @Test
    void moveCharacter() {
    }

    @Test
    void garmin() throws IOException {
        assertEquals("Type \"help\" for a list of commands.", clanLeader.garmin("", "", ""));
        assertEquals(":)", clanLeader.garmin("help", "", ""));
        assertEquals("Character succefully created", clanLeader.garmin("create", "", ""));
        System.out.println(clanLeader.garmin("scan", "", ""));
    }
}