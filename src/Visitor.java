public interface Visitor {
    void visit(Entity entity);
    void visit(Enemy enemy);
    void visit(Warrior warrior);
    void visit(Rogue rogue);
    void visit(Mage mage);
}
