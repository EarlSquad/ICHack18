package Logic;

/**
 * Created by cheun on 27/1/2018.
 */

public class Eyes {
    private Tile tile;

    public Eyes(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }

    public String toString() {
        return tile.toString();
    }
}
