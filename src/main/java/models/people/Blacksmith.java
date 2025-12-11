package models.people;

public class Blacksmith extends Character implements Gaul, Worker {

    public Blacksmith(String name, char sex, double height, int age, double strength) {
        super(name, sex, height, age, strength);
    }

    /**
     * Do the work specified by the character.
     */
    @Override
    public void work() {
        System.out.println(this.getName() + " hits his hammer on hot iron.");
    }
}
