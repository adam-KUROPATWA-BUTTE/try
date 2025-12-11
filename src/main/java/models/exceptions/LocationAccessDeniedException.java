package models.exceptions;

/**
 * Exception thrown when a character is not allowed in a specific location.
 * @author Project Team
 */
public class LocationAccessDeniedException extends RuntimeException {
    
    public LocationAccessDeniedException(String message) {
        super(message);
    }
    
    public LocationAccessDeniedException(String characterName, String locationType) {
        super(String.format("Character '%s' is not allowed in location type: %s", 
            characterName, locationType));
    }
}
