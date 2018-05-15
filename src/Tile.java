import java.util.ArrayList;

public class Tile {
    private int id;

    private ArrayList<Token> tokens;
    private boolean rollAgain;
    private boolean isPrivate;

    /**Constructor
     * @param id Token ID
     * @param isPrivate Is a private tile (IDs 0-3 and 12-13)
     * @param rollAgain Is a doubleroll tile (IDs 3,7,13)
     */
    public Tile(int id, boolean isPrivate, boolean rollAgain){this.id = id;
        this.isPrivate = isPrivate;
        this.rollAgain = rollAgain;

        tokens = new ArrayList<Token>();
    }

    /**removes token from tile
     * @param token token that should be removed from tile
     */
    public void removeToken(Token token){
        tokens.remove(token);
        token.setTile(null);
    }

    /**adds token to tile
     *   removes other players tile if there is one
     *   checks if move is possible is done at Main.move()
     *
     * @param token token that should be added
     */
    public void addToken(Token token){
        tokens.add(token);
        token.setTile(this);
        if(!isPrivate){
            for(Token t:tokens){
                if(t.getPlayer()!=token.getPlayer()){
                    removeToken(t);
                }
            }
        }
    }

    /**Checks if other own token is already on this tile
     * @param playerId player whos token it is
     * @return
     */
    public boolean isOccupiedByOwn(int playerId){
        for(Token t:tokens){
            if(t.getPlayer()==playerId){
                return true;
            }
        }
        return false;
    }

    /**Checks if another players token is already on this tile
     * @param playerId player whos token it is (not Opponent!!!)
     * @return
     */
    public boolean isOccupiedByOtherPlayer(int playerId){
        for(Token t:tokens){
            if(t.getPlayer()!=playerId){
                return true;
            }
        }
        return false;
    }

    /**get and set methods*/
    //public void setPrivate(boolean isPrivate){ this.isPrivate = isPrivate;}
    public boolean  isPrivate()     {return isPrivate;}
    public boolean  isRollAgain()   {return rollAgain;}
    public int      getID()         {return id; }


}
