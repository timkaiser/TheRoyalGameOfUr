import java.util.ArrayList;

/** This AI always chooses to move the first possible token (in the list). */
public class SimpleAI extends Player {
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
    public SimpleAI(int id){
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

        for(Token token: tokens){
            if(Main.isValidMove(board,token,dice)){ return token; }
        }

        return null;
        //==============================================================================
    }

    /** This method returns the players type (relevant for wincounter)
     * @return Player type*/
    @Override
    public String getType(){ return "Sandy Simpelton";}

}


