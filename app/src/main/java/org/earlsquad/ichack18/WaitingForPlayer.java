package org.earlsquad.ichack18;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.earlsquad.ichack18.api.RoomFullListener;
import org.earlsquad.ichack18.api.RoomManager;

public class WaitingForPlayer extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_waiting_for_player);

    RoomManager.getInstance().waitForRoomFull(new RoomFullListener() {
      @Override
      public void roomFull() {
        startActivity(new Intent(WaitingForPlayer.this, Room.class));
      }
    });
  }
}
