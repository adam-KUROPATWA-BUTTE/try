package models.people;

public class InnKeeper extends Character implements Gaul, Worker {

    public InnKeeper(String name, char sex, double height, int age, double strength) {
        super(name, sex, height, age, strength);
    }

    /**
     * Do the work specified by the character.
     */
    @Override
    public void work() {
        System.out.println(this.getName() + " maintains their inn.");
    }
    
    /**
     * Innkeeper bonus: +50% healing effectiveness with food
     * No combat bonus
     */
    @Override
    protected int getCombatBonus() {
        return 0;
    }
}
