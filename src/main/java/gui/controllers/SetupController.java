package gui.controllers;

import gui.ArmoriqueApp;
import gui.utils.GameState;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.clanLeader.ClanLeader;
import models.factory.CharacterFactory;
import models.food.Food;
import models.location.Battlefield;
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
        Location romanCity = new Location("Rome", 300.0, LocationType.ROMAIN_TOWN);
        Battlefield battlefield = new Battlefield("Forest Battlefield", 300.0);
        Location gaulRomanTown = new Location("Lutetia", 250.0, LocationType.GAUL_ROMAIN_VILLAGE);
        Location enclosure = new Location("Dark Forest", 180.0, LocationType.ENCLOSURE);

        theater.addLocation(gaulVillage);
        theater.addLocation(romanCamp);
        theater.addLocation(romanCity);
        theater.addLocation(battlefield);
        theater.addLocation(gaulRomanTown);
        theater.addLocation(enclosure);

        // Add characters to Gaul Village: 1 Merchant, 1 Innkeeper, 1 Blacksmith, 1 Druid
        Character merchant = CharacterFactory.createCharacter(
                CharacterFactory.CharacterType.MERCHANT, "Economix", 'm', 1.65, 40, 0.6);
        Character innkeeper = CharacterFactory.createCharacter(
                CharacterFactory.CharacterType.INNKEEPER, "Impedimenta", 'f', 1.70, 38, 0.65);
        Character blacksmith = CharacterFactory.createCharacter(
                CharacterFactory.CharacterType.BLACKSMITH, "Fulliautomatix", 'm', 2.00, 35, 0.95);
        Character druid = CharacterFactory.createCharacter(
                CharacterFactory.CharacterType.DRUID, "Getafix", 'm', 1.70, 60, 0.7);

        gaulVillage.addCharacter(merchant);
        gaulVillage.addCharacter(innkeeper);
        gaulVillage.addCharacter(blacksmith);
        gaulVillage.addCharacter(druid);

        // Add characters to Roman Camp: 1 Legionary, 1 General
        Character legionary = CharacterFactory.createCharacter(
                CharacterFactory.CharacterType.LEGIONARY, "Marcus", 'm', 1.80, 30, 0.8);
        Character general = CharacterFactory.createCharacter(
                CharacterFactory.CharacterType.GENERAL, "Julius", 'm', 1.85, 45, 0.85);

        romanCamp.addCharacter(legionary);
        romanCamp.addCharacter(general);

        // Add characters to Roman City: 1 Legionary, 1 Prefect, 1 General
        Character legionary2 = CharacterFactory.createCharacter(
                CharacterFactory.CharacterType.LEGIONARY, "Cassius", 'm', 1.78, 28, 0.82);
        Character prefect = CharacterFactory.createCharacter(
                CharacterFactory.CharacterType.PREFECT, "Pontius", 'm', 1.75, 50, 0.7);
        Character general2 = CharacterFactory.createCharacter(
                CharacterFactory.CharacterType.GENERAL, "Caesar", 'm', 1.88, 52, 0.9);

        romanCity.addCharacter(legionary2);
        romanCity.addCharacter(prefect);
        romanCity.addCharacter(general2);

        // Add lycanthropes to Enclosure: Pack with hierarchy (1 alpha, 1 beta, 2 gamma, 1 omega)
        models.people.Werewolf alpha = new models.people.Werewolf("Fenrir", 'm', 2.1, 35, 0.95, models.people.Werewolf.Rank.ALPHA);
        models.people.Werewolf beta = new models.people.Werewolf("Grayback", 'm', 1.95, 30, 0.85, models.people.Werewolf.Rank.BETA);
        models.people.Werewolf gamma1 = new models.people.Werewolf("Shadowfang", 'm', 1.88, 25, 0.75, models.people.Werewolf.Rank.GAMMA);
        models.people.Werewolf gamma2 = new models.people.Werewolf("Nightclaw", 'f', 1.82, 23, 0.72, models.people.Werewolf.Rank.GAMMA);
        models.people.Werewolf omega = new models.people.Werewolf("Runt", 'm', 1.65, 20, 0.5, models.people.Werewolf.Rank.OMEGA);

        enclosure.addCharacter(alpha);
        enclosure.addCharacter(beta);
        enclosure.addCharacter(gamma1);
        enclosure.addCharacter(gamma2);
        enclosure.addCharacter(omega);

        // Note: Battlefield starts empty as per requirements

        // Add some food to locations
        Food[] basicFoods = {Food.WILDBOAR, Food.WINE, Food.MISTLETOE, Food.CARROT, Food.SALT};
        for (Food food : basicFoods) {
            gaulVillage.addFood(food);
            romanCamp.addFood(food);
            romanCity.addFood(food);
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

            // Create Battlefield instances for battlefield type, regular Location for others
            Location location;
            if (type == LocationType.BATTLEFIELD) {
                location = new Battlefield(name, defaultArea);
            } else {
                location = new Location(name, defaultArea, type);
            }

            if (firstLocation == null) {
                firstLocation = location;
            }

            theater.addLocation(location);

            // Add characters ONLY to non-battlefield locations (battlefields start empty)
            if (type != LocationType.BATTLEFIELD) {
                for (int j = 0; j < charactersPerLoc; j++) {
                    try {
                        Character character = createRandomCharacterForLocation(type, strength, random);
                        location.addCharacter(character);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Could not create character for location " + name + ": " + e.getMessage());
                        // Skip this character and continue
                    }
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