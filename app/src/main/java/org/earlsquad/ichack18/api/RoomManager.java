package org.earlsquad.ichack18.api;

import android.util.Log;
import com.google.firebase.database.*;

public class RoomManager {
  private static final RoomManager INSTANCE = new RoomManager();
  private static final String TAG = "RoomManager";
  private static final String ROOMS = "rooms";
  private static final String USERS = "users";

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

  public void joinRoom(String roomName, String userName) {
    DatabaseReference rooms = database.getReference(ROOMS);
    rooms.child(roomName).child(USERS).push().setValue(userName);
  }
}
