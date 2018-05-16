import java.util.ArrayList;

/** This class represents the tiles on the game board.
 * Tiles can be shared or private (represented ba isPrivate) (see Readme)
 * Rather than create their own private tiles for each player they share the same tile and can each have on token on them.
 * On shared tiles there can only be 1 token.
 * Tiles can also be double roll tiles (represented by rollAgain). The shared double roll tile is a safespot.
 */
public class Tile {
    /** Tile id (used to identify a tile and it's position on the board) */
    private int id;

    /** List of tokens on this tile (max 2 if private tile, 1 if shared tile*/
    private ArrayList<Token> tokens;
    /** Shows if this tile is a double roll tile */
    private boolean rollAgain;
    /** Shows if this tile is a private tile */
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

    /** This method removes a token from tile
     * @param token token that should be removed from tile
     */
    public void removeToken(Token token){
        //remove token from tile
        tokens.remove(token);
        //remove tile from token
        token.setTile(null);
    }

    /** This method adds a token to tile
     * and removes other players tile if there is one
     * (checks if move is possible is done at Main.move())
     *
     * @param token token that should be added
     */
    public void addToken(Token token){
        //add token to tile
        tokens.add(token);
        //add tile to token
        token.setTile(this);
        //remove opponents token if necessary
        if(!isPrivate && !rollAgain){
            for(Token t:tokens){
                if(t.getPlayer()!=token.getPlayer()){
                    removeToken(t);
                }
            }
        }
    }

    /**This method checks if other own token is already on this tile
     * @param playerId player whose token it is
     * @return true if own token occupies this tile, false otherwise
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
     * @return true if opponents token occupies this tile, false otherwise
     */
    public boolean isOccupiedByOpponent(int playerId){
        for(Token t:tokens){
            if(t.getPlayer()!=playerId){
                return true;
            }
        }
        return false;
    }

    //__getter and setter_______________________________
    /** This method returns the tiles ID (and therefore the position on the board)
     * @return Tile ID*/
    public int getID(){return id; }

    /** This method returns whether or not the tile is private
     * @return true if tile is private, false otherwise*/
    public boolean isPrivate(){return isPrivate;}

    /** This method returns whether or not the tile is a double roll tile
     * @return true if tile is a double roll tile, false otherwise*/
    public boolean isRollAgain(){return rollAgain;}

}
