package org.earlsquad.ichack18.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheun on 27/1/2018.
 */

public class Hand {
    private Eyes eyes;
    private List<Meld> meld;

    public Hand(Hand original) {
        this.eyes = original.getEyes();
        this.meld = new ArrayList<>(original.getMeld());
    }

    public Hand(List<Meld> meld) {
        this.meld = meld;
    }

    public Hand(Eyes eyes, List<Meld> meld) {
        this.eyes = eyes;
        this.meld = meld;
    }

    public void setEyes(Eyes eyes) {
        this.eyes = eyes;
    }

    public Eyes getEyes() {
        return eyes;
    }

    public List<Meld> getMeld() {
        return meld;
    }

    public void setMeld(List<Meld> meld) {
        this.meld = meld;
    }
}
