public class Human extends Player {
    public Human(int id){
        super(id);
    }

    public Token turn(Tile[] board, int dice){
        Token t;
        
        return tokens.get((int)(Math.random()*tokens.size()));
    };
}
