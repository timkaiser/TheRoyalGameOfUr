import java.util.ArrayList;

public class Output {
    /**Output board to console*/
    public static void printBoard(Tile[] board, Player[] players){
        /**Safe strings for every line in array*/
        String[] lines = new String[7];

        /**Setup board*/
        lines[0] = " _____________↓_         _↑_____ ";
        lines[1] = "|> <|   |   |   |       |> <|   |";
        lines[2] = "|---|---|---|---|---.---|---|---|";
        lines[3] = "|   |   |   |> <|   |   |   |   |";
        lines[4] = "|---|---|---|---|---'---|---|---|";
        lines[5] = "|> <|   |   |   |       |> <|   |";
        lines[6] = "'---'---'---'-↑-'       '-↓-'---'";

        /**Place tokens*/
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

        /**print board*/
        for (int i = 0; i < lines.length; i++) {
            System.out.println(lines[i]);
        }
    }

    /**Returns corresponding ASCII-symbol for player tokens
     * @param playerID: ID of player
     * @param tokenID: Token we want the symbol for
     * @return :  ❶ for player 0, ① for player 1
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
