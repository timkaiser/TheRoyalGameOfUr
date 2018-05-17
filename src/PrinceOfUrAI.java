import java.util.ArrayList;

public class PrinceOfUrAI extends Player {
    /* Player attribute:
     *
     *  protected int id;      //player ID
     *
     *  protected ArrayList<Token> tokens;     //list of tokens that did not reach the goal yet
     */

    //==== Space for own attributes ======================================================
    private  Integer wProgress = 702;

    private  Integer wKickOut = 1916;
    private  Integer wKickOutProgress = 446;

    private  Integer wGetKickedOutNow = 1598;
    private  Integer wGetKickedOutThen = -1766;

    private  Integer wEnteringGame = 1972;
    private  Integer wLeavingGame = 1022;

    private  Integer wEnterPrivate = 762;
    private  Integer wLeavePrivate = 414;

    private  Integer wEnterSafespot = 1494;
    private  Integer wLeaveSafespot = -1000;

    private  Integer wEnterDoubleroll = 92;
    private  Integer wLeaveDoubleroll = 204;

    private  Integer wBlockOwnNow = 0;
    private  Integer wBlockOwnThen = 0;


    private int wincounter = 0;
    //==============================================================================

    /** Constructor
     * !!! Do not change the ID or the super() call !!!
     *
     *@param id Player ID
     */
    public PrinceOfUrAI(int id){
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
                double utility =
                        + wProgress             *   pProgress(token,start,finish,board,opponentsTokens,dice)

                        + wKickOut              *   pKickOut(token,start,finish,board,opponentsTokens,dice)
                        + wKickOutProgress      *   pKickOutProgress(token,start,finish,board,opponentsTokens,dice)

                        + wGetKickedOutNow      *   pGetKickedOutNow(token,start,finish,board,opponentsTokens,dice)
                        + wGetKickedOutThen     *   pGetKickedOutThen(token,start,finish,board,opponentsTokens,dice)

                        + wEnteringGame         *   pEnteringGame(token,start,finish,board,opponentsTokens,dice)
                        + wLeavingGame          *   pLeavingGame(token,start,finish,board,opponentsTokens,dice)

                        + wEnterPrivate         *   pEnterPrivate(token,start,finish,board,opponentsTokens,dice)
                        + wLeavePrivate         *   pLeavePrivate(token,start,finish,board,opponentsTokens,dice)

                        + wEnterSafespot        *   pEnterSafespot(token,start,finish,board,opponentsTokens,dice)
                        + wLeaveSafespot   *   pLeaveLeaveSafespot(token,start,finish,board,opponentsTokens,dice)

                        + wEnterDoubleroll      *   pEnterDoubleroll(token,start,finish,board,opponentsTokens,dice)
                        + wLeaveDoubleroll      *   pLeaveDoubleroll(token,start,finish,board,opponentsTokens,dice)

                        + wBlockOwnNow          *   pBlockOwnNow(token,start,finish,board,opponentsTokens,dice)
                        + wBlockOwnThen         *   pBlockOwnThen(token,start,finish,board,opponentsTokens,dice)   ;

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
        return "Prince of Ur (" + wProgress +"," + wKickOut +"," + wKickOutProgress +"," + wGetKickedOutNow +"," + wGetKickedOutThen+","
                + wEnteringGame +"," + wLeavingGame +"," + wEnterPrivate +"," + wLeavePrivate +"," + wEnterSafespot +","
                + wLeaveSafespot +"," + wEnterDoubleroll +"," + wLeaveDoubleroll +"," + wBlockOwnNow +"," + wBlockOwnThen + ")";
    }

    //==== Space for own methods ======================================================
    private double pProgress(Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        return (start==null)? 0 : (start.getID()+1)/14;
    }

