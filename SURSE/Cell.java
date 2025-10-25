public class Cell {
    int ox, oy;
    StringBuffer contains = new StringBuffer();
    CellEntityType state;
    boolean visited = false;
    public Cell(int x, int y) {
        contains.append("N");
        this.ox = x;
        this.oy = y;
        state = CellEntityType.VOID;
    }

    @Override
    public String toString() {
        if (visited)
            return this.contains.toString();
        return "*";
    }
    public String ShowActualGrid(){
        return contains.toString();
    }
}
