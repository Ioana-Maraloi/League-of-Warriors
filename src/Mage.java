import java.util.Random;

public class Mage extends CharacterCls {
    public Mage(String name, int xp, int lvl) {
        super(name, xp, lvl);
        iceImmunity = true;
        Random rand = new Random();
        Charisma = rand.nextInt(40, 50);
        Strength = rand.nextInt(20, 40);
        Dexterity = rand.nextInt(20, 40);
        setDamage(10);
    }
    @Override
    public String toString() {
        return "[MAGE] " + super.toString();
    }
    @Override
    public void receiveDamage(int damage) {
        if (Strength > 30 && Dexterity > 30) {
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
        if (Charisma > 45) {
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
