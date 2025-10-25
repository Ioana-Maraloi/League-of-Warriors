import java.util.Random;

public class Enemy extends Entity implements Battle{
    Enemy(){
        super();
        Random rand = new Random();
        int rand1 = rand.nextInt(75, 100);
        life = rand1;
        rand1 = rand.nextInt(75, 100);
        aura = rand1;
        setDamage(rand.nextInt(20, 30));
//       choosing the spells
        rand1 = rand.nextInt(0,2);
        for ( int i = 0; i < rand1; i++){
            Ice ice = new Ice();
            spells.add(ice);
        }
        rand1 = rand.nextInt(0, 2);
        for ( int i = 0; i < rand1; i++){
            Fire fire = new Fire();
            spells.add(fire);
        }
        rand1 = rand.nextInt(0, 2);
        for ( int i = 0; i < rand1; i++){
            Earth earth = new Earth();
            spells.add(earth);
        }
        rand1 = rand.nextInt(1, 4);
        switch(rand1){
            case 1:
                fireImmunity = true;
                break;
            case 2:
                iceImmunity = true;
                break;
            case 3:
                earthImmunity = true;
                break;
        }
    }
    @Override
    public void receiveDamage(int damage) {
        life -= damage;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[ENEMY] " + "life: ").append(life).append(" aura: ").append(aura).append(" normalDamage: ").append(getDamage()).append("\n");
        if (fireImmunity) {
            s.append("fire immunity\n");
        }
        if (iceImmunity) {
            s.append("ice immunity\n");
        }
        if (earthImmunity) {
            s.append("earth immunity\n");
        }
        s.append("spells: ").append(spells.size()).append("\n");
        return s.toString();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
