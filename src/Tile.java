import java.util.ArrayList;

public class Tile {
    private int id;

    private ArrayList<Token> tokens;
    private boolean rollAgain;
    private boolean isPrivate;

    public Tile(boolean isPrivate){
        this.isPrivate = isPrivate;
    }

    public Tile(boolean isPrivate, boolean rollAgain){
        this(isPrivate);

        this.rollAgain = rollAgain;
    }

    public boolean removeToken(Token t){
        tokens.remove(t);
        t.setTile(null);
        return true;
    }

    public boolean addToken(Token token){
        tokens.add(token);
        token.setTile(this);
        if(!isPrivate){
            for(Token t:tokens){
                if(t.getPlayer()!=token.getPlayer()){
                    removeToken(t);
                }
            }
        }
        return true;
    }

    public boolean isOccupiedByOwn(int playerId){
        for(Token t:tokens){
            if(t.getPlayer()==playerId){
                return true;
            }
        }
        return false;
    }

    public boolean isOccupiedByOtherPlayer(int playerId){
        for(Token t:tokens){
            if(t.getPlayer()!=playerId){
                return true;
            }
        }
        return false;
    }

    //get und set methoden
    //public void setPrivate(boolean isPrivate){ this.isPrivate = isPrivate;}
    public boolean isPrivate(){return isPrivate;}

    //public void setRollAgain(boolean isPrivate){ this.isPrivate = isPrivate;}
    public boolean isRollAgain(){return rollAgain;}

    //get ID
    public int getID(){
        return id;
    }


}
