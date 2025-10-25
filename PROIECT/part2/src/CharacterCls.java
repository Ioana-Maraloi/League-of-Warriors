import java.util.Random;

public abstract class CharacterCls extends Entity implements Battle{
    String name;
    int xp, level;
    int enemiesDefeated = 0;
    public CharacterCls(String name, int xp, int level) {
        maxLife = 100;
        life = maxLife;
        aura = 50;
        maxAura = 50;
        this.name = name;
        this.xp = xp;
        this.level = level;
        addPower(3);
    }

    public void receiveDamage(int damage) {
        Random rand  = new Random();
        int chance = rand.nextInt(2);

        life = life - damage - damage * chance;
    }

    public int getDamage() {
        return super.getDamage();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n");
        sb.append("XP: ").append(xp).append("\n");
        sb.append("Level: ").append(level).append("\n");
        sb.append("life: ").append(life).append("\n");
        sb.append("Aura: ").append(aura).append("\n");
        sb.append("Damage: ").append(getDamage()).append("\n");
        for (Spell spell : spells) {
            sb.append("Spell: ").append(spell).append("\n");
        }
        return sb.toString();
    }
}
