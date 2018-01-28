package org.earlsquad.ichack18.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheun on 27/1/2018.
 */

public class TileHandState {
    private List<Tile> tiles;
    private Hand hands;

    public TileHandState(TileHandState state) {
        this.tiles = new ArrayList<>(state.getTiles());
        this.hands = new Hand(state.getHands());
    }

    public TileHandState(List<Tile> tiles, Hand hands) {
        this.tiles = tiles;
        this.hands = hands;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public Hand getHands() {
        return hands;
    }

    public int getTileNumber() {
        return tiles.size();
    }

    public void printState() {
        System.out.println("Tiles:");
        for(int id = 0; id < tiles.size(); id++) {
            System.out.print(tiles.get(id) + " ");
        }
        System.out.println();
        System.out.println("Meld:");
        for(Meld meld : hands.getMeld()) {
            System.out.print(meld + " ");
        }
        System.out.println();
        System.out.println("Eyes:");
        System.out.println(hands.getEyes());

        System.out.println();
    }
}
