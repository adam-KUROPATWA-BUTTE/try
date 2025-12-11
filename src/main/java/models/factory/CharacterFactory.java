package models.factory;

import models.people.*;

/**
 * Factory class for creating different types of characters.
 * Implements the Factory design pattern as required by project specifications.
 * @author Project Team
 */
public class CharacterFactory {

    /**
     * Character type enumeration.
     */
    public enum CharacterType {
        BLACKSMITH,
        DRUID,
        GENERAL,
        INNKEEPER,
        LEGIONARY,
        MERCHANT,
        PREFECT,
        WEREWOLF
    }

    /**
     * Create a character based on the specified type.
     * @param type the type of character to create
     * @param name the name of the character
     * @param sex the sex of the character ('m' or 'f')
     * @param height the height of the character
     * @param age the age of the character
     * @param strength the strength of the character
     * @return the created character
     * @throws IllegalArgumentException if an unknown character type is provided
     */
    public static models.people.Character createCharacter(CharacterType type, String name, char sex, 
                                           double height, int age, double strength) {
        return switch (type) {
            case BLACKSMITH -> new Blacksmith(name, sex, height, age, strength);
            case DRUID -> new Druid(name, sex, height, age, strength);
            case GENERAL -> new General(name, sex, height, age, strength);
            case INNKEEPER -> new InnKeeper(name, sex, height, age, strength);
            case LEGIONARY -> new Legionary(name, sex, height, age, strength);
            case MERCHANT -> new Merchant(name, sex, height, age, strength);
            case PREFECT -> new Prefect(name, sex, height, age, strength);
            case WEREWOLF -> new Werewolf(name, sex, height, age, strength);
        };
    }

    /**
     * Create a character from a string type name.
     * @param typeName the name of the character type
     * @param name the name of the character
     * @param sex the sex of the character
     * @param height the height of the character
     * @param age the age of the character
     * @param strength the strength of the character
     * @return the created character
     * @throws IllegalArgumentException if an unknown character type is provided
     */
    public static models.people.Character createCharacter(String typeName, String name, char sex, 
                                           double height, int age, double strength) {
        CharacterType type = CharacterType.valueOf(typeName.toUpperCase());
        return createCharacter(type, name, sex, height, age, strength);
    }

    /**
     * Create a random character with given attributes.
     * @param name the name of the character
     * @param sex the sex of the character
     * @param height the height of the character
     * @param age the age of the character
     * @param strength the strength of the character
     * @return a randomly selected character type
     */
    public static models.people.Character createRandomCharacter(String name, char sex, 
                                                  double height, int age, double strength) {
        CharacterType[] types = CharacterType.values();
        CharacterType randomType = types[(int) (Math.random() * types.length)];
        return createCharacter(randomType, name, sex, height, age, strength);
    }
}
