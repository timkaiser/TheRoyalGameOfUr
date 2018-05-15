import java.util.Scanner;

public class Human extends Player {

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

    public static String getType(){return "Human";}
}
