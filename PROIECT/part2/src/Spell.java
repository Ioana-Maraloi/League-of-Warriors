import java.util.Random;

public abstract class Spell implements Visitor {
    int damage, cost;
//    String name;
    boolean fire, earth, ice;
    Spell() {
        Random rand  = new Random();
        this.damage = rand.nextInt(20,30);
        this.cost = rand.nextInt(10, 15);
        fire = false;
        earth = false;
        ice = false;
    }
    public String toString() {
        return "damage: " + damage + " cost: " + cost;
    }
}
