import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/** This is a template class for an AI. You can extend it by changing the turn() method and giving it an awesome name.
 * Also: NO CHEATING!!! */
public class HeuristicAI extends Player{
    /* Player attribute:
    *
    *  protected int id;      //player ID
    *
    *  protected ArrayList<Token> tokens;     //list of tokens that did not reach the goal yet
    */

    //==== Space for own attributes ======================================================

    //====================================================================================

    /** Constructor
     * !!! Do not change the ID or the super() call !!!
     *
     *@param id Player ID
     */

    public HeuristicAI(int id){
        super(id);
        // tree = new TreeNode();
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
        return getMoves(board, dice).get(0);
        //==============================================================================
    }

    /** This method returns the players type (relevant for wincounter)
     * @return Player type*/
    @Override
    public String getType(){
        return "HeuristicBot";
    }

    private ArrayList<Token> getMoves(Tile[] board, int dice){
        ArrayList<Token> possibleMoves = (ArrayList<Token>)tokens.clone();
        possibleMoves.removeIf(v -> !Main.isValidMove(board,v,dice));
        Collections.sort(possibleMoves, new Comparator<Token>() {
            @Override
            public int compare(Token move1, Token move2)
            {
                return (heuristic(board, move2, dice) - heuristic(board, move1, dice));
            }
        });
        return possibleMoves;
    }

    private int heuristic(Tile[] board, Token moveToken, int dice){
        Tile lastTile = moveToken.getTile();
        Tile nextTile = null;
        int finished = 0;

        if (dice == 0){
            nextTile = lastTile;
        } else if (lastTile == null) {
            nextTile = board[dice - 1];
        } else {
            if (lastTile.getID()+dice==14) {
                finished = 1;
            } else {
                nextTile = board[lastTile.getID() + dice];
            }
        }

        int tokenScore = 0;
        for (int i = 0; i < board.length; i++){
            int multiplier = 1;
            if(i == 3 || i == 7){
                multiplier = 2;
            }
            if(board[i] == lastTile) {
                // Skip tile, not occupied anymore
                continue; 
            }
            if(board[i] == nextTile) {
                // Set score, occupied now
                tokenScore += i * multiplier;
                continue;
            }
            if(board[i].isOccupiedByOwn(id)) {
                tokenScore += i * multiplier;
                continue;
            }
            if(board[i].isOccupiedByOpponent(id)) {
                tokenScore -= i * multiplier;
                continue;
            }
        }
        return tokenScore + finished * 14;
    }
}