public class SimpleKI extends Player {
    /**IMPORTANT!!! DO NOT REMOVE*/
    private static int wincounter = 0;
    /**##########################*/

    public SimpleKI(int id){
        super(id);
    }
    /**KI always chooses first vaild token to move*/
    @Override
    public Token turn(Tile[] board, int dice){
        for(Token token: tokens){
            if(Main.isValidMove(board,token,dice)){ return token; }
        }

        return null;//.get((int)(Math.random()*tokens.size()));
    };

    @Override
    public String getName(){ return "Simpelton ("+id+")";}


}
