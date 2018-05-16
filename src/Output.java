import java.util.ArrayList;

/**This Class manages all the output. Other output-class my inherit from it. This particular class does not putput at all. Use this if you want no output*/
public class Output {
    /** Empty Placeholders for output of the current state of the board
     * @param board Gameboard
     * @param players List of Players
     */
    public void printBoard(Tile[] board, Player[] players){}

    /** Empty placeholder for other output
     * @param s Message that should be displayed
     */
    public void print(String s){}
}

