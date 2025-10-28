public class Fire extends Spell{
    public Fire(){
        super();
        fire = true;
    }

    @Override
    public String toString() {
        return "(power:fire " + super.toString() + ")";
    }
    @Override
    public void visit(Entity entity) {

    }

    @Override
    public void visit(Enemy enemy) {
        if (enemy.fireImmunity)
            return;
        enemy.receiveDamage(damage);
    }
    @Override
    public void visit(Warrior warrior) {
        if (warrior.fireImmunity)
            return;
        warrior.receiveDamage(damage);
    }
    @Override
    public void visit(Rogue rogue){
        if (rogue.fireImmunity)
            return;
        rogue.receiveDamage(damage);
    }

    @Override
    public void visit(Mage mage) {
        if (mage.fireImmunity)
            return;
        mage.receiveDamage(damage);
    }
}
