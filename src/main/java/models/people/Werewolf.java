package models.people;

public class Werewolf extends Character implements Combatant {

    public Werewolf(String name, char sex, double height, int age, double strength) {
        super(name, sex, height, age, strength);
    }

    /**
     * Initiates a battle against another Character.
     *
     * @param opponent The opponent with whom to begin a battle.
     */
    @Override
    public void combat(Character opponent) {
        System.out.println(this.getName() + " begins shredding apart " + opponent.getName());
    }
}
