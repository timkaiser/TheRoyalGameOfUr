import java.util.*;

/** This is the main class. It manages the game board, the players, the wincounter and the gameloop*/
public class Main implements Runnable{
    //wincounter
    /** This hashmap keeps track of the winners of all games*/
    private static HashMap <String, Integer> wincounter = new HashMap<String, Integer>();

    //gameboard
    /** This is the game board. it manages all tiles
     * @see Tile */
    private Tile[] board;

    //players
    /** Array that contains all(2) players*/
    private Player player[];
    /** Array of lists that contain each players finished tokens*/
    private ArrayList<Token> finishedTokens[];
    /** ID of player whose turn it currently is*/
    private int activePlayer;

    //Output device
    /** This is the device that manages the output. It can be on the console (see ConsoleOutput), graphical (to-do) or non at all (see Output)
     * @see Output
     * @see ConsoleOutput
     */
    private Output output;

    //Time gameploop pauses between moves
    /** Time between each turn. Relevant for AI only games with graphical/console output*/
    private int sleeptime = 0;

    /**The constructor of Main manages the game setup. It initializes the game board, the token lists, etc...)
     * @param player1 First player (AI or Human)
     * @param player2 Second player (AI or Human)
     * @param output Manages output (e.g. to console or a GUI or not at all)
     * @param sleeptime Time between each turn (important vor AI only matches with Graphical output)
     *
     * @see Player
     * @see Output
     * @see ConsoleOutput
     */
    public Main(Player player1, Player player2, Output output, int sleeptime){
        this.output = output;
        this.sleeptime = sleeptime;

        //Setup Game Board
        //create board tiles
        board = new Tile[14];
        for(int i = 0; i<board.length; i++){
            //tiles 0-4 and 12-13 are private; tiles 3, 7 and 13 are double roll tiles
            board[i] = new Tile(/*ID:*/i,/*isPrivate:*/(i<=3||i>=12), /*rollAgain:*/((i==3)||(i==7)||(i==13)) );
        }

        //Setup Players
        player = new Player[2];
        player[0] = player1;
        player[1] = player2;
        activePlayer = (int)(Math.random()*2);

        finishedTokens = new ArrayList[2];
        finishedTokens[0] = new ArrayList<Token>();
        finishedTokens[1] = new ArrayList<Token>();

        //print board
        output.printBoard(board,player);

        //Start loop
        run();//new Thread(this).start();
    }

    /**This method takes care of the gameloop. It runs in an infinite while loop until the game is won. It calls turn() for the currently active player.*/
    public void run(){
        gameloop:
        while(true){
            //Player turns
            //also: test if player has already won
            if(turn(player[activePlayer])){ break gameloop;}

            //Output current state of the game
            output.printBoard(board, player);

            //Pause loop for a short time to make AI only games easier to follow
            try{Thread.sleep(sleeptime);}catch(Exception e){};
        }
    }

    /** This method takes care of one players turn.
     * It first changes the active player for the next turn,
     * then rolls a dice, then ask what the player wants do do, if there is a possible move
     * and finally makes that move, if possible
     *
     * @param player player whos turn it is;
     * @return true if player has won, false otherwise*/
    private boolean turn(Player player){
        //change active player (changend back in move() if you land on a double roll)
        activePlayer = (activePlayer+1)%this.player.length;

        //roll Dice
        int dice = rollDice();
        output.print("Dice: "+dice+"\n");

        // move token if valid move exists
        if(!doValidMovesExsist(board,player,dice)) { return true; }

        //move otherwise. throw error if move is not possible
        move(player, player.turn(board, this.player[activePlayer].getTokens(), dice), dice);
            //throw new Error("Illegal Move ("+player.getID());


        //check if player has won
        if(finishedTokens[player.getID()].size()==7){
            output.print(player.getName() + " won!\n");
            incrementWincounter(player.getClass().getTypeName());
            return true;
        }

        return false;
    }

