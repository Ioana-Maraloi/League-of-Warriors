import java.util.Random;

public class Warrior extends CharacterCls {
    public Warrior(String name, int xp, int lvl) {
        super(name, xp, lvl);
        fireImmunity = true;
        Random rand = new Random();
        Strength = rand.nextInt(40, 50);
        Charisma = rand.nextInt(20, 40);
        Dexterity = rand.nextInt(20, 40);
        super.setDamage(20);
    }
    @Override
    public String toString() {
        return "[WARRIOR] " + super.toString();
    }
    @Override
    public void receiveDamage(int damage) {
        if (Charisma > 30 && Dexterity > 30) {
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
        if (Strength > 45) {
            Random rand = new Random();
            int possibility = rand.nextInt(2);
            if (possibility == 0) {
                return super.getDamage() * 2;
            }
            return super.getDamage();
        }
        return super.getDamage();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
