package Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cheun on 27/1/2018.
 */

public class Utils {

    public static List<TileHandState> buildState(List<Tile> tiles) {
        List<TileHandState> newList = new ArrayList<>();
        Set<Tile> set = new HashSet<>();
        for(int pos1 = 0; pos1 < tiles.size(); pos1++) {
            Tile tile = tiles.get(pos1);
            if(!set.contains(tile)) {
                for (int pos2 = pos1 + 1; pos2 < tiles.size(); pos2++) {
                    if (tiles.get(pos1).equals(tiles.get(pos2))) {
                        List<Tile> tileCopy = new ArrayList<>(tiles);
                        set.add(tiles.get(pos1));
                        tileCopy.remove(tiles.get(pos1));
                        tileCopy.remove(tiles.get(pos2));
                        newList.add(new TileHandState(tileCopy, new Hand(new Eyes(tile), new ArrayList<Meld>())));
                    }
                }
            }
        }
        return newList;
    }

    public static List<TileHandState> buildMelds(List<TileHandState> original) {
        List<TileHandState> newList = new ArrayList<>(original);
        for (int id = 0; id < original.size(); id++) {
            TileHandState fstState = new TileHandState(original.get(id));
            TileHandState removeState = original.get(id);
            for (int pos1 = 1; pos1 < fstState.getTiles().size(); pos1++) {
                for (int pos2 = pos1 + 1; pos2 < fstState.getTiles().size(); pos2++) {
                    Tile fstTile = fstState.getTiles().get(0);
                    Tile sndTile = fstState.getTiles().get(pos1);
                    Tile thdTile = fstState.getTiles().get(pos2);
                    MeldType chkType = checkValidTriple(fstTile, sndTile, thdTile);
                    if (chkType != MeldType.INVALID) {
//                        System.out.println(fstTile + " " + sndTile + " " + thdTile);
                        TileHandState newState = new TileHandState(fstState);
                        List<Meld> melds = newState.getHands().getMeld();
                        melds.add(new Meld(chkType, fstTile));
                        newState.getHands().setMeld(melds);
                        newState.getTiles().remove(fstTile);
                        newState.getTiles().remove(sndTile);
                        newState.getTiles().remove(thdTile);
                        newList.add(newState);
                    }
                }
            }
            newList.remove(removeState);
        }
        if(!newList.isEmpty()) {
            if(newList.get(0).getTileNumber() != 0) {
                printStates(newList);
                return buildMelds(newList);
            } else {
                System.out.println("Success, returning");
                return newList;
            }
        } else {
            System.out.println("No valid groups");
            return null;
        }
    }

    public static void main(String[] args) {
        List<Tile> tiles = new ArrayList<>();
        List<Meld> melds = new ArrayList<>();
        tiles.add(new Tile(TileType.DOTS, 3));
        tiles.add(new Tile(TileType.DOTS, 3));
        tiles.add(new Tile(TileType.DOTS, 3));
        tiles.add(new Tile(TileType.DOTS, 2));
        tiles.add(new Tile(TileType.DOTS, 2));
        tiles.add(new Tile(TileType.DOTS, 2));
        tiles.add(new Tile(TileType.WINDS, 2));
        tiles.add(new Tile(TileType.WINDS, 2));
        tiles.add(new Tile(TileType.WINDS, 2));
        tiles.add(new Tile(TileType.BAMBOO, 1));
        tiles.add(new Tile(TileType.BAMBOO, 2));
        tiles.add(new Tile(TileType.BAMBOO, 3));
        tiles.add(new Tile(TileType.DRAGONS, 3));
        tiles.add(new Tile(TileType.DRAGONS, 3));
        List<TileHandState> state = buildState(tiles);
        List<TileHandState> finalState = buildMelds(state);
        for(int id = 0; id < finalState.size(); id++) {
            finalState.get(id).printState();
        }
    }

    public static void printStates(List<TileHandState> original) {
        for(int id = 0; id < original.size(); id++) {
//            System.out.println(original.get(id).getHands().size());
//            System.out.println(original.get(id).getTileNumber());
            for(Meld meld : original.get(id).getHands().getMeld()) {
                System.out.print(meld + " ");
            }
            System.out.println();
        }
    }

    private static MeldType checkValidTriple(Tile fst, Tile snd, Tile thd) {
        TileType fstType = fst.getType();
        TileType sndType = snd.getType();
        TileType thdType = thd.getType();

        int fstNumber = fst.getNumber();
        int sndNumber = snd.getNumber();
        int thdNumber = thd.getNumber();

        if(!((fstType == sndType) && (sndType == thdType))) {
            return MeldType.INVALID;
        }

        if((fstNumber == sndNumber) && (thdNumber == sndNumber)) {
            return MeldType.PONG;
        }

        if(fstType == TileType.DRAGONS || fstType == TileType.WINDS) {
            return MeldType.INVALID;
        }

        int minNumber = Math.min(Math.min(fstNumber, sndNumber), thdNumber);
        int maxNumber = Math.max(Math.max(fstNumber, sndNumber), thdNumber);
        int midNumber = fstNumber + sndNumber + thdNumber - minNumber - maxNumber;

        if((minNumber + 1) == midNumber && (midNumber + 1) == maxNumber) {
            return MeldType.CHOW;
        }
        return MeldType.INVALID;
    }
}

