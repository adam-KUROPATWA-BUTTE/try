package models.people;

public class Werewolf extends Character implements Combatant {
    
    public enum Rank {
        ALPHA,  // α rank: +50% damage, +30% endurance
        BETA,   // β rank: +30% damage, +15% endurance
        GAMMA,  // γ rank: +15% damage
        OMEGA   // ω rank: -20% damage
    }
    
    private final Rank rank;

    public Werewolf(String name, char sex, double height, int age, double strength) {
        this(name, sex, height, age, strength, Rank.GAMMA); // Default to gamma
    }
    
    public Werewolf(String name, char sex, double height, int age, double strength, Rank rank) {
        super(name, sex, height, age, strength);
        this.rank = rank;
        applyRankBonuses();
    }
    
    private void applyRankBonuses() {
        switch (rank) {
            case ALPHA:
                setEndurance(getEndurance() * 1.30); // +30% endurance
                break;
            case BETA:
                setEndurance(getEndurance() * 1.15); // +15% endurance
                break;
            case GAMMA:
            case OMEGA:
                // No endurance bonus
                break;
        }
    }
    
    public Rank getRank() {
        return rank;
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
    
    /**
     * Werewolf combat bonus based on rank:
     * α rank: +50% damage
     * β rank: +30% damage
     * γ rank: +15% damage
     * ω rank: -20% damage
     */
    @Override
    protected int getCombatBonus() {
        return switch (rank) {
            case ALPHA -> 50;   // +50% damage
            case BETA -> 30;    // +30% damage
            case GAMMA -> 15;   // +15% damage
            case OMEGA -> -20;  // -20% damage
        };
    }
}
