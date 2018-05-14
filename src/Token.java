public class Token {
    private int id;
    private int player;
    private Tile tile;

    public Token(int id, int player){
        this.id = id;
        this.player = player;
    }

    public Tile     getTile()           { return tile; }
    public void     setTile(Tile tile)  { this.tile = tile; }

    public int   getPlayer()         { return player; }
}


