public class KI extends Player {

    public KI(int id){
        super(id);
    }

    public Token turn(Tile[] board, int dice){
        Token t;

        return tokens.get((int)(Math.random()*tokens.size()));
    };

}
