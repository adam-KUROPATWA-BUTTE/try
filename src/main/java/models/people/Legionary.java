package models.people;

public class Legionary extends Character implements Roman, Combatant {

    public Legionary(String name, char sex, double height, int age, double strength) {
        super(name, sex, height, age, strength);
    }

    /**
     * Initiates a battle against another Character.
     *
     * @param opponent The opponent with whom to begin a battle.
     */
    @Override
    public void combat(Character opponent) {
        System.out.println(this.getName() + " begins fighting " + opponent.getName());
    }
}
