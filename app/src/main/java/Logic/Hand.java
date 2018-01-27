package Logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheun on 27/1/2018.
 */

public class Hand {
    private Eyes eyes;
    private List<Tile> tiles = new ArrayList<>();
    private Wind roundWind;
    private Wind playerWind;
    private int length;
    private int fan = 0;

    private List<Tile> flowerSeasons = new ArrayList<>();
    private List<Tile> dots = new ArrayList<>();
    private List<Tile> bamboos = new ArrayList<>();
    private List<Tile> characters = new ArrayList<>();
    private List<Tile> dragons = new ArrayList<>();
    private List<Tile> winds = new ArrayList<>();

    public void setRoundWind(Wind roundWind) {
        this.roundWind = roundWind;
    }

    public void setPlayerWind(Wind playerWind) {
        this.playerWind = playerWind;
    }

    public Hand(List<Tile> tiles) {
        this.tiles = tiles;
        for(int id = 0; id < tiles.size(); id++) {
            Tile tile = tiles.get(id);
            TileType type = tile.getType();
            switch(type) {
                case FLOWERS:
                case SEASONS:
                    flowerSeasons.add(tile);
                    break;
                case DOTS:
                    dots.add(tile);
                    break;
                case BAMBOO:
                    bamboos.add(tile);
                    break;
                case CHARACTERS:
                    characters.add(tile);
                    break;
                case DRAGONS:
                    dragons.add(tile);
                    break;
                case WINDS:
                    winds.add(tile);
                    break;
            }
        }
        Collections.sort(dots, new compareNumber());
        Collections.sort(bamboos, new compareNumber());
        Collections.sort(characters, new compareNumber());
    }

    public static List<List<Meld>> buildHand(List<Tile> tiles, List<Meld> melds) {
        List<List<Meld>> meldCombinations = new ArrayList<>();

        List<Tile> tilesCopy = new ArrayList<>(tiles);
        boolean canGetSuccessfulTriplets = false;
     /*   if(tilesCopy.size() % 3 == 2) {

    }*/

        while(tilesCopy.size() > 0) {
            int pos = 0;
            List<Meld> meldList = new ArrayList<>();
            Tile fstTile = tilesCopy.get(pos);
            for (int pos1 = 1; pos1 < tilesCopy.size(); pos1++) {
                if(pos != pos1) {
                    Tile sndTile = tilesCopy.get(pos1);
                    for (int pos2 = 1; pos2 < tilesCopy.size(); pos2++) {
                        if (pos != pos2 && pos2 != pos1) {
                            Tile thdTile = tilesCopy.get(pos2);
                            MeldType chkType = checkValidTriple(fstTile, sndTile, thdTile);
                            if (chkType != MeldType.INVALID) {
                                melds.add(new Meld(chkType, fstTile.getType()));
                                tilesCopy.remove(fstTile);
                                tilesCopy.remove(sndTile);
                                tilesCopy.remove(thdTile);
                                if(tilesCopy.size() == 0) {
                                    meldCombinations.add(new ArrayList<>(melds));
//                                    printMelds(meldCombinations);
//                                    melds = new ArrayList<>();
//                                    tilesCopy = new ArrayList<>(tiles);
                                }
                                canGetSuccessfulTriplets = true;
                                break;
                            }
                        }
                    }
                    if(canGetSuccessfulTriplets) {
                        break;
                    }
                }
            }
            if(!canGetSuccessfulTriplets) {
                if(meldCombinations.isEmpty()) {
                    System.out.println("NO VALID HAND");
                    return null;
                    //TODO: exit case for no valid hand
                }
                break;
            }
        }
        return meldCombinations;
    }

    public static List<List<Meld>> meldCombinations = new ArrayList<>();

    public static List<TileMeldState> buildMelds(List<TileMeldState> original) {
        List<TileMeldState> newList = new ArrayList<>(original);
        for (int id = 0; id < original.size(); id++) {
            TileMeldState fstState = original.get(id);
            for (int pos1 = 1; pos1 < fstState.getTiles().size(); pos1++) {
                for (int pos2 = pos1 + 1; pos2 < fstState.getTiles().size(); pos2++) {
                    Tile fstTile = fstState.getTiles().get(0);
                    Tile sndTile = fstState.getTiles().get(pos1);
                    Tile thdTile = fstState.getTiles().get(pos2);
                    MeldType chkType = checkValidTriple(fstTile, sndTile, thdTile);
                    if (chkType != MeldType.INVALID) {
//                        System.out.println(fstTile + " " + sndTile + " " + thdTile);
                        TileMeldState newState = new TileMeldState(fstState);
                        newState.getMelds().add(new Meld(chkType, fstTile.getType()));
                        newState.getTiles().remove(fstTile);
                        newState.getTiles().remove(sndTile);
                        newState.getTiles().remove(thdTile);
                        newList.add(newState);
                    }
                }
            }
            newList.remove(fstState);
        }
        if(!newList.isEmpty()) {
//            for(int id = 0; id < newList.size(); id++) {
//                for(Meld meld : newList.get(id).getMelds()) {
//                    System.out.print(meld + " ");
//                }
//                System.out.println();
//            }
            if(newList.get(0).getTileNumber() != 0) {
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
        tiles.add(new Tile(TileType.BAMBOO, 3));
        tiles.add(new Tile(TileType.BAMBOO, 3));
        tiles.add(new Tile(TileType.BAMBOO, 3));
        tiles.add(new Tile(TileType.BAMBOO, 2));
        tiles.add(new Tile(TileType.BAMBOO, 2));
        tiles.add(new Tile(TileType.BAMBOO, 2));
        tiles.add(new Tile(TileType.BAMBOO, 1));
        tiles.add(new Tile(TileType.BAMBOO, 1));
        tiles.add(new Tile(TileType.BAMBOO, 1));
        TileMeldState state = new TileMeldState(tiles, melds);
        List<TileMeldState> stateList = new ArrayList<>();
        stateList.add(state);
        printStates(buildMelds(stateList));
    }

    public static void printStates(List<TileMeldState> original) {
        for(int id = 0; id < original.size(); id++) {
//            System.out.println(original.get(id).getMelds().size());
//            System.out.println(original.get(id).getTileNumber());
            for(Meld meld : original.get(id).getMelds()) {
                System.out.print(meld + " ");
            }
            System.out.println();
        }
    }

    public static void printMelds(List<List<Meld>> melds) {
        for(List<Meld> meld : melds) {
            for(Meld mel : meld) {
                System.out.println(mel.getMeldType().toString() + " " + mel.getTileType().toString());
            }
        }
    }

    public List<Hand> getValidHands() {
        List<Meld> melds = new ArrayList<>();
        for(int id = 0; id < length; id++) {
            if(!dots.isEmpty()) {
                for(int num = 0; num < dots.size(); num++) {

                }
            }
        }
        return null;
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

    private class compareNumber implements Comparator<Tile> {
        @Override
        public int compare(Tile lhs, Tile rhs) {
            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
            return lhs.getNumber() > rhs.getNumber() ? 1 : (lhs.getNumber() < rhs.getNumber()) ? -1 : 0;
        }
    }
}
