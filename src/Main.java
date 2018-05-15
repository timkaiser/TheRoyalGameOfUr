import java.util.ArrayList;

/*TODO: - Wincounter
*       - change main class
*       - Tournament
*/
public class Main implements Runnable{
    /**Game board*/
    private Tile[] board;

    /**Player*/
    private Player player[];
    private ArrayList<Token> finishedTokens[];

    private int activePlayer;

    /**Output device*/
    private Output output;

    /**Time gamepllop pauses between moves*/
    private int sleeptime = 0;

    /**Setup the game*/
    public Main(Player player1, Player player2, Output output, int sleeptime){
        this.output = output;
        this.sleeptime = sleeptime;

        /**Setup Game Board**/
        //create board tiles
        board = new Tile[14];
        for(int i = 0; i<board.length; i++){
            //tiles 0-4 and 12-13 are private; tiles 3, 7 and 13 are double roll tiles
            board[i] = new Tile(/*ID:*/i,/*isPrivate:*/(i<=3||i>=12), /*rollAgain:*/((i==3)||(i==7)||(i==13)) );
        }

        /**Setup Players**/
        player = new Player[2];
        player[0] = player1;
        player[1] = player2;
        activePlayer = (int)(Math.random()*2);

        finishedTokens = new ArrayList[2];
        finishedTokens[0] = new ArrayList<Token>();
        finishedTokens[1] = new ArrayList<Token>();

        /**print board*/
        output.printBoard(board,player);

        /**Start loop**/
        new Thread(this).start();
    }

    /**Gameloop*/
    public void run(){
        gameloop:
        while(true){
            /** Player turns */
            //also: test if player has already won
            Player currentPlayer = player[activePlayer];
            if(turn(currentPlayer)){
                //currentPlayer.getID()
                break gameloop;
            }

            output.printBoard(board, player);

            try{Thread.sleep(sleeptime);}catch(Exception e){};
        }
    }

    /** each players turn;
     * @param player: player whos turn it is;
     * @return : true if player has won, false otherwise*/
    private boolean turn(Player player){
        /**change active player (changend back in move() if you land on a double roll)*/
        activePlayer = (activePlayer+1)%this.player.length;

        /**roll Dice**/
        int dice = rollDice();
        output.println("Dice: "+dice);

        /**move token*/
        move(player, player.turn(board, dice), dice);

        /** check if player has won*/
        if(finishedTokens[player.getID()].size()==7){
            output.println(player.getName() + " won!");
            return true;
        }

        return false;
    }

    /** move token
     * @param player Player whos turn it is
     * @param token Token that should be moved
     * @param dice Number of fields to move
     * @return true if move is successful, false otherwise
     */
    private boolean move(Player player, Token token, int dice){
        /**Don't move if dice is zero*/
        if(dice == 0){ return true; }

        /**Don't move if there are no vaild moves*/
        if(!doValidMovesExsist(board,player,dice)){ return true; }

        /**NO CHEATING!!! >.<*/
        if(player.getID() != token.getPlayer()) return false;

        /**check if move is valid*/
        if(!isValidMove(board,token,dice)){return false; }

        /**test if tile reached the end*/
        if(token.getTile()!=null && token.getTile().getID()+dice==14){
            token.getTile().removeToken(token);
            finishedTokens[player.getID()].add(token);
            player.removeToken(token);
            return true;
        }

        /**calculate new tile ID*/
        Tile newTile;
        if(token.getTile()==null){  //If token is not on board yet
            newTile = board[dice-1];
        }else {     //If token is already on board
            newTile = board[token.getTile().getID() + dice];
        }

        /**Change active player back if you land on a double roll*/
        if(newTile.isRollAgain()){
            activePlayer = player.getID();
        }

        /**actually move token*/
        if(token.getTile()!=null){ token.getTile().removeToken(token); }
        newTile.addToken(token);

        return true;
    }

    /**Test if a move is valid
     * @param board: game board
     * @param token: token that should be moved
     * @param dice: number of tiles to move
     * @return : false if move is invalid
     *          true otherwise
     * */
    public static boolean isValidMove(Tile[] board, Token token, int dice){
        /**move is always valid if dice is zero*/
        if(dice == 0){ return true; }

        /**calculate new tile ID*/
        int newTileID;
        if(token.getTile()==null){  //If token is not on board yet
            newTileID = dice-1;
        }else {     //If token is already on board
            newTileID = token.getTile().getID() + dice;
        }
        if(newTileID == board.length) { return true; }  //test if player reached the goal
        else if(newTileID>board.length){ return false; } //test if player moved out of board area
        Tile newTile = board[newTileID];    //find new Tile

        /**test if tile is blocked by own token*/
        if(newTile.isOccupiedByOwn(token.getPlayer())){ return false; } //test if tile is occupied by own token

        /**test if other player is on safespot*/
        if(!newTile.isPrivate() && newTile.isRollAgain() && newTile.isOccupiedByOtherPlayer(token.getPlayer())){ return false; } //test if other player is on safespot

        /** return true otherwise*/
        return true;
    }

    /**Tests if there are valid moves left for player
     * @param board Game board
     * @param player Player whos turn it is
     * @param dice dice result
     * @return
     */
     public static boolean doValidMovesExsist(Tile[] board, Player player, int dice) {
         for(Token token:player.tokens){
             if(isValidMove(board,token,dice)) return true;
         }

         return false;
     }

    /**Roll 4 binary dice and return the sum of the values
     * @return: 0 with probability 1/16
     *          1 with probability 4/16
     *          2 with probability 6/16
     *          3 with probability 4/16
     *          4 with probability 1/16
     */
    private static int rollDice(){
        return ((Math.random()<0.5)?1:0)+((Math.random()<0.5)?1:0)+((Math.random()<0.5)?1:0)+((Math.random()<0.5)?1:0);
    }

    /**main methode*/
    public static void main(String args[]){
        new Main(new SimpleKI(0),new RandomKI(1),new ConsoleOutput(), 500);
    }
}
