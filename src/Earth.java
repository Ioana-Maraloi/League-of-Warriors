public class Earth extends Spell{
    public Earth(){
        super();
        earth = true;
    }
    @Override
    public String toString() {
        return "(power:earth " + super.toString() + ")";
    }

    @Override
    public void visit(Entity entity) {
    }

    @Override
    public void visit(Enemy enemy) {
        if (enemy.earthImmunity)
            return;
        enemy.receiveDamage(damage);
    }
    @Override
    public void visit(Warrior warrior) {
        if (warrior.earthImmunity)
            return;
        warrior.receiveDamage(damage);
    }
    @Override
    public void visit(Rogue rogue){
        if (rogue.earthImmunity)
            return;
        rogue.receiveDamage(damage);
    }

    @Override
    public void visit(Mage mage) {
        if (mage.earthImmunity)
            return;
        mage.receiveDamage(damage);
    }
}
