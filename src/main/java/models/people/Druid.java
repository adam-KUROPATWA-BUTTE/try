package models.people;

public class Druid extends Character implements Gaul, Combatant, Dirigeant, Worker {

    public Druid(String name, char sex, double height, int age, double strength) {
        super(name, sex, height, age, strength);
    }

    @Override
    public void combat(Character opponent) {
        System.out.println(this.getName() + " combats " + opponent.getName());
    }

    @Override
    public void work() {
        System.out.println(this.getName() + " does druid work.");
    }

    @Override
    public void manage() {
        System.out.println(this.getName() + " does druid dirigeant work.");
    }

    public void cookPotion() {
        System.out.println(this.getName() + " cooks a potion.");
    }
    
    /**
     * Druid bonus: Brew potions, +25% potion effects, magic attacks
     */
    @Override
    protected int getCombatBonus() {
        return 15; // Magic attack bonus
    }
}
