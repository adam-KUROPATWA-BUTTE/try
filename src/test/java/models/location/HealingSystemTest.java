package models.location;

import models.food.Food;
import models.people.Merchant;
import models.people.Druid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test healing system with resource requirements
 */
class HealingSystemTest {
    
    private Location village;
    private Merchant merchant;
    private Druid druid;
    
    @BeforeEach
    void setUp() {
        village = new Location("Test Village", 100.0, LocationType.GAUL_TOWN);
        merchant = new Merchant("TestMerchant", 'm', 1.7, 30, 0.5);
        druid = new Druid("TestDruid", 'm', 1.7, 50, 0.6);
    }
    
    @Test
    void healCharacter_withHerbs_heals50HP() {
        village.addHealingHerbs(1);
        merchant.reduceHealth(60);
        
        double healthBefore = merchant.getHealth();
        village.healCharacter(merchant);
        
        assertEquals(healthBefore + 50, merchant.getHealth());
        assertEquals(0, village.getHealingHerbs());
    }
    
    @Test
    void healCharacter_withFood_heals30HP() {
        village.addFood(Food.WILDBOAR);
        merchant.reduceHealth(50);
        
        double healthBefore = merchant.getHealth();
        village.healCharacter(merchant);
        
        assertEquals(healthBefore + 30, merchant.getHealth());
        assertTrue(village.getFoods().isEmpty());
    }
    
    @Test
    void healCharacter_deadCharacter_throwsException() {
        village.addHealingHerbs(1);
        merchant.die();
        
        assertThrows(IllegalStateException.class, () -> {
            village.healCharacter(merchant);
        });
    }
    
    @Test
    void healCharacter_noResources_throwsException() {
        merchant.reduceHealth(50);
        
        assertThrows(IllegalStateException.class, () -> {
            village.healCharacter(merchant);
        });
    }
    
    @Test
    void healCharacter_prefersHerbsOverFood() {
        village.addHealingHerbs(1);
        village.addFood(Food.WILDBOAR);
        merchant.reduceHealth(60);
        
        double healthBefore = merchant.getHealth();
        village.healCharacter(merchant);
        
        // Should use herbs (50 HP) not food (30 HP)
        assertEquals(healthBefore + 50, merchant.getHealth());
        assertEquals(0, village.getHealingHerbs());
        assertEquals(1, village.getFoods().size()); // Food not consumed
    }
    
    @Test
    void spawnHerbsIfNeeded_spawnAfter3Turns() {
        assertEquals(0, village.getHealingHerbs());
        
        village.spawnHerbsIfNeeded(); // Turn 1
        assertEquals(0, village.getHealingHerbs());
        
        village.spawnHerbsIfNeeded(); // Turn 2
        assertEquals(0, village.getHealingHerbs());
        
        village.spawnHerbsIfNeeded(); // Turn 3
        assertEquals(2, village.getHealingHerbs()); // Spawns 2 herbs
    }
    
    @Test
    void spawnHerbsIfNeeded_onlyInVillages() {
        Location battlefield = new Location("Battlefield", 100.0, LocationType.BATTLEFIELD);
        
        battlefield.spawnHerbsIfNeeded();
        battlefield.spawnHerbsIfNeeded();
        battlefield.spawnHerbsIfNeeded();
        
        assertEquals(0, battlefield.getHealingHerbs()); // No herbs spawn on battlefield
    }
    
    @Test
    void healCharacters_skipsDeadCharacters() {
        village.addCharacter(merchant);
        village.addCharacter(druid);
        
        merchant.die();
        druid.reduceHealth(30);
        
        double druidHealthBefore = druid.getHealth();
        
        // Should not throw exception when healing all characters
        assertDoesNotThrow(() -> village.healCharacters(20));
        
        // Merchant stays dead
        assertEquals(0, merchant.getHealth());
        assertTrue(merchant.isDead());
        
        // Druid gets healed
        assertEquals(druidHealthBefore + 20, druid.getHealth());
    }
    
    @Test
    void addHealingHerbs_increasesCount() {
        village.addHealingHerbs(5);
        assertEquals(5, village.getHealingHerbs());
        
        village.addHealingHerbs(3);
        assertEquals(8, village.getHealingHerbs());
    }
}
