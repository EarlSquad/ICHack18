package org.earlsquad.ichack18;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.earlsquad.ichack18.api.RoomManager;

import java.util.List;

public class Room extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_room);

    TextView roomNameView = (TextView) findViewById(R.id.room_name);
    roomNameView.setText(RoomManager.getInstance().getCurrentRoomName());

    TextView player1NameView = (TextView) findViewById(R.id.player1_name);
    TextView player2NameView = (TextView) findViewById(R.id.player2_name);
    TextView player3NameView = (TextView) findViewById(R.id.player3_name);
    TextView player4NameView = (TextView) findViewById(R.id.player4_name);
    List<String> users = RoomManager.getInstance().getAllUserNames();
    player1NameView.setText(users.get(0));
    player2NameView.setText(users.get(1));
    player3NameView.setText(users.get(2));
    player4NameView.setText(users.get(3));
  }

  public void goToTakepic(View v) {
    startActivity(new Intent(Room.this, TakePic.class));
  }
}
