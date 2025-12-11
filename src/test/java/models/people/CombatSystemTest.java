package models.people;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test combat system, death mechanics, and character bonuses
 */
class CombatSystemTest {
    
    private Merchant merchant;
    private Blacksmith blacksmith;
    private Druid druid;
    private Legionary legionary;
    private General general;
    private Werewolf alphaWolf;
    private Werewolf omegaWolf;
    
    @BeforeEach
    void setUp() {
        merchant = new Merchant("Merchant", 'm', 1.7, 30, 0.5);
        blacksmith = new Blacksmith("Blacksmith", 'm', 1.9, 35, 0.8);
        druid = new Druid("Druid", 'm', 1.7, 50, 0.6);
        legionary = new Legionary("Legionary", 'm', 1.8, 28, 0.75);
        general = new General("General", 'm', 1.85, 45, 0.9);
        alphaWolf = new Werewolf("Alpha", 'm', 2.0, 30, 0.95, Werewolf.Rank.ALPHA);
        omegaWolf = new Werewolf("Omega", 'm', 1.65, 20, 0.4, Werewolf.Rank.OMEGA);
    }
    
    @Test
    void combatBonus_merchant_returns0() {
        assertEquals(0, merchant.getCombatBonus());
    }
    
    @Test
    void combatBonus_blacksmith_returns30() {
        assertEquals(30, blacksmith.getCombatBonus());
    }
    
    @Test
    void combatBonus_druid_returns15() {
        assertEquals(15, druid.getCombatBonus());
    }
    
    @Test
    void combatBonus_legionary_returns20() {
        assertEquals(20, legionary.getCombatBonus());
    }
    
    @Test
    void combatBonus_general_returns40() {
        assertEquals(40, general.getCombatBonus());
    }
    
    @Test
    void combatBonus_alphaWolf_returns50() {
        assertEquals(50, alphaWolf.getCombatBonus());
    }
    
    @Test
    void combatBonus_omegaWolf_returnsMinus20() {
        assertEquals(-20, omegaWolf.getCombatBonus());
    }
    
    @Test
    void fight_dealsDifferentDamageToOpponents() {
        double initialHealthA = blacksmith.getHealth();
        double initialHealthB = merchant.getHealth();
        
        // Blacksmith attacks merchant
        blacksmith.fight(merchant);
        double merchantHealthAfter = merchant.getHealth();
        
        // Merchant attacks blacksmith  
        merchant.fight(blacksmith);
        double blacksmithHealthAfter = blacksmith.getHealth();
        
        // Damage should be different due to different stats and bonuses
        double merchantDamageTaken = initialHealthB - merchantHealthAfter;
        double blacksmithDamageTaken = initialHealthA - blacksmithHealthAfter;
        
        assertNotEquals(merchantDamageTaken, blacksmithDamageTaken);
        assertTrue(merchantDamageTaken > blacksmithDamageTaken); // Blacksmith should deal more damage
    }
    
    @Test
    void reduceHealth_clampAt0() {
        merchant.reduceHealth(150);
        assertEquals(0, merchant.getHealth());
    }
    
    @Test
    void reduceHealth_doesNotGoNegative() {
        merchant.reduceHealth(200);
        assertTrue(merchant.getHealth() >= 0);
    }
    
    @Test
    void reduceHealth_to0_callsDie() {
        merchant.reduceHealth(100);
        assertTrue(merchant.isDead());
        assertEquals(0, merchant.getHealth());
    }
    
    @Test
    void heal_deadCharacter_throwsException() {
        merchant.die();
        assertThrows(IllegalStateException.class, () -> {
            merchant.heal(50);
        });
    }
    
    @Test
    void heal_doesNotExceedMaxHealth() {
        merchant.heal(50);
        assertEquals(100, merchant.getHealth()); // Should cap at maxHealth
    }
    
    @Test
    void heal_aliveCharacter_increasesHealth() {
        merchant.reduceHealth(30);
        double healthBefore = merchant.getHealth();
        merchant.heal(20);
        assertEquals(healthBefore + 20, merchant.getHealth());
    }
    
    @Test
    void die_setsHealthTo0() {
        merchant.die();
        assertEquals(0, merchant.getHealth());
    }
    
    @Test
    void die_setsDead() {
        merchant.die();
        assertTrue(merchant.isDead());
    }
    
    @Test
    void fight_deadCharacter_doesNothing() {
        merchant.die();
        double opponentHealth = blacksmith.getHealth();
        merchant.fight(blacksmith);
        assertEquals(opponentHealth, blacksmith.getHealth());
    }
    
    @Test
    void fight_againstDeadCharacter_doesNothing() {
        merchant.die();
        double deadHealth = merchant.getHealth();
        blacksmith.fight(merchant);
        assertEquals(deadHealth, merchant.getHealth());
    }
    
    @Test
    void werewolf_alpha_hasIncreasedEndurance() {
        Werewolf normalWolf = new Werewolf("Normal", 'm', 2.0, 30, 0.95);
        assertTrue(alphaWolf.getEndurance() > normalWolf.getEndurance());
    }
    
    @Test
    void werewolf_rankBonus_affectsCombat() {
        // Create two identical wolves with different ranks
        Werewolf alpha = new Werewolf("Alpha", 'm', 2.0, 30, 0.8, Werewolf.Rank.ALPHA);
        Werewolf omega = new Werewolf("Omega", 'm', 2.0, 30, 0.8, Werewolf.Rank.OMEGA);
        
        Merchant target1 = new Merchant("Target1", 'm', 1.7, 30, 0.5);
        Merchant target2 = new Merchant("Target2", 'm', 1.7, 30, 0.5);
        
        double initialHealth1 = target1.getHealth();
        double initialHealth2 = target2.getHealth();
        
        alpha.fight(target1);
        omega.fight(target2);
        
        double alphaDamage = initialHealth1 - target1.getHealth();
        double omegaDamage = initialHealth2 - target2.getHealth();
        
        // Alpha should deal more damage than omega
        assertTrue(alphaDamage > omegaDamage);
    }
}
