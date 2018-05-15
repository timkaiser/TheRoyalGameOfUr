public class KI extends Player {

    public KI(int id){
        super(id);
    }

    @Override
    public Token turn(Tile[] board, int dice){
        for(Token token: tokens){
            if(Main.isValidMove(board,token,dice)){ return token; }
        }

        return null;//.get((int)(Math.random()*tokens.size()));
    };

}
