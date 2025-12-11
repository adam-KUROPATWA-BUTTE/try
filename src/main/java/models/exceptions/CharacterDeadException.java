package models.exceptions;

/**
 * Exception thrown when a character is already dead and cannot perform actions.
 * @author Project Team
 */
public class CharacterDeadException extends RuntimeException {
    
    public CharacterDeadException(String characterName) {
        super(String.format("Character '%s' is dead and cannot perform this action", characterName));
    }
}
