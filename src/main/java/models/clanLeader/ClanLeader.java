package models.clanLeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import models.food.Food;
import models.location.Battlefield;
import models.location.Location;
import models.location.LocationRestriction;
import models.people.Blacksmith;
import models.people.Character;
import models.people.Druid;
import models.people.General;
import models.people.InnKeeper;
import models.people.Legionary;
import models.people.Merchant;
import models.people.Prefect;
import models.people.Werewolf;
import models.theater.Theater;

public class ClanLeader {
    public String name;
    public char gender;
    public int age;
    private Location location;
    private Theater theater;

    private static final String[] CHARACTERSTYPE  = {
            "Blacksmith", "Druid", "General", "Innkeeper", "Legionary", "Merchant", "Prefect", "Werewolf"
    };

    private static final String[] NAMES = {
            "Aelia", "Cassius", "Livia", "Marcus", "Tara", "Gwen",
            "Ulric", "Serena", "Hadrian", "Lucius", "Freya"
    };

    public ClanLeader(String name, char gender, int age, Location location) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.location = location;
    }

    /**
     * Get a random type of characters
     * @param rand as the random variable
     * @return a type of character
     */
    private String getRandomType(Random rand) {

        if (getLocation() == null) {
            return CHARACTERSTYPE[rand.nextInt(CHARACTERSTYPE.length)];
        }
        Location location = getLocation();
        List<String> allowedTypes = new ArrayList<>();
        for (String type : CHARACTERSTYPE) {
            Character dummy = createCharacterInstance(List.of(type, "Tmp", 'm', 1.0, 30, 1.0));
            if (LocationRestriction.isAllowed(location.getType(), dummy)) {
                allowedTypes.add(type);
            }
        }
        if (allowedTypes.isEmpty()) {
            return CHARACTERSTYPE[rand.nextInt(CHARACTERSTYPE.length)];
        }
        return allowedTypes.get(rand.nextInt(allowedTypes.size()));
    }


    /**
     * get a random name from the list
     * @param rand as the random variable
     * @return a name as a string
     */
    private String getRandomName(Random rand) {
        return NAMES[rand.nextInt(NAMES.length)];
    }

    /**
     * Assign or update the Location for this ClanLeader.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Returns the Location associated with this ClanLeader, or null if none.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Assign or update the Location for this ClanLeader.
     */
    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    /**
     * Returns the Location associated with this ClanLeader, or null if none.
     */
    public Theater getTheater() {
        return theater;
    }

    public String getName() {
        return name;
    }

    public char getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    /**
     * Scan the location by printing all the information : location parameter, characters list, food list
     */
    public String scanLocation() {
        if (getLocation() == null) return "No location assigned";
        return getLocation().toString();
    }

    /**
     * Create a character with the param values
     * @param data as the list of data for the character
     * @return the character
     */
    private Character createCharacterInstance(List<Object> data) {
        String type = data.get(0).toString();
        String name = data.get(1).toString();
        char sex = (char) data.get(2);
        double height = (Double) data.get(3);
        int age = (int) data.get(4);
        double strength = (Double) data.get(5);
        return switch (type) {
            case "Blacksmith" -> new Blacksmith(name, sex, height, age, strength);
            case "Druid" -> new Druid(name, sex, height, age, strength);
            case "General" -> new General(name, sex, height, age, strength);
            case "Innkeeper", "InnKeeper" -> new InnKeeper(name, sex, height, age, strength);
            case "Legionary" -> new Legionary(name, sex, height, age, strength);
            case "Merchant" -> new Merchant(name, sex, height, age, strength);
            case "Prefect" -> new Prefect(name, sex, height, age, strength);
            case "Werewolf" -> new Werewolf(name, sex, height, age, strength);
            default -> throw new IllegalArgumentException("Unknown character type: " + type);
        };
    }

    /**
     * Create a new character in the clanLeader location
     */
    public void createCharacter(List<Object> data) {
        if (!getLocation().addCharacter(createCharacterInstance(data))) {
            System.out.println(name+" already exists");
        }
    }

    /**
     * Function that give random data to create a character
     * @return all the data (type of character, name, sex, height, age, strength
     */
    public List<Object> randomCharacterData() {
        Random rand = new Random();
        String type;
        type = getRandomType(rand);
        String name;
        name = getRandomName(rand);
        char sex;
        if  (rand.nextBoolean()) {sex = 'f';}
        else {sex = 'm';}
        double height;
        height = rand.nextDouble();
        int age;
        age = rand.nextInt(100) + 1;
        double strength;
        strength = rand.nextDouble();
        return List.of(type, name, sex, height, age, strength);
    }

    /**
     * Heal all character in the clanLeader location
     */
    public void healCharacters() {
        if (getLocation() == null) return;
        getLocation().healCharacters(10.0);
    }

    /**
     * Feed all character in the clanLeader location
     */
    public void feedCharacters() {
        if (getLocation() == null) return;
        getLocation().feedCharacters();
    }

    /**
     * Make a druid prepare potion
     * @return a boolean to know is the potion is prepared
     */
    public boolean preparePotion() {
        // require a druid present in the location to prepare potion
        if (getLocation() == null) return false;
        boolean haveDruid = false;
        for (Character c : getLocation().getCharacters()) {
            if (c instanceof Druid) { haveDruid = true; break; }
        }
        if (!haveDruid) return false;

        ArrayList<Food> ingredient = getLocation().getMagicPotion().sortFoods(new ArrayList<>(getLocation().getFoods()));
        for (Food food : ingredient) {
            if (getLocation().getMagicPotion().addToCurrentRecipes(food)) {
                getLocation().removeFood(food);
                if (getLocation().getMagicPotion().checkIfFullRecipe()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Makes a character drink magic potion
     */
    public void makeCharacterDrink(Character character) {
        character.drinkPotion(getLocation().getMagicPotion());
    }

    /**
     * Move a character to a battlefield or an enclosure.
     */
    //transférer un personnage de son lieu à un champ de bataille ou un enclos
    public void moveCharacter(Location newLocation, Character character) {
        getLocation().removeCharacter(character);
        newLocation.addCharacter(character);
    }

    /**
     * Transfer a character to a battlefield with origin tracking.
     * This method properly tracks where the character came from for later return.
     * 
     * @param character the character to transfer
     * @param battlefield the destination battlefield
     * @return true if transfer was successful
     */
    public boolean transferCharacterToBattlefield(Character character, Battlefield battlefield) {
        if (getLocation() == null || getTheater() == null) {
            return false;
        }
        
        return getTheater().transferCharacterToBattlefield(character, getLocation(), battlefield);
    }

    @Override
    public String toString() {
        String loc = getLocation() == null ? "aucun" : getLocation().getName();
        return "Chef de clan: " + getName() + "\n" +
            "Genre: " + getGender() + "\n" +
            "Age: " + getAge() + "\n" +
            "Lieu assigner: " + loc + "\n";
    }

    public String garmin(String input, String charId, String locId) {
        //String name = System.console().readLine();
        String output = "Type \"help\" for a list of commands.";
        switch (input) {
            case "help":
                return ":)";
            case "heal":
                healCharacters();
                return "All Characters got healed";
            case "feed":
                feedCharacters();
                return "All Characters got fed";
            case "drink":
                //TODO add a way to select the character
                return "The selected Character have drink potion";
            case "magic":
                if (preparePotion()) {return "Potion prepared";}
                else {return "No potion prepared";}
            case "move":
                Character character = getLocation().getCharacters().get(Integer. parseInt(charId));
                Location newLocation = getTheater().getLocations().get(Integer. parseInt(locId));
                moveCharacter(newLocation, character);
                return "The selected Character have move to selected location";
            case "create":
                createCharacter(randomCharacterData());
                return "Character successfully created";
            case "scan":
                return scanLocation();
            case "info":
                return toString();
        }
        return output;
    }
}
