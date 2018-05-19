import java.util.ArrayList;

public class KingOfUrAI extends Player {
    /* Player attribute:
     *
     *  protected int id;      //player ID
     *
     *  protected ArrayList<Token> tokens;     //list of tokens that did not reach the goal yet
     */

    //==== Space for own attributes ======================================================
    private  final int  PROGRESS = 0;

    private  final int  KICKOUT = 1;
    private  final int  KICKOUT_PROGRESS = 2;

    private  final int  GET_KICKED_OUT_NOW = 3;
    private  final int  GET_KICKED_OUT_THEN = 4;

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

    private int weights[] = {1291,1094,1844,-661,419,-33,137,554,-467,2847,-870,142,111,0,0};


    //==============================================================================

    /** Constructor
     * !!! Do not change the ID or the super() call !!!
     *
     *@param id Player ID
     */
    public KingOfUrAI(int id){
        super(id);

        //==== Space for own code ======================================================

        //==============================================================================
    }


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
                                + weights[PROGRESS]               *   pProgress(token,start,finish,board,opponentsTokens,dice)

                                + weights[KICKOUT]                *   pKickOut(token,start,finish,board,opponentsTokens,dice)
                                + weights[KICKOUT_PROGRESS]       *   pKickOutProgress(token,start,finish,board,opponentsTokens,dice)

                                + weights[GET_KICKED_OUT_NOW]     *   pGetKickedOutNow(token,start,finish,board,opponentsTokens,dice)
                                + weights[GET_KICKED_OUT_THEN]    *   pGetKickedOutThen(token,start,finish,board,opponentsTokens,dice)

                                + weights[ENTERING_GAME]          *   pEnteringGame(token,start,finish,board,opponentsTokens,dice)
                                + weights[LEAVING_GAME]           *   pLeavingGame(token,start,finish,board,opponentsTokens,dice)

                                + weights[ENTERING_PRIVATE]       *   pEnterPrivate(token,start,finish,board,opponentsTokens,dice)
                                + weights[LEAVING_PRIVATE]        *   pLeavePrivate(token,start,finish,board,opponentsTokens,dice)

                                + weights[ENTERING_SAFESPOT]      *   pEnterSafespot(token,start,finish,board,opponentsTokens,dice)
                                + weights[LEAVING_SAFESPOT]       *   pLeaveLeaveSafespot(token,start,finish,board,opponentsTokens,dice)

                                + weights[ENTERING_DOUBLEROLL]    *   pEnterDoubleroll(token,start,finish,board,opponentsTokens,dice)
                                + weights[LEAVING_DOUBLEROLL]     *   pLeaveDoubleroll(token,start,finish,board,opponentsTokens,dice)

                                + weights[BLOCK_OWN_NOW]          *   pBlockOwnNow(token,start,finish,board,opponentsTokens,dice)
                                + weights[BLOCK_OWN_THEN]         *   pBlockOwnThen(token,start,finish,board,opponentsTokens,dice)   ;

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
        return "The King of Ur ";
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
        this.weights = weights;
    }

    public int[]getWeights(){
        return weights;
    }

    //==============================================================================
}
