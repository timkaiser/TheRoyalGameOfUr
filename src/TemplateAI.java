import java.util.ArrayList;

/** This is a template class for an AI. You can extend it by changing the turn() method and giving it an awesome name.
 * Also: NO CHEATING!!! */
public class TemplateAI extends Player{
    /* Player attribute:
    *
    *  protected int id;      //player ID
    *
    *  protected ArrayList<Token> tokens;     //list of tokens that did not reach the goal yet
    */

    //==== Space for own attributes ======================================================

    //==============================================================================

    /** Constructor
     * !!! Do not change the ID or the super() call !!!
     *
     *@param id Player ID
     */
    public TemplateAI(int id){
        super(id);

        //==== Space for own code ======================================================

        //==============================================================================
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
        //TODO: Program AI here
        return null;
        //==============================================================================
    }

    /** This method returns the players type (relevant for wincounter)
     * @return Player type*/
    @Override
    public String getType(){
        //TODO: give awesome name
        return "NotSoAwesomeName";
    }

    //==== Space for own methods ======================================================

    //==============================================================================

}
