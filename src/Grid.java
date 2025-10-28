import java.util.ArrayList;
import java.util.Random;

public class Grid {
    static int length;
    static int height;
    static int x;
    static int y;
    ArrayList<ArrayList<Cell>> matrix;
//    static int numberGames = 0;
    private Grid() {
            matrix = generate();
//            matrix = generateGivenMatrix();
    }
    public static Grid getInstance() {
        return new Grid();
    }
    private static ArrayList<ArrayList<Cell>> generateGivenMatrix() {
        length = 5;
        height = 5;
        ArrayList<ArrayList<Cell>>grid = new ArrayList<>();
        for(int i = 0; i < length; i++) {
            ArrayList<Cell> line = new ArrayList<>();
            for(int j = 0; j < height; j++) {
                Cell cell = new Cell(i, j);
                line.add(cell);
            }
            grid.add(line);
        }
//        adding the player
        grid.get(0).get(0).state = CellEntityType.PLAYER;
        grid.get(0).get(0).visited = true;
        y = 0;
        x = 0;
        grid.get(0).get(0).contains.replace(0, 1, "P");
//        adding the sanctuaries
        grid.get(0).get(3).state = CellEntityType.SANCTUARY;
        grid.get(0).get(3).contains.replace(0, 1, "S");
        grid.get(1).get(3).state = CellEntityType.SANCTUARY;
        grid.get(1).get(3).contains.replace(0, 1, "S");
        grid.get(2).get(0).state = CellEntityType.SANCTUARY;
        grid.get(2).get(0).contains.replace(0, 1, "S");
        grid.get(4).get(3).state = CellEntityType.SANCTUARY;
        grid.get(4).get(3).contains.replace(0, 1, "S");
//        adding the enemies
        grid.get(3).get(4).contains.replace(0, 1, "E");
        grid.get(3).get(4).state = CellEntityType.ENEMY;
//         adding the portal
        grid.get(4).get(4).state = CellEntityType.PORTAL;
        grid.get(4).get(4).contains.replace(0, 1, "F");
        return grid;
    }
    private static ArrayList<ArrayList<Cell>> generate() {
        Random rand = new Random();
        length = rand.nextInt(5, 10);
        height = rand.nextInt(5, 10);
        ArrayList<ArrayList<Cell>>grid = new ArrayList<>();
        for(int i = 0; i < height; i++) {
            ArrayList<Cell> line = new ArrayList<>();
            for(int j = 0; j < length; j++) {
                Cell cell = new Cell(i, j);
                line.add(cell);
            }
            grid.add(line);
        }
//        adding the player

        int rand1 = rand.nextInt(height);
        int rand2 = rand.nextInt(length);

        grid.get(rand1).get(rand2).state = CellEntityType.PLAYER;
        grid.get(rand1).get(rand2).visited = true;
        y = rand1;
        x = rand2;
        grid.get(rand1).get(rand2).contains.replace(0, 1, "P");
        boolean used =false;
//        adding the portal
        while(!used) {
            rand1 = rand.nextInt(height);
            rand2 = rand.nextInt(length);
            if (grid.get(rand1).get(rand2).state == CellEntityType.VOID) {
                grid.get(rand1).get(rand2).state = CellEntityType.PORTAL;
                grid.get(rand1).get(rand2).contains.replace(0, 1, "F");
                used = true;
            }
        }
//        adding the sanctuaries
        used = false;
        for (int i = 0 ; i < (length * height) / 10; i++) {
            while (!used) {
                rand1 = rand.nextInt(height);
                rand2 = rand.nextInt(length);

                if (grid.get(rand1).get(rand2).state == CellEntityType.VOID) {
                    grid.get(rand1).get(rand2).contains.replace(0,1,"S");
                    grid.get(rand1).get(rand2).state = CellEntityType.SANCTUARY;
                    used = true;
                }
            }
            used = false;

        }
//        adding the enemies
        for (int i = 0 ; i < (length * height) / 5; i++) {
            while (!used) {
                rand1 = rand.nextInt(height);
                rand2 = rand.nextInt(length);
                if (grid.get(rand1).get(rand2).state == CellEntityType.VOID) {
                    grid.get(rand1).get(rand2).contains.replace(0, 1, "E");
                    grid.get(rand1).get(rand2).state = CellEntityType.ENEMY;
                    used = true;
                }
            }
            used = false;
        }
        return grid;
    }
    public String checkEnemySanctuaryPortal(int y, int x){
        if (matrix.get(y).get(x).state == CellEntityType.SANCTUARY) {
            return "sanctuary";
        }
        if (matrix.get(y).get(x).state == CellEntityType.PORTAL) {
            return "portal";
        }
        if (matrix.get(y).get(x).state == CellEntityType.ENEMY) {
            return "enemy";
        }
        return "clear";
    }
    public String goNorth(){
        matrix.get(y).get(x).state = CellEntityType.VOID;
        matrix.get(y).get(x).contains.replace(0, 1, "V");
        matrix.get(y).get(x).visited = true;
        y -= 1;
        if (!checkEnemySanctuaryPortal(y, x).equals("clear"))
            return checkEnemySanctuaryPortal(y, x);
        matrix.get(y).get(x).state = CellEntityType.PLAYER;
        matrix.get(y).get(x).visited = true;
        matrix.get(y).get(x).contains.replace(0, 1,"P");
        return "successful";
    }
    public String goSouth(){
        matrix.get(y).get(x).state = CellEntityType.VOID;
        matrix.get(y).get(x).contains.replace(0, 1,"V");
        matrix.get(y).get(x).visited = true;
        y += 1;
        if (!checkEnemySanctuaryPortal(y, x).equals("clear"))
            return checkEnemySanctuaryPortal(y, x);
        matrix.get(y).get(x).state = CellEntityType.PLAYER;
        matrix.get(y).get(x).visited = true;
        matrix.get(y).get(x).contains.replace(0, 1, "P");
        return "successful";
    }
    public String goWest(){
        matrix.get(y).get(x).state = CellEntityType.VOID;
        matrix.get(y).get(x).contains.replace(0, 1, "V");
        matrix.get(y).get(x).visited = true;
        x -= 1;
        if (!checkEnemySanctuaryPortal(y, x).equals("clear"))
            return checkEnemySanctuaryPortal(y, x);
        matrix.get(y).get(x).state = CellEntityType.PLAYER;
        matrix.get(y).get(x).visited = true;
        matrix.get(y).get(x).contains.replace(0,  1, "P");
        return "successful";
    }
    public String goEast(){
        matrix.get(y).get(x).state = CellEntityType.VOID;
        matrix.get(y).get(x).visited = true;
        matrix.get(y).get(x).contains.replace(0, 1, "V");
        x += 1;
        if (!checkEnemySanctuaryPortal(y, x).equals("clear"))
            return checkEnemySanctuaryPortal(y, x);
        matrix.get(y).get(x).state = CellEntityType.PLAYER;
        matrix.get(y).get(x).visited = true;
        matrix.get(y).get(x).contains.replace(0, 1, "P");
        return "successful";
    }
    public ArrayList<Integer> getPlayer(){
        ArrayList<Integer> returned = new ArrayList<>();
        returned.add(x);
        returned.add(y);
        return returned;
    }
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                output.append(matrix.get(i).get(j).toString()).append(" ");
            }
//            display actual grid for testing
            output.append("     ");
            for (int j = 0; j < length; j++) {
                output.append(matrix.get(i).get(j).ShowActualGrid()).append(" ");
            }
            output.append("\n");
        }
        return output.toString();
    }
}