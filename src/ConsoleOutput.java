import java.util.ArrayList;

/** This class is used to display the current state of the game on the console. */
public class ConsoleOutput extends Output {
    /** This method prints the current state of the game on the console
     * @param board Gameboard
     * @param players List of Players
     */
    public void printBoard(Tile[] board, Player[] players){
        //Clean Board
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        //Display current tokens
        System.out.println(players[0].getName()+": Tokens left: "+players[0].getTokens().size()+"   Tokens finished: "+(7-players[0].getTokens().size()));
        System.out.println(players[1].getName()+": Tokens left: "+players[1].getTokens().size()+"   Tokens finished: "+(7-players[1].getTokens().size()));

        //Safe strings for every line in array
        String[] lines = new String[7];

        //Setup board
        lines[0] = " _____________↓_         _↑_____ ";
        lines[1] = "|> <|   |   |   |       |> <|   |";
        lines[2] = "|---|---|---|---|---.---|---|---|";
        lines[3] = "|   |   |   |> <|   |   |   |   |";
        lines[4] = "|---|---|---|---|---'---|---|---|";
        lines[5] = "|> <|   |   |   |       |> <|   |";
        lines[6] = "'---'---'---'-↑-'       '-↓-'---'";

        //Place tokens
        for (int p = 0; p < players.length; p++) {
            ArrayList<Token> tokens = players[p].getTokens();
            for (Token t:tokens ){
                if(t.getTile()!=null){
                    //Calculate position in String array
                    int r = getRow(p,t.getTile().getID())*2+1;
                    int c = getColumn(t.getTile().getID())*4+2;
                    lines[r] = replace(lines[r],getTokenChar(p,t.getId()),c);
                }
            }
        }

        //print board
        for (int i = 0; i < lines.length; i++) {
            System.out.println(lines[i]);
        }
    }

    /**This method outputs Strings to console
     * @param s String that should be printed on the console
     */
    public void print(String s){
        System.out.print(s);
    }


    /**This method returns corresponding ASCII-symbol for player tokens
     * @param playerID: ID of player
     * @param tokenID: Token we want the symbol for
     * @return :  white numbered tokens for player 0, black numbered tokens for player 1
     */
    private static char getTokenChar(int playerID, int tokenID){
        if(tokenID==0)
            return (char)((playerID==0) ? 9471 : 9450);
        else
            return (char)(/*charater to start with:*/ ((playerID==0) ? 10101 : 9311) + /*character offset:*/ tokenID);  //the 0 is the last number in

    }

    /**returns row of a tile on te board
     * @param playerID ID of player whos tile it is (important for private tiles)
     * @param tileID Index of tile
     * @return 0,1 or 2 depending on row
     */
    private static int getRow(int playerID, int tileID) {
        return (tileID >= 4 && tileID <= 11) ? 1 : (playerID == 0) ? 0 : 2;
    }

    /**returns column of a tile on te board
     * @param tileID Index of tile
     * @return 0-7 depending on column
     */
    private static int getColumn(int tileID){
        return (tileID<=3)? 3-tileID : (tileID>=12)? -(tileID-19) : tileID-4;
    }

    /** replaces one character in string s with char c
     * @param s String where the char should be replaced in
     * @param c Char that will replace the old char
     * @param index Position where to replace the char
     * @return new String with char replaced
     */
    private static String replace(String s, char c, int index){
        return s.substring(0,index)+c+s.substring(index+1);
    }
}
