package Logic;

/**
 * Created by cheun on 27/1/2018.
 */

public class Tile {
    private TileType type;
    private int number;

    public Tile(TileType type) {
        this.type = type;
        this.number = 0;
    }

    public Tile(TileType type, int number) {
        this.type = type;
        this.number = number;
    }

    public TileType getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return type + " " + number;
    }
}
