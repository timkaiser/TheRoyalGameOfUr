import java.util.ArrayList;
import java.util.Scanner;

/** This class is used for human players. You can input the token you want to move via console. Using ConsoleOutput is highly recommended while playing with a human player*/
public class Human extends Player {
    /* Player attribute:
     *
     *  protected int id;      //player ID
     *
     *  protected ArrayList<Token> tokens;     //list of tokens that did not reach the goal yet
     */

    /** Constructor
     * !!! Do not change the ID or the super() call !!!
     *
     *@param id Player ID
     */
    public Human(int id){
        super(id);
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
        while(true){
            System.out.print("Enter Valid Token id ( ");
            for(Token t:tokens){
                System.out.print("("+t.getId()+") ");
            }
            System.out.print("):\n");
            int id = new Scanner(System.in).nextInt();

            for(Token t:tokens){
                if(id == t.getId()){
                    return t;
                }
            }
        }
        //==============================================================================
    }

    /** This method returns the players type (relevant for wincounter)
     * @return Player type*/
    @Override
    public String getType(){ return "Boring Human";}

}



