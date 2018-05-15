import java.util.Scanner;

public class Human extends Player {
    /**IMPORTANT!!! DO NOT REMOVE*/
    private static int wincounter = 0;
    /**##########################*/

    public Human(int id){
        super(id);
    }

    public Token turn(Tile[] board, int dice){
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
    }

    /**IMPORTANT!!! DO NOT REMOVE*/
    public static int getWincounter(){ return wincounter; }
    /**##########################*/
}
