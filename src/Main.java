import java.util.ArrayList;
//TODO: test if there are valid moves left
public class Main implements Runnable{
    /**Game board*/
    private Tile[] board;

    /**Player*/
    private Player player[];
    private ArrayList<Token> finishedTokens[];



    /**Setup the game*/
    public Main(){
        /**Setup Game Board**/
        //create board tiles
        board = new Tile[14];
        for(int i = 0; i<board.length; i++){
            //tiles 0-4 and 12-13 are private; tiles 3, 7 and 13 are double roll tiles
            board[i] = new Tile(/*isPrivate:*/(i<=3||i>=12), /*rollAgain:*/((i==3)||(i==7)||(i==13)) );
        }

        /**Setup Players**/
        player = new Player[2];
        player[0] = new KI(0);
        player[1] = new KI(1);

        finishedTokens = new ArrayList[2];
        finishedTokens[0] = new ArrayList<Token>();
        finishedTokens[1] = new ArrayList<Token>();

        /**Start loop**/
        new Thread(this).start();
    }

    /**Gameloop*/
    public void run(){
        gameloop:
        while(true){
            /** Player turns */
            //also: test if player has already won
            if(turn(player[0])) break gameloop;
            if(turn(player[1])) break gameloop;

            //TODO: remove
            break gameloop;
        }
    }

    /** each players turn;
     * @Input: player whos turn it is;
     * @Output: true if player has won, false otherwise*/
    private boolean turn(Player player){
        /**roll Dice**/
        int dice = rollDice();

        /**move token*/
        move(player, player.turn(board, dice), dice);

        /** check if player has won*/
        if(finishedTokens[player.getID()].size()==7){
            System.out.println(player.getName() + " won!");
            return true;
        }

        return false;
    }

    /** move token */
    private boolean move(Player player, Token token, int dice){
        /**Don't move if dice is zero*/
        if(dice == 0){ return true; }

        /**NO CHEATING!!! >.<*/
        if(player.getID() != token.getPlayer()) return false;

        /**check if move is valid*/
        if(!isValidMove(board,token,dice)){return false; }

        /**calculate new tile ID*/
        Tile newTile;
        if(token.getTile()==null){  //If token is not on board yet
            newTile = board[dice-1];
        }else {     //If token is already on board
            newTile = board[token.getTile().getID() + dice];
        }
        /**actually move token*/
        token.getTile().removeToken(token);
        newTile.addToken(token);

        return false;
    }

    /**Test if a move is valid
     * @Input:  board: game board
     *          token: token that should be moved
     *          dice: number of tiles to move
     * @Output: false if move is invalid
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

    /**Roll 4 binary dice and return the sum of the values
     * @Output: 0 with probability 1/16
     *          1 with probability 4/16
     *          2 with probability 6/16
     *          3 with probability 4/16
     *          4 with probability 1/16
     */
    private static int rollDice(){
        return ((Math.random()<0.5)?1:0)+((Math.random()<0.5)?1:0)+((Math.random()<0.5)?1:0)+((Math.random()<0.5)?1:0);
    }

    /**Output board to console*/
    private void printBoard(){
        for(int i = 0; i<board.length; i++){
            System.out.print("|" + (board[i].isRollAgain()? "X" : "_") + ((i==board.length-1)? "|\n" : ""));
        }
        for(int i = 0; i<board.length; i++){
            if(board[i].isPrivate()) {
                System.out.print("|" + (board[i].isRollAgain() ? "X" : "_") + ((i == board.length - 1) ? "|\n" : ""));
            }else{
                System.out.print("  ");
            }
        }
    }

    /**main methode*/
    public static void main(String args[]){
       new Main();
    }
}
