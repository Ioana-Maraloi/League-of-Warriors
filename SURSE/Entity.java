import java.util.ArrayList;
import java.util.Random;

public abstract class Entity implements Battle, Element{
    int life, maxLife;
    int aura, maxAura;
    boolean fireImmunity, iceImmunity, earthImmunity = false;
    int Charisma, Strength, Dexterity;
    ArrayList <Spell> spells = new ArrayList<>();
    private int damage = 10;
    public void setMaxLife(){
        life = maxLife;
    }
    public void setMaxAura(){
        aura = maxAura;
    }
    public int getDamage(){
        return damage;
    }
    public void setDamage(int n){
        damage = n;
    }
    public void addPower(int nr) {
        Random rand = new Random();
        for (int i = 0; i < nr; i++) {
            int rnd = rand.nextInt(3);
            switch (rnd) {
                case 0:
                    Fire fire = new Fire();
                    spells.add(fire);
                    break;
                case 1:
                    Earth earth = new Earth();
                    spells.add(earth);
                    break;
                case 2:
                    Ice ice = new Ice();
                    spells.add(ice);
                    break;
                default:
                    break;
            }
        }
    }
    public void attack(Spell spell, Entity target) {
        if (aura > spell.cost){
            if (spell.ice && target.iceImmunity) {
                return;
            }
            if (spell.earth && target.earthImmunity) {
                return;
            }
            if (spell.fire && target.fireImmunity) {
                return;
            }
            Random rand = new Random();
            int rand1 = rand.nextInt(2);
            target.receiveDamage(spell.damage + rand1 * spell.damage / 2);
            aura = aura - spell.cost;
        } else {
            target.receiveDamage(damage);
        }
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
