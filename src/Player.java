import java.util.ArrayList;

/**Parent class for all player, both human and AI*/
abstract class Player {
    /**player ID*/
    protected int id;

    /**list of tokens that did not reach the goal yet*/
    protected ArrayList<Token> tokens;

    /** Constructor
     * @param id Player ID
     */
    public Player(int id){
        this.id = id;

        tokens = new ArrayList<Token>();

        /** every player starts with 7 tokens*/
        for (int i = 0; i < 7; i++) {
            tokens.add(new Token(i, this.id));
        }
    }

    /** This method removes a token from player (used when goal is reached)
     * @param token Token that should be removed
     */
    public void removeToken(Token token){
        tokens.remove(token);
    }

    //========== Revlevant for Competition ===========================================================================//
    /**This method is called every time the player can move a token
     * Override this in your AI
     * @param board Game board
     * @param opponentsTokens Tokens of your opponent
     * @param dice dice result
     * @return Token that should be moved this turn
     */
    abstract Token turn(Tile[] board, ArrayList<Token> opponentsTokens, int dice);

    //================================================================================================================//

    //__getter and setter methods______________________________________________
    /** This method returns the players name
     * @return Player name (with ID)*/
    public final String getName(){return getType()+id;}

    /** This method returns the players type (relevant for wincounter)
     * @return Player type*/
    public String getType() {return "Player";}

    /** This method returns the players ID
     * @return Player ID*/
    public int getID(){return id;}

    /** This method sets the players ID
     * @param id Player ID*/
    public void setID(int id){this.id=id;}

    /** This method returns the token list for this player
     * @return List of players tokens (that have not reched the goal yet */
    public ArrayList<Token> getTokens()     {return tokens;}

    /** This method returns the number of tokens not on board (or in goal)
     * @return int numberOfTokens*/
    public int getNumberTokenNotOnBoard() {
        int number = 0;
        for (Token t:tokens) {
            if(t.getTile() == null){ number++; }
        }
        return number;
    }
}
