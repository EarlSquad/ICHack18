package org.earlsquad.ichack18.api;

public interface JoinRoomListener {
  void roomFullError();
  void joinSuccessful(String userId);
}
