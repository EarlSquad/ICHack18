package org.earlsquad.ichack18;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainJoinRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joinroom);
    }


    public void goToRoom(View v){
        startActivity(new Intent(MainJoinRoom.this, Room.class));
    }
}
