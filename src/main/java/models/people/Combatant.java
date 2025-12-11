package models.people;

public interface Combatant {

    /**
     * Initiates a battle against another Character.
     * @param opponent The opponent with whom to begin a battle.
     */
    void combat(Character opponent);

}