    private double pKickOut(Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        if(finish==null || finish.isPrivate() || finish.getID()==7) return 0;
        return (finish.isOccupiedByOpponent(this.id))?1:0;
    }
    private double pKickOutProgress(Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        if(finish==null || finish.isPrivate() || finish.getID()==7) return 0;
        return (!finish.isOccupiedByOpponent(this.id))?0:finish.getID();
    }

    private double pGetKickedOutNow(Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        double result = 0;

        if(start == null || start.isPrivate() || start.getID()==7){ return 0; }

        //dice results:
        for (int i = 1; i <=4 ; i++) {
            tokensloop:
            for(Token oT: opponentsTokens){
                if(oT.getTileId()+dice == start.getID()){
                    result += getProbability(i);
                    break tokensloop;
                }
            }
        }

        return result;
    }

    private double pGetKickedOutThen(Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        double result = 0;

        if(finish == null || finish.isPrivate() || finish.getID()==7){ return 0; }

        //dice results:
        for (int i = 1; i <=4 ; i++) {
            tokensloop:
            for(Token oT: opponentsTokens){
                if(oT.getTileId()+dice == finish.getID()){
                    result += getProbability(i);
                }
            }
        }

        return result;
    }

    private double pEnteringGame(Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        return (token.getTile()==null)? 1 : 0;
    }

    private double pLeavingGame(Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        return (token.getTile()==null)? 0 : ((token.getTile().getID()+dice)==14)? 1 : 0;
    }

    private double pEnterPrivate(Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        if(finish == null){ return 0; }
        return finish.isPrivate()? 1:0;
    }

    private double pLeavePrivate(Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        return (start==null)? 0 : (start.isPrivate())? 1 : 0;
    }

    private double pEnterSafespot(Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        if(finish == null){ return 0; }
        return finish.getID()==7 ? 1:0;
    }

    private double pLeaveLeaveSafespot(Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        return (token.getTile()==null)? 0 : (token.getTile().getID()==7)? 1 : 0;
    }

    private double pEnterDoubleroll(Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        if(finish == null){ return 0; }
        return finish.isPrivate()? 1:0;
    }

    private double pLeaveDoubleroll(Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        return (token.getTile()==null)? 0 : (token.getTile().isRollAgain())? 1 : 0;
    }

    private double pBlockOwnNow(Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        //TODO
        return 0;
    }

    private double pBlockOwnThen(Token token, Tile start, Tile finish, Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        //TODO
        return 0;
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

    public void setWeights(int[] weights){
        if(weights.length != 15){
            System.out.println("ERROR in PrinceOfUrAI.setWeights()");
            return;
        }

        wProgress = weights[0];

        wKickOut = weights[1];
        wKickOutProgress = weights[2];

        wGetKickedOutNow = weights[3];
        wGetKickedOutThen = weights[4];

        wEnteringGame = weights[5];
        wLeavingGame = weights[6];

        wEnterPrivate = weights[7];
        wLeavePrivate = weights[8];

        wEnterSafespot = weights[9];
        wLeaveSafespot = weights[10];

        wEnterDoubleroll = weights[11];
        wLeaveDoubleroll = weights[12];

        wBlockOwnNow = weights[13];
        wBlockOwnThen = weights[14];


    }

    public int[] getWeights(){
        int[] weights = new int[14];

        weights[0] = wProgress           ;

        weights[1] = wKickOut            ;
        weights[2] = wKickOutProgress    ;

        weights[3] = wGetKickedOutNow    ;
        weights[4] = wGetKickedOutThen   ;

        weights[5] = wEnteringGame       ;
        weights[6] = wLeavingGame        ;

        weights[7] = wEnterPrivate       ;
        weights[8] = wLeavePrivate       ;

        weights[9] = wEnterSafespot      ;
        weights[10]= wLeaveSafespot ;

        weights[11]= wEnterDoubleroll    ;
        weights[12]= wLeaveDoubleroll    ;

        weights[13]= wBlockOwnNow        ;
        weights[14]= wBlockOwnThen       ;

        return weights;
    }

    public int getWincounter() { return wincounter; }

    //==============================================================================
}
