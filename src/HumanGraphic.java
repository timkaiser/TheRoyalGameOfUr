import java.util.ArrayList;

public class HumanGraphic extends Player{

    public HumanGraphic(int id){
        super(id);
    }

    public Token turn(Tile[] board, ArrayList<Token> opponentsTokens, int dice){
        //==== Space for own code ======================================================

        for(Token token: tokens){
            if(Main.isValidMove(board,token,dice)){ return token; }
        }

        return null;
        //==============================================================================
    }

    /** This method returns the players type (relevant for wincounter)
     * @return Player type*/
    @Override
    public String getType(){ return "Human with graphic input";}


    public static void main(String[] args){
        UserInterface ui = new UserInterface();
        new Main( new HumanGraphic(0), new KingOfUrAI(1), ui , 0);
    }

}