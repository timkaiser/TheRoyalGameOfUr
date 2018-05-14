import java.util.ArrayList;

abstract class Player {
    protected ArrayList<Token> tokens;
    protected int id;

    public Player(int id){
        this.id = id;

        for (int i = 0; i < 7; i++) {
            tokens.add(new Token(i, this.id));
        }
    }

    abstract Token turn(Tile[] board, int dice);

    public String   getName()   {return "Player "+id;}
    public int      getID()     {return id;}
}
