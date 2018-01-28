package Logic;

/**
 * Created by cheun on 27/1/2018.
 */

public class Meld {
    private MeldType meldType;
    private Tile tile;

    public Meld(MeldType meldType, Tile tile) {
        this.meldType = meldType;
        this.tile = tile;
    }

    public MeldType getMeldType() {
        return meldType;
    }

    public TileType getTileType() {
        return tile.getType();
    }

    public void setMeldType(MeldType meldType) {
        this.meldType = meldType;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    @Override
    public String toString() {
        return meldType + " " + tile;
    }
}
