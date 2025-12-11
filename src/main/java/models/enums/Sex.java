package models.enums;

/**
 * Enum representing the biological sex of a character.
 * @author Project Team
 */
public enum Sex {
    MALE('m'),
    FEMALE('f');

    private final char symbol;

    Sex(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    /**
     * Convert a character to a Sex enum value.
     * @param c the character ('m' or 'f')
     * @return the corresponding Sex value
     */
    public static Sex fromChar(char c) {
        return switch (Character.toLowerCase(c)) {
            case 'm' -> MALE;
            case 'f' -> FEMALE;
            default -> throw new IllegalArgumentException("Invalid sex character: " + c);
        };
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
