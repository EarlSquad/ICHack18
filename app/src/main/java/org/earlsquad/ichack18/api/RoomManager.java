package org.earlsquad.ichack18.api;

import android.util.Log;
import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;

public class RoomManager {
  private static final RoomManager INSTANCE = new RoomManager();
  private static final String TAG = "RoomManager";
  private static final String ROOMS = "rooms";
  private static final String USERS = "users";
  private static final String HISTORY = "history";
  private static final String SCORE = "score";
  private static final String WINNER = "winner";

  private FirebaseDatabase database = FirebaseDatabase.getInstance();

  public static RoomManager getInstance() {
    return INSTANCE;
  }

  public void createRoom(final String roomName, final RoomExistsListener listener) {
    final DatabaseReference rooms = database.getReference(ROOMS);
    rooms.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.hasChild(roomName)) {
          Log.d(TAG, "Room name taken");
          if (listener != null) {
            listener.roomExists();
          }
        } else {
          rooms.child(roomName).setValue("dummy");
          if (listener != null) {
            listener.roomNotExists();
          }
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  public void joinRoom(String roomName, final String userName, final JoinRoomListener listener) {
    final DatabaseReference room = database.getReference(ROOMS).child(roomName);
    room.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        long userCount = dataSnapshot.getChildrenCount();
        if (userCount < 4) {
          String userId = room.child(USERS).push().getKey();
          room.child(USERS).child(userId).setValue(userName);
          if (listener != null) listener.joinSuccessful(userId);
        } else {
          if (listener != null) listener.roomFullError();
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  public void waitForRoomFull(String roomName, final RoomFullListener listener) {
    DatabaseReference room = database.getReference(ROOMS).child(roomName);

    room.child("users").addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        long userCount = dataSnapshot.getChildrenCount();
        if (userCount == 4) {
          Log.d(TAG, "Room is full");
          listener.roomFull();
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  public void addHistoryRecord(String roomName, int score, String winnerUserId) {
    Map<String, Object> record = new HashMap<>();
    record.put(SCORE, score);
    record.put(WINNER, winnerUserId);
    database.getReference(ROOMS).child(roomName).child(HISTORY).push().setValue(record);
  }

  public void setHistoryUpdateListener(String roomName, final HistoryUpdateListener listener) {
    DatabaseReference roomHistory = database.getReference(ROOMS).child(roomName).child(HISTORY);
    roomHistory.addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        int score = dataSnapshot.child(SCORE).getValue(Integer.class);
        String winner = dataSnapshot.child(WINNER).getValue(String.class);
        listener.newHistoryRecord(score, winner);
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {

      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }
}
