package org.earlsquad.ichack18;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainRoomCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createroom);
    }

    public void goToJoinRoom(View v){
        Toast toast = Toast.makeText(getApplicationContext(), "ac", Toast.LENGTH_SHORT);
        toast.show();
        startActivity(new Intent(MainRoomCreation.this, MainJoinRoom.class));
    }
}
