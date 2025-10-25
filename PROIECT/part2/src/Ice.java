public class Ice extends Spell{
    public Ice(){
        super();
        ice = true;
    }
    @Override
    public String toString() {
        return "(power:ice " + super.toString() + ")";
    }
    @Override
    public void visit(Entity entity) {
    }

    @Override
    public void visit(Enemy enemy) {
        if (enemy.iceImmunity)
            return;
        enemy.receiveDamage(damage);
    }
    @Override
    public void visit(Warrior warrior) {
        if (warrior.iceImmunity)
            return;
        warrior.receiveDamage(damage);
    }
    @Override
    public void visit(Rogue rogue){
        if (rogue.iceImmunity)
            return;
        rogue.receiveDamage(damage);
    }

    @Override
    public void visit(Mage mage) {
        if (mage.iceImmunity)
            return;
        mage.receiveDamage(damage);
    }

}
