/** This class represents the tokens of each player.
 * Every player gets 7 of the at the beginning of the game.
 */
public class Token {
    /** Token ID */
    private final int id;

    /** Player this token belongs to */
    private final int player;

    /** Tile the token currently is on (null if it is on no tile)*/
    private Tile tile;

    /** Constructor: Initializes a token
     * @param id Token ID
     * @param player Player this token belongs to
     */
    public Token(int id, int player){
        this.id = id;
        this.player = player;
    }

    //__getter and setter methods________________________________________________
    /** This method returns the tile on which the token currently is (null if no tile at all)
     * @return Tile on which the token currently is (null if no tile at all) */
    public Tile getTile(){ return tile; }

    /** This method sets the tile the token is on
     * @param tile New tile the token is placed on*/
    public void     setTile(Tile tile)  { this.tile = tile; }

    /** This method returns the tokens ID
     * @return Token ID*/
    public int getId() { return id; }

    /** This method returns the ID of the tile the token is on
     * @return -1 is token is on no tile, the tile ID otherwise*/
    public int getTileId() { return (tile==null)? -1 : tile.getID(); }

    /** This method returns the player this token belongs to
     * @return Player this token belongs to*/
    public int      getPlayer()         { return player; }
}


