package org.earlsquad.ichack18.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cheun on 27/1/2018.
 */

public class Utils {

    public static TileHandState getMaximumScoreState(List<TileHandState> states) {
        int maxFan = 0;
        TileHandState maxState = null;
        for(int id = 0; id < states.size(); id++) {
            maxFan = Math.max(maxFan, getScoreFromState(states.get(id), false).getScore());
            maxState = states.get(id);
        }
        return maxState;
    }

    public static Result getScoreFromState(TileHandState state, boolean showType) {
        Result result = new Result(0, new ArrayList<String>());
        int fan = 0;
        int max = 10;
        Hand hand = state.getHands();
        List<Meld> meld = hand.getMeld();
        int chowCount = 0;
        int pongCount = 0;
        int honorCount = 0;
        int dragonCount = 0;
        int windCount = 0;
        boolean dragonEye = false;
        boolean windEye = false;
        Set<TileType> simpleSuit = new HashSet<>();
        for(int id = 0; id < meld.size(); id++) {
            Meld indivMeld = meld.get(id);
            if(indivMeld.getMeldType() == MeldType.CHOW) {
                chowCount++;
            } else if(indivMeld.getMeldType() == MeldType.PONG) {
                pongCount++;
            }
            if(indivMeld.getTileType() == TileType.BAMBOO ||  indivMeld.getTileType() == TileType.CHARACTERS || indivMeld.getTileType() == TileType.DOTS) {
                simpleSuit.add(indivMeld.getTileType());
            } else if (indivMeld.getTileType() == TileType.DRAGONS){
                honorCount++;
                dragonCount++;
            } else if (indivMeld.getTileType() == TileType.WINDS) {
                honorCount++;
                windCount++;
            }
        }
        if(hand.getEyes().getTile().getType() == TileType.BAMBOO || hand.getEyes().getTile().getType() == TileType.CHARACTERS || hand.getEyes().getTile().getType() == TileType.DOTS) {
            simpleSuit.add(hand.getEyes().getTile().getType());
            dragonEye = false;
        } else {
            dragonEye = hand.getEyes().getTile().getType() == TileType.DRAGONS;
            windEye = hand.getEyes().getTile().getType() == TileType.WINDS;
        }
        if(pongCount == 0 && chowCount == 4) {
            if(showType)
                System.out.println("Common Hand");
                result.getWinningTypes().add("Common Hand");
            fan++;
        }
        if(pongCount == 4 && chowCount == 0) {
            if(showType)
                System.out.println("All in Triples");
                result.getWinningTypes().add("All in Triples");
            fan += 3;
        }
        if(simpleSuit.size() == 1 && (dragonEye || honorCount > 0)) {
            if(showType)
                System.out.println("Mix One Suit");
                result.getWinningTypes().add("Mix One Suit");
            fan += 3;
        } else if((simpleSuit.size() == 1 && (!dragonEye && honorCount == 0))) {
            if(showType)
                System.out.println("All One Suit");
                result.getWinningTypes().add("All One Suit");
            fan += 7;
        }
        if(dragonCount == 2 && dragonEye) {
            if(showType)
                System.out.println("Small Dragon");
                result.getWinningTypes().add("Small Dragon");
            fan  += 5;
        }
        if(dragonCount == 3) {
            if(showType)
                System.out.println("Great Dragon");
                result.getWinningTypes().add("Great Dragon");
            fan += 8;
        }
        if(windCount == 3 && windEye) {
            if(showType)
                System.out.println("Small Winds");
                result.getWinningTypes().add("Small Winds");
            fan = max;
        }
        if(windCount == 4) {
            if(showType)
                System.out.println("Great Winds");
                result.getWinningTypes().add("Great Winds");
            fan = max;
        }
        if(honorCount == 4 && (dragonEye || windEye)) {
            fan = max;
        }
        result.setScore(fan);
        return result;
    }

    public static List<TileHandState> buildState(List<Tile> tiles, List<Meld> shownMelds) {
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
                        newList.add(new TileHandState(tileCopy, new Hand(new Eyes(tile), shownMelds)));
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
        tiles.add(new Tile(TileType.DOTS, 1));
        tiles.add(new Tile(TileType.DOTS, 2));
        tiles.add(new Tile(TileType.DOTS, 3));
        tiles.add(new Tile(TileType.DOTS, 4));
        tiles.add(new Tile(TileType.DOTS, 5));
        tiles.add(new Tile(TileType.DOTS, 6));
        tiles.add(new Tile(TileType.DRAGONS, 3));
        tiles.add(new Tile(TileType.DRAGONS, 3));
        List<TileHandState> state = buildState(tiles, melds);
        List<TileHandState> finalState = buildMelds(state);
/*        for(int id = 0; id < finalState.size(); id++) {
            finalState.get(id).printState();
            int score = getScoreFromState(finalState.get(id), false);
            System.out.println("Fan = " + score);
        }*/
        TileHandState maxState = getMaximumScoreState(finalState);
        int score = getScoreFromState(maxState, true).getScore();
        System.out.println("Fan = " + score);
    }

    public static Result getScore(List<Tile> shownTiles, List<Tile> concealedTiles) {
        List<Meld> shownMelds = getShownMelds(shownTiles);
        List<TileHandState> state = buildState(concealedTiles, shownMelds);
        List<TileHandState> finalState = buildMelds(state);
        TileHandState maxState = getMaximumScoreState(finalState);
        return getScoreFromState(maxState, true);
    }

    public static List<Meld> getShownMelds(List<Tile> tiles) {
        List<Meld> melds = new ArrayList<>();
        for(int id = 0; id < tiles.size(); id += 3) {
            Tile fst = tiles.get(id);
            Tile snd = tiles.get(id + 1);
            Tile thd = tiles.get(id + 2);
            melds.add(new Meld(checkValidTriple(fst, snd, thd), fst));
        }
        return melds;
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