    /** This method moves token a token if possible
     * @param player Player whose turn it is
     * @param token Token that should be moved
     * @param dice Number of fields to move
     * @return true if move is successful, false otherwise
     */
    private boolean move(Player player, Token token, int dice){
        //Don't move if dice is zero
        if(dice == 0){ return true; }

        //Don't move if there are no vaild moves
        if(!doValidMovesExsist(board,player,dice)){ return true; }

        //NO CHEATING!!! >.<
        if(player.getID() != token.getPlayer()) return false;

        //check if move is valid
        if(!isValidMove(board,token,dice)){return false; }

        //test if tile reached the end
        if(token.getTile()!=null && token.getTile().getID()+dice==14){
            token.getTile().removeToken(token);
            finishedTokens[player.getID()].add(token);
            player.removeToken(token);
            return true;
        }

        //calculate new tile ID
        Tile newTile;
        if(token.getTile()==null){  //If token is not on board yet
            newTile = board[dice-1];
        }else {     //If token is already on board
            newTile = board[token.getTile().getID() + dice];
        }

        //Change active player back if you land on a double roll
        if(newTile.isRollAgain()){
            activePlayer = player.getID();
        }

        //actually move token
        if(token.getTile()!=null){ token.getTile().removeToken(token); }
        newTile.addToken(token);

        return true;
    }

    /** This method test if a move is valid
     * @param board game board
     * @param token token that should be moved
     * @param dice number of tiles to move
     * @return false if move is invalid
     *          true otherwise
     * */
    public static boolean isValidMove(Tile[] board, Token token, int dice){
        //move is always valid if dice is zero*/
        if(dice == 0){ return true; }

        //calculate new tile ID
        int newTileID;
        //test if token is not on board yet
        if(token.getTile()==null){
            newTileID = dice-1;
        }else {
            newTileID = token.getTile().getID() + dice;
        }

        //test if player reached the goal
        if(newTileID == board.length) { return true; }

        //test if player moved out of board area
        else if(newTileID>board.length){ return false; }

        //find new Tile
        Tile newTile = board[newTileID];

        //test if tile is blocked by own token
        if(newTile.isOccupiedByOwn(token.getPlayer())){ return false; }

        //test if other player is on safespot
        if(!newTile.isPrivate() && newTile.isRollAgain() && newTile.isOccupiedByOpponent(token.getPlayer())){ return false; }

        // return true otherwise*/
        return true;
    }

    /** This method tests if there are valid moves left for a player and a dice result
     * @param board Game board
     * @param player Player whose turn it is
     * @param dice dice result
     * @return true if vaild turns exist, false otherwise
     */
     public static boolean doValidMovesExsist(Tile[] board, Player player, int dice) {
         for(Token token:player.tokens){
             if(isValidMove(board,token,dice)) return true;
         }

         return false;
     }

    /** This method rolls 4 binary dice and returns the sum of the values
     * @return: 0 with probability 1/16
     *          1 with probability 4/16
     *          2 with probability 6/16
     *          3 with probability 4/16
     *          4 with probability 1/16
     */
    private static int rollDice(){
        return ((Math.random()<0.5)?1:0)+((Math.random()<0.5)?1:0)+((Math.random()<0.5)?1:0)+((Math.random()<0.5)?1:0);
    }

    /** This method increments Wincounter for the winner of a match
     * @param winner Name of the winner
     */
    private void incrementWincounter(String winner){
        if(!wincounter.containsKey(winner)){
            wincounter.put(winner,0);
        }
        wincounter.replace(winner,wincounter.get(winner)+1);
    }

    /** Outputs wincounter to console (this is not managed in Output because the wincounter should also be displayed if there is no other graphical outputs e.g. for AI only Tournaments )*/
    public static void printWincounter(){
        Set set = wincounter.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.println(mentry.getKey() + ": "+ mentry.getValue());
        }

    }

    /** This is the main method. The programm starts here.
     * @param args unused
     */
    public static void main(String args[]){
        for (int i = 0; i < 10000; i++) {
            new Main(new SimpleAI(0), new RandomAI(1), new Output(), 0);
        }
        printWincounter();
    }

}
