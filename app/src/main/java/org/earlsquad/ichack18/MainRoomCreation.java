package org.earlsquad.ichack18;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.earlsquad.ichack18.api.RoomExistsListener;
import org.earlsquad.ichack18.api.RoomManager;

public class MainRoomCreation extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.createroom);
  }

  public void goToJoinRoom(View v) {
    EditText createRoomNameView = (EditText) findViewById(R.id.create_room_name);
    EditText userNameView = (EditText) findViewById(R.id.new_user_name);
    final String roomName = createRoomNameView.getText().toString();
    final String userName = userNameView.getText().toString();
    RoomManager.getInstance().createRoom(roomName, new RoomExistsListener() {
      @Override
      public void roomExists() {
        Toast.makeText(getApplicationContext(), "Room name already exist", Toast.LENGTH_LONG).show();
      }

      @Override
      public void roomNotExists() {
        Intent intent = new Intent(MainRoomCreation.this, MainJoinRoom.class);
        intent.putExtra("userName", userName);
        intent.putExtra("roomName", roomName);
        startActivity(intent);
      }
    });
  }
}
