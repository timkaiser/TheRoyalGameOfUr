import java.util.ArrayList;

/** This AI chooses a random token to move. */
public class RandomAI extends Player {
    /* Player attribute:
     *
     *  protected int id;      //player ID
     *
     *  protected ArrayList<Token> tokens;     //list of tokens that did not reach the goal yet
     */

    /** Constructor
     * !!! Do not change the ID or the super() call !!!
     *
     *@param id Player ID
     */
    public RandomAI(int id){
        super(id);
    }


    /**This method is called every time the player can move a token
     * Program the main part of your AI here.
     * @param board Game board
     * @param opponentsTokens Tokens of your opponent
     * @param dice dice result
     * @return Token that should be moved this turn
     */
    @Override
    public Token turn(Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        //==== Space for own code ======================================================
        while(true){
            int index = (int)(Math.random()*tokens.size());
            if(Main.isValidMove(board,tokens.get(index),dice)){return tokens.get(index);}

        }
        //==============================================================================
    }

    /** This method returns the players type (relevant for wincounter)
     * @return Player type*/
    @Override
    public String getType(){ return "Randy Random";}

}

