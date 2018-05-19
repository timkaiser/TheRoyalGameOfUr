import java.util.ArrayList;

public class PrincessOfUrAI extends Player {
    /* Player attribute:
     *
     *  protected int id;      //player ID
     *
     *  protected ArrayList<Token> tokens;     //list of tokens that did not reach the goal yet
     */

    //==== Space for own attributes ======================================================
    private  final int PROGRESS = 0;

    private  final int  KICKOUT = 1;
    private  final int  KICKOUT_PROGRESS = 2;

    private  final int  GET_KICKED_OUT_NOW = 3;
    private  final int  GET_KICKED_OUT_THEN = -4;

    private  final int  ENTERING_GAME = 5;
    private  final int  LEAVING_GAME = 6;

    private  final int  ENTERING_PRIVATE = 7;
    private  final int  LEAVING_PRIVATE = 8;

    private  final int  ENTERING_SAFESPOT = 9;
    private  final int  LEAVING_SAFESPOT = 10;

    private  final int  ENTERING_DOUBLEROLL = 11;
    private  final int  LEAVING_DOUBLEROLL = 12;

    private  final int  BLOCK_OWN_NOW = 13;
    private  final int  BLOCK_OWN_THEN = 14;

    private int weights[][] = new int[15][15];


    private int wincounter = 0;
    //==============================================================================

    /** Constructor
     * !!! Do not change the ID or the super() call !!!
     *
     *@param id Player ID
     */
    public PrincessOfUrAI(int id){
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
        if(dice == 0){ return null; }
        double maxUtility = -100000;
        Token bestToken = null;
        for(Token token:tokens){
            if(Main.isValidMove(board, token, dice)){
                Tile start = token.getTile();
                Tile finish = (start==null)? board[dice-1] : (start.getID()+dice>=14) ? null  : board[start.getID()+dice];
                double utility = 0;

                for(int i = 0; i < weights.length; i++) {
                    for(int j = 0; j < weights[i].length; j++) {
                        utility += weights[i][j]*probablility(i,token,start,finish,board,opponentsTokens,dice)*probablility(j,token,start,finish,board,opponentsTokens,dice);
                    }
                }

                if(utility > maxUtility){
                    maxUtility = utility;
                    bestToken = token;
                }
            }
        }

        return bestToken;
        //==============================================================================
    }

    /** This method returns the players type (relevant for wincounter)
     * @return Player type*/
    @Override
    public String getType(){
        String s = "{";
        for(int i = 0; i < weights.length; i++) {
            s+="{";
            for(int j = 0; j < weights[i].length; j++) {
                s += weights[i][j]+",";
            }
            s+="},\n";
        }

        s.substring(0, s.lastIndexOf(','));

        s+= "}";

        return s;
    }

    //==== Space for own methods ======================================================
    private double probablility(int attribute, Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        double result = 0;
        switch(attribute) {
            case PROGRESS:
                return (start == null) ? 0 : (start.getID() + 1) / 14;
            case KICKOUT:
                if (finish == null || finish.isPrivate() || finish.getID() == 7) return 0;
                return (finish.isOccupiedByOpponent(this.id)) ? 1 : 0;
            case KICKOUT_PROGRESS:
                if (finish == null || finish.isPrivate() || finish.getID() == 7) return 0;
                return (!finish.isOccupiedByOpponent(this.id)) ? 0 : finish.getID();

            case GET_KICKED_OUT_NOW:
                result = 0;

                if (start == null || start.isPrivate() || start.getID() == 7) {
                    return 0;
                }

                //dice results:
                for (int i = 1; i <= 4; i++) {
                    tokensloop:
                    for (Token oT : opponentsTokens) {
                        if (oT.getTileId() + dice == start.getID()) {
                            result += getProbability(i);
                            break tokensloop;
                        }
                    }
                }

                return result;

            case GET_KICKED_OUT_THEN:
                result = 0;

                if (finish == null || finish.isPrivate() || finish.getID() == 7) {
                    return 0;
                }

                //dice results:
                for (int i = 1; i <= 4; i++) {
                    tokensloop:
                    for (Token oT : opponentsTokens) {
                        if (oT.getTileId() + dice == finish.getID()) {
                            result += getProbability(i);
                        }
                    }
                }

                return result;


            case ENTERING_GAME:
                return (token.getTile() == null) ? 1 : 0;

            case LEAVING_GAME:
                return (token.getTile() == null) ? 0 : ((token.getTile().getID() + dice) == 14) ? 1 : 0;

            case ENTERING_PRIVATE:
                if (finish == null) {
                    return 0;
                }
                return finish.isPrivate() ? 1 : 0;

            case LEAVING_PRIVATE:
                return (start == null) ? 0 : (start.isPrivate()) ? 1 : 0;

            case ENTERING_SAFESPOT:
                if (finish == null) {
                    return 0;
                }
                return finish.getID() == 7 ? 1 : 0;

            case LEAVING_SAFESPOT:
                return (token.getTile() == null) ? 0 : (token.getTile().getID() == 7) ? 1 : 0;


            case ENTERING_DOUBLEROLL:
                if (finish == null) {
                    return 0;
                }
                return finish.isPrivate() ? 1 : 0;


            case LEAVING_DOUBLEROLL:
                return (token.getTile() == null) ? 0 : (token.getTile().isRollAgain()) ? 1 : 0;

            case BLOCK_OWN_NOW:
                //TODO
                return 0;

            case BLOCK_OWN_THEN:
                //TODO
                return 0;

            default:
                return 0;
        }
    }


    @Override
    public void removeToken(Token token){
        super.removeToken(token);

        if(tokens.isEmpty()){
            wincounter++;
        }
    }
    //-------------------------------

    public static double getProbability(int dice){
        switch (dice) {
            case 0:
                return 1 / 16;
            case 1:
                return 4 / 16;
            case 2:
                return 6 / 16;
            case 3:
                return 4 / 16;
            case 4:
                return 1 / 16;
            default:
                return 0;
        }
    }

    public void resetTokens(){
        tokens = new ArrayList<Token>();

        /** every player starts with 7 tokens*/
        for (int i = 0; i < 7; i++) {
            tokens.add(new Token(i, this.id));
        }
    }

    public void setWeights(int[][] weights){
        this.weights = weights;
    }

    public int[][] getWeights(){
        return weights;
    }

    public int getWincounter() { return wincounter; }

    //==============================================================================
}
