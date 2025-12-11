package gui.controllers;

import gui.ArmoriqueApp;
import gui.utils.GameState;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.clanLeader.ClanLeader;
import models.factory.CharacterFactory;
import models.food.Food;
import models.location.Location;
import models.location.LocationType;
import models.people.Character;
import models.theater.Theater;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Controller for the Setup Screen
 */
public class SetupController implements Initializable {
    
    @FXML private TextField theaterNameField;
    @FXML private Spinner<Integer> locationCountSpinner;
    @FXML private Slider simulationSpeedSlider;
    @FXML private Label speedLabel;
    @FXML private Slider defaultAreaSlider;
    @FXML private Label areaLabel;
    @FXML private Slider initialFoodSlider;
    @FXML private Label foodLabel;
    @FXML private Spinner<Integer> charactersPerLocationSpinner;
    @FXML private Slider initialStrengthSlider;
    @FXML private Label strengthLabel;
    @FXML private CheckBox enablePotionsCheckBox;
    @FXML private CheckBox enableLycanthropesCheckBox;
    @FXML private CheckBox enableBattlesCheckBox;
    
    private GameState gameState;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameState = ArmoriqueApp.getGameState();
        
        // Set default values
        theaterNameField.setText("Armorique");
        
        // Bind labels to sliders
        simulationSpeedSlider.valueProperty().addListener((obs, oldVal, newVal) -> 
            speedLabel.setText(String.format("%.1fx", newVal.doubleValue()))
        );
        
        defaultAreaSlider.valueProperty().addListener((obs, oldVal, newVal) -> 
            areaLabel.setText(String.valueOf(newVal.intValue()))
        );
        
        initialFoodSlider.valueProperty().addListener((obs, oldVal, newVal) -> 
            foodLabel.setText(String.valueOf(newVal.intValue()))
        );
        
