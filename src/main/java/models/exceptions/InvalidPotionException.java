package models.exceptions;

/**
 * Exception thrown when trying to brew a potion without proper ingredients or Druid.
 * @author Project Team
 */
public class InvalidPotionException extends RuntimeException {
    
    public InvalidPotionException(String message) {
        super(message);
    }
}
