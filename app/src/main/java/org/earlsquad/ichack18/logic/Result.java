package org.earlsquad.ichack18.logic;

import java.util.List;

public class Result {
  private int score;
  private List<String> winningTypes;

  public Result(int score, List<String> winningTypes) {
    this.score = score;
    this.winningTypes = winningTypes;
  }

  public int getScore() {
    return score;
  }

  public List<String> getWinningTypes() {
    return winningTypes;
  }

  public void setScore(int score) {
    this.score = score;
  }
}