        initialStrengthSlider.valueProperty().addListener((obs, oldVal, newVal) -> 
            strengthLabel.setText(String.format("%.2f", newVal.doubleValue()))
        );
    }
    
    @FXML
    private void onBack() {
        ArmoriqueApp.showMainMenu();
    }
    
    @FXML
    private void onQuickStart() {
        setupDefaultGame();
        ArmoriqueApp.showGameView();
    }
    
    @FXML
    private void onStartGame() {
        setupCustomGame();
        ArmoriqueApp.showGameView();
    }
    
    /**
     * Setup a game with default configuration
     */
    private void setupDefaultGame() {
        Theater theater = new Theater();
        
        // Create default locations
        Location gaulVillage = new Location("Asterix's Village", 150.0, LocationType.GAUL_TOWN);
        Location romanCamp = new Location("Aquarium Camp", 200.0, LocationType.ROMAIN_CAMP);
        Location battlefield = new Location("Forest Battlefield", 300.0, LocationType.BATTLEFIELD);
        Location gaulRomanTown = new Location("Lutetia", 250.0, LocationType.GAUL_ROMAIN_VILLAGE);
        Location enclosure = new Location("Dark Forest", 180.0, LocationType.ENCLOSURE);
        
        theater.addLocation(gaulVillage);
        theater.addLocation(romanCamp);
        theater.addLocation(battlefield);
        theater.addLocation(gaulRomanTown);
        theater.addLocation(enclosure);
        
        // Add characters to Gaul Village
        Character asterix = CharacterFactory.createCharacter(
            CharacterFactory.CharacterType.DRUID, "Asterix", 'm', 1.60, 35, 0.9);
        Character obelix = CharacterFactory.createCharacter(
            CharacterFactory.CharacterType.BLACKSMITH, "Obelix", 'm', 2.00, 35, 0.95);
        Character getafix = CharacterFactory.createCharacter(
            CharacterFactory.CharacterType.DRUID, "Getafix", 'm', 1.70, 60, 0.7);
        
        gaulVillage.addCharacter(asterix);
        gaulVillage.addCharacter(obelix);
        gaulVillage.addCharacter(getafix);
        
        // Add characters to Roman Camp
        Character marcus = CharacterFactory.createCharacter(
            CharacterFactory.CharacterType.LEGIONARY, "Marcus", 'm', 1.80, 30, 0.8);
        Character julius = CharacterFactory.createCharacter(
            CharacterFactory.CharacterType.GENERAL, "Julius", 'm', 1.85, 45, 0.85);
        
        romanCamp.addCharacter(marcus);
        romanCamp.addCharacter(julius);
        
        // Add some food to locations
        Food[] basicFoods = {Food.WILDBOAR, Food.WINE, Food.MISTLETOE, Food.CARROT, Food.SALT};
        for (Food food : basicFoods) {
            gaulVillage.addFood(food);
            romanCamp.addFood(food);
        }
        
        // Create clan leader
        ClanLeader chief = new ClanLeader("Vitalstatistix", 'm', 50, gaulVillage);
        chief.setTheater(theater);
        
        // Update game state
        gameState.setTheater(theater);
        gameState.setPlayerClanLeader(chief);
        gameState.setTheaterName("Armorique");
        gameState.initializeNewGame();
        gameState.updateTotalPopulation();
        gameState.addEvent("Game started with default configuration");
    }
    
    /**
     * Setup a game with custom configuration from UI
     */
    private void setupCustomGame() {
        Theater theater = new Theater();
        Random random = new Random();
        
        // Get configuration from UI
        String theaterName = theaterNameField.getText();
        int locationCount = locationCountSpinner.getValue();
        double defaultArea = defaultAreaSlider.getValue();
        int foodCount = (int) initialFoodSlider.getValue();
        int charactersPerLoc = charactersPerLocationSpinner.getValue();
        double strength = initialStrengthSlider.getValue();
        
        // Location types to cycle through
        LocationType[] locationTypes = {
            LocationType.GAUL_TOWN,
            LocationType.ROMAIN_CAMP,
            LocationType.BATTLEFIELD,
            LocationType.GAUL_ROMAIN_VILLAGE,
            LocationType.ENCLOSURE
        };
        
        String[] locationNames = {
            "Village", "Camp", "Battlefield", "Town", "Enclosure",
            "Settlement", "Fortress", "Clearing", "Outpost", "Grove"
        };
        
        Location firstLocation = null;
        
        // Create locations
        for (int i = 0; i < locationCount; i++) {
            LocationType type = locationTypes[i % locationTypes.length];
            String name = locationNames[i % locationNames.length] + " " + (i + 1);
            Location location = new Location(name, defaultArea, type);
            
            if (firstLocation == null) {
                firstLocation = location;
            }
            
            theater.addLocation(location);
            
            // Add characters
            for (int j = 0; j < charactersPerLoc; j++) {
                try {
                    Character character = createRandomCharacterForLocation(type, strength, random);
                    location.addCharacter(character);
                } catch (Exception e) {
                    // Skip if character can't be added
                }
            }
            
            // Add food
            Food[] availableFoods = Food.values();
            for (int j = 0; j < foodCount && j < availableFoods.length; j++) {
                location.addFood(availableFoods[random.nextInt(availableFoods.length)]);
            }
        }
        
        // Create clan leader
        ClanLeader chief = new ClanLeader("Player Chief", 'm', 40, firstLocation);
        chief.setTheater(theater);
        
        // Update game state
        gameState.setTheater(theater);
        gameState.setPlayerClanLeader(chief);
        gameState.setTheaterName(theaterName);
        gameState.setSimulationSpeed(simulationSpeedSlider.getValue());
        gameState.initializeNewGame();
        gameState.updateTotalPopulation();
        gameState.addEvent("Game started with custom configuration");
        gameState.addEvent(locationCount + " locations created");
    }
    
    /**
     * Create a random character appropriate for the location type
     */
    private Character createRandomCharacterForLocation(LocationType type, double strength, Random random) {
        String[] names = {"Marcus", "Julius", "Asterix", "Obelix", "Getafix", "Vitalstatistix", 
                          "Fulliautomatix", "Cacofonix", "Impedimenta", "Panacea"};
        String name = names[random.nextInt(names.length)] + random.nextInt(100);
        char sex = random.nextBoolean() ? 'm' : 'f';
        double height = 1.5 + random.nextDouble() * 0.5;
        int age = 20 + random.nextInt(40);
        
        CharacterFactory.CharacterType characterType;
        
        // Choose appropriate character type for location
        if (type == LocationType.GAUL_TOWN) {
            CharacterFactory.CharacterType[] gaulTypes = {
                CharacterFactory.CharacterType.DRUID,
                CharacterFactory.CharacterType.BLACKSMITH,
                CharacterFactory.CharacterType.INNKEEPER,
                CharacterFactory.CharacterType.MERCHANT
            };
            characterType = gaulTypes[random.nextInt(gaulTypes.length)];
        } else if (type == LocationType.ROMAIN_CAMP || type == LocationType.ROMAIN_TOWN) {
            CharacterFactory.CharacterType[] romanTypes = {
                CharacterFactory.CharacterType.LEGIONARY,
                CharacterFactory.CharacterType.PREFECT,
                CharacterFactory.CharacterType.GENERAL
            };
            characterType = romanTypes[random.nextInt(romanTypes.length)];
        } else if (type == LocationType.ENCLOSURE) {
            characterType = CharacterFactory.CharacterType.WEREWOLF;
        } else {
            // For battlefield and gallo-roman towns, mix
            CharacterFactory.CharacterType[] allTypes = CharacterFactory.CharacterType.values();
            characterType = allTypes[random.nextInt(allTypes.length)];
        }
        
        return CharacterFactory.createCharacter(characterType, name, sex, height, age, strength);
    }
}
