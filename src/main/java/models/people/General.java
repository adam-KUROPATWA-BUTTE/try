package models.people;

public class General extends Character implements Roman, Combatant, Dirigeant {

    public General(String name, char sex, double height, int age, double strength) {
        super(name, sex, height, age, strength);
    }

    /**
     * Initiates a battle against another Character.
     *
     * @param opponent The opponent with whom to begin a battle.
     */
    @Override
    public void combat(Character opponent) {
        System.out.println(this.getName() + " initiates combat with " + opponent.getName());
    }

    /**
     * Manages the locations belonging to this Dirigeant.
     */
    @Override
    public void manage() {
        System.out.println(this.getName() + " redirects funds to his army.");
    }
    
    /**
     * General bonus: +40% attack, tactical command
     */
    @Override
    protected int getCombatBonus() {
        return 40; // +40% attack
    }
}
