package models.people;

public class Prefect extends Character implements Roman, Dirigeant {

    public Prefect(String name, char sex, double height, int age, double strength) {
        super(name, sex, height, age, strength);
    }

    /**
     * Manages the locations belonging to this Dirigeant.
     */
    @Override
    public void manage() {
        System.out.println(this.getName() + " does nothing.");
    }
    
    /**
     * Prefect bonus: Leadership (+15% to allies)
     * No direct combat bonus
     */
    @Override
    protected int getCombatBonus() {
        return 0;
    }
}
