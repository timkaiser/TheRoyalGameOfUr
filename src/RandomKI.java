public class RandomKI extends Player {
    public RandomKI(int id){
        super(id);
    }

    /**KI chooses random token to move*/
    @Override
    public Token turn(Tile[] board, int dice){
        while(true){
            int index = (int)(Math.random()*tokens.size());
            if(Main.isValidMove(board,tokens.get(index),dice)){return tokens.get(index);}

        }
    }


    @Override
    public String getName(){ return "Randy Random ("+id+")";}
    public static String getType(){return "Random";}
}
