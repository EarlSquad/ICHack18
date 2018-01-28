package org.earlsquad.ichack18;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.earlsquad.ichack18.api.JoinRoomListener;
import org.earlsquad.ichack18.api.RoomManager;

public class MainJoinRoom extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.joinroom);

    String roomName = getIntent().getStringExtra("roomName");
    if (roomName != null) {
      joinRoom(roomName, getIntent().getStringExtra("userName"));
    }
  }

  private void joinRoom(final String roomName, final String userName) {
    RoomManager.getInstance().joinRoom(roomName, userName, new JoinRoomListener() {
      @Override
      public void roomFullError() {
        Toast.makeText(MainJoinRoom.this, "Room is full :(", Toast.LENGTH_LONG).show();
      }

      @Override
      public void joinSuccessful(String userId) {
        RoomManager.getInstance().setCurrentRoomName(roomName).setUserName(userName).setUserId(userId);
        startActivity(new Intent(MainJoinRoom.this, WaitingForPlayer.class));
      }
    });
  }

  public void goToRoom(View v) {
    EditText roomNameView = (EditText) findViewById(R.id.join_room_name);
    EditText userNameView = (EditText) findViewById(R.id.join_user_name);
    String roomName = roomNameView.getText().toString();
    String userName = userNameView.getText().toString();
    joinRoom(roomName, userName);
  }
}
