import java.util.ArrayList;
import java.util.Random;

public class Game {
    public static boolean newGrid;
    CharacterCls myCharacter;
    public static Grid grid;
    DisplayGrid displayGrid;
    static Account myAccount;
    private static Game game;
    private Game(CharacterCls character) {
        myCharacter = character;
        grid = Grid.getInstance();
    }
    public static void newGrid(){
        grid = Grid.getInstance();
    }
    public static Game getInstance(CharacterCls myCharacter) {
        if (game == null) {
            game = new Game(myCharacter);
        }
        return game;
    }
    public void setAccount(Account account) {
        myAccount = account;
    }
    public static Account getMyAccount() {
        return myAccount;
    }
    public ArrayList<String> possibleMoves() {
        ArrayList<Integer> position = grid.getPlayer();
        Integer x = position.get(0);
        Integer y = position.get(1);
        ArrayList<String> moves = new ArrayList<>();

        if (x != Grid.length - 1) {
            moves.add("Move-right");
        }
        if (x != 0) {
            moves.add("Move-left");
        }
        if (y != Grid.height - 1) {
            moves.add("Move-down");
        }
        if (y != 0) {
            moves.add("Move-up");
        }
        return moves;
    }
    public void sanctuary() {
        Random rand = new Random();
        if (myCharacter.life < myCharacter.maxLife)
            myCharacter.life = rand.nextInt(myCharacter.life, myCharacter.maxLife);
        if (myCharacter.aura < myCharacter.maxAura)
            myCharacter.aura = rand.nextInt(myCharacter.aura, myCharacter.maxAura);
        myCharacter.addPower(2);

        grid.matrix.get(Grid.y).get(Grid.x).state = CellEntityType.PLAYER;
        grid.matrix.get(Grid.y).get(Grid.x).visited = true;

        grid.matrix.get(Grid.y).get(Grid.x).contains.replace(0, 1, "P");
    }
    public void run(CharacterCls character) {
        displayGrid = new DisplayGrid(character);
        displayGrid.setVisible(true);
        displayGrid.updateGrid();
    }

    public static void main(String[] args) {
        CreateLoginForm createLoginForm = new CreateLoginForm();
        createLoginForm.setVisible(true);
    }
}
