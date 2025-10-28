import java.util.Random;

public class Rogue extends CharacterCls {

    public Rogue(String name, int xp, int lvl) {
        super(name, xp, lvl);
        earthImmunity = true;
        Random rand = new Random();
        Dexterity = rand.nextInt(40, 50);
        Charisma = rand.nextInt(20, 40);
        Strength = rand.nextInt(20, 40);
        setDamage(15);
    }
    @Override
    public String toString() {
        return "[ROGUE] " + super.toString();
    }
    @Override
    public void receiveDamage(int damage) {
        if (Strength > 30 && Charisma > 30) {
            Random rand = new Random();
            int possibility = rand.nextInt(2);
            if (possibility == 0)
                life -= damage;
            else life -= damage / 2;
        }
        else life -= damage;
    }
    @Override
    public int getDamage() {
        if (Dexterity > 45) {
            Random rand = new Random();
            int possibility = rand.nextInt(2);
            if (possibility == 0)
                return super.getDamage() * 2;
            return super.getDamage();
        }
        return super.getDamage();
    }

    @Override
    public void accept(Visitor visitor) {
       visitor.visit(this);
    }
}
