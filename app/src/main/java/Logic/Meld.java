package Logic;

/**
 * Created by cheun on 27/1/2018.
 */

public class Meld {
    private MeldType meldType;
    private TileType tileType;

    public Meld(MeldType meldType, TileType tileType) {
        this.meldType = meldType;
        this.tileType = tileType;
    }

    public MeldType getMeldType() {
        return meldType;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setMeldType(MeldType meldType) {
        this.meldType = meldType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }
}
