import models.clanLeader.ClanLeader;
import models.factory.CharacterFactory;
import models.food.Food;
import models.location.Battlefield;
import models.location.Location;
import models.location.LocationType;
import models.people.Character;
import models.people.Druid;
import models.theater.Theater;
import models.utils.CharacterSorter;

import java.util.Scanner;

/**
 * Main entry point for the Armorique Invasion Simulation.
 * Demonstrates the various features of the simulation system.
 * @author Project Team
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Armorique Invasion Simulation ===");
        System.out.println("TD3 + TD4 Integration Project\n");

        // Create the theater
        Theater theater = new Theater();
        
        // Create locations
        Location gaulVillage = new Location("Asterix's Village", 150.0, LocationType.GAUL_TOWN);
        Location romanCamp = new Location("Aquarium Camp", 200.0, LocationType.ROMAIN_CAMP);
        Battlefield battlefield = new Battlefield("Forest Battlefield", 300.0);
        Location gaulRomanTown = new Location("Lutetia", 250.0, LocationType.GAUL_ROMAIN_VILLAGE);
        
        // Add locations to theater
        theater.addLocation(gaulVillage);
        theater.addLocation(romanCamp);
        theater.addLocation(battlefield);
        theater.addLocation(gaulRomanTown);
        
        System.out.println("Theater created with " + theater.getLocations().size() + " locations");
        System.out.println(theater);
        
        // Create characters using the Factory pattern
        System.out.println("\n=== Creating Characters (Factory Pattern) ===");
        Character asterix = CharacterFactory.createCharacter(
            CharacterFactory.CharacterType.DRUID, "Asterix", 'm', 1.60, 35, 90.0);
        Character obelix = CharacterFactory.createCharacter(
            CharacterFactory.CharacterType.BLACKSMITH, "Obelix", 'm', 2.00, 35, 150.0);
        Character marcus = CharacterFactory.createCharacter(
            CharacterFactory.CharacterType.LEGIONARY, "Marcus", 'm', 1.80, 30, 80.0);
        Character julius = CharacterFactory.createCharacter(
            CharacterFactory.CharacterType.GENERAL, "Julius", 'm', 1.85, 45, 100.0);
        
        System.out.println("Created: " + asterix.getName() + " (Druid)");
        System.out.println("Created: " + obelix.getName() + " (Blacksmith)");
        System.out.println("Created: " + marcus.getName() + " (Legionary)");
        System.out.println("Created: " + julius.getName() + " (General)");
        
        // Add characters to locations
        gaulVillage.addCharacter(asterix);
        gaulVillage.addCharacter(obelix);
        romanCamp.addCharacter(marcus);
        romanCamp.addCharacter(julius);
        
        System.out.println("\n=== Characters Added to Locations ===");
        System.out.println("Gaul Village: " + gaulVillage.getCharactersNbr() + " characters");
        System.out.println("Roman Camp: " + romanCamp.getCharactersNbr() + " characters");
        
        // Add food to location
        System.out.println("\n=== Adding Food ===");
        gaulVillage.addFood(Food.WILDBOAR);
        gaulVillage.addFood(Food.WILDBOAR);
        gaulVillage.addFood(Food.WINE);
        gaulVillage.addFood(Food.MISTLETOE);
        gaulVillage.addFood(Food.CARROT);
        gaulVillage.addFood(Food.SALT);
        gaulVillage.addFood(Food.FRESHFOURLEAFCLOVER);
        gaulVillage.addFood(Food.FAIRLYFRESHFISH);
        gaulVillage.addFood(Food.ROCKOIL);
        gaulVillage.addFood(Food.HONEY);
        gaulVillage.addFood(Food.MEAD);
        gaulVillage.addFood(Food.SECRETINGREDIENT);
        System.out.println("Added " + gaulVillage.getFoods().size() + " food items to Gaul Village");
        
        // Create clan leader
        System.out.println("\n=== Creating Clan Leader ===");
        ClanLeader chief = new ClanLeader("Vitalstatistix", 'm', 50, gaulVillage);
        chief.setTheater(theater);
        System.out.println(chief);
        
        // Prepare magic potion
        System.out.println("=== Brewing Magic Potion ===");
        boolean potionReady = chief.preparePotion();
        if (potionReady) {
            System.out.println("✓ Magic potion successfully brewed!");
            System.out.println("Kettle contains: " + gaulVillage.getMagicPotion().getPortionInKettle() + " portions");
        } else {
            System.out.println("✗ Could not brew potion (missing Druid or ingredients)");
        }
        
        // Demonstrate sorting (custom algorithm)
        System.out.println("\n=== Sorting Characters by Strength (QuickSort) ===");
        java.util.List<Character> allCharacters = new java.util.ArrayList<>();
        allCharacters.add(asterix);
        allCharacters.add(obelix);
        allCharacters.add(marcus);
        allCharacters.add(julius);
        
        java.util.List<Character> sortedByStrength = CharacterSorter.sortByStrength(allCharacters);
        System.out.println("Sorted by strength (descending):");
        for (Character c : sortedByStrength) {
            System.out.printf("  %s: %.1f strength\n", c.getName(), c.getStrength());
        }
        
        // Demonstrate Collections with Generics
        System.out.println("\n=== Demonstrating Generics & Collections ===");
        java.util.HashMap<String, Location> locationMap = new java.util.HashMap<>();
        locationMap.put("gaul", gaulVillage);
        locationMap.put("roman", romanCamp);
        locationMap.put("battle", battlefield);
        
        System.out.println("Location map created with " + locationMap.size() + " entries");
        System.out.println("Accessing 'gaul': " + locationMap.get("gaul").getName());
        
        // Demonstrate Iterator
        System.out.println("\n=== Iterating Through Characters ===");
        java.util.Iterator<Character> iterator = gaulVillage.getCharacters().iterator();
        while (iterator.hasNext()) {
            Character c = iterator.next();
            System.out.println("  - " + c.getName() + " (Health: " + c.getHealth() + ")");
        }
        
        // Display final status
        System.out.println("\n=== Final Status ===");
        System.out.println(gaulVillage);
        
        System.out.println("\n=== Simulation Complete ===");
        System.out.println("All Java concepts demonstrated:");
        System.out.println("✓ 1. Inheritance (Character hierarchy)");
        System.out.println("✓ 2. Abstract classes (Character)");
        System.out.println("✓ 3. Interfaces (Combatant, Worker, Dirigeant)");
        System.out.println("✓ 4. Generics (Collections with type parameters)");
        System.out.println("✓ 5. Collections (ArrayList, HashMap, Iterator)");
        System.out.println("✓ 6. Exceptions (Custom exceptions defined)");
        System.out.println("✓ 7. Design Patterns (Factory, Observer planned)");
        System.out.println("✓ 8. Enums (LocationType, Food, Sex)");
        System.out.println("✓ 9. Sorting (QuickSort, BubbleSort, InsertionSort)");
        System.out.println("✓ 10. Encapsulation & OOP principles");
    }
}
