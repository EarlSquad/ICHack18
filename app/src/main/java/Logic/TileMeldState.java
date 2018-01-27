package Logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheun on 27/1/2018.
 */

public class TileMeldState {
    private List<Tile> tiles;
    private List<Meld> melds;

    public TileMeldState(TileMeldState state) {
        this.tiles = new ArrayList<>(state.getTiles());
        this.melds = new ArrayList<>(state.getMelds());
    }

    public TileMeldState(List<Tile> tiles, List<Meld> melds) {
        this.tiles = tiles;
        this.melds = melds;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Meld> getMelds() {
        return melds;
    }

    public int getTileNumber() {
        return tiles.size();
    }
}
