package org.earlsquad.ichack18;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TakePic extends AppCompatActivity {

    private String winner = "Nobody";
    private String loser  = "Nobody";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takepic);
        showPopUp2();
    }

    public void goToScoreBoard(View v) {
        startActivity(new Intent(TakePic.this, ScoreBoard.class));
    }


    private void showPopUp2() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Game State");
        helpBuilder.setMessage("TBC");
        helpBuilder.setPositiveButton("Draw",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        showDraw(); //Testing Purpose
                    }
                });
        helpBuilder.setNegativeButton("Self-Touch", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                showSelfTouch();
            }
        });

        helpBuilder.setNeutralButton("Win-Lose", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                showWinLose();
            }
        });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }

    private void showSelfTouch() {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Choose the winner:");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Alvis");
        arrayAdapter.add("Noel");
        arrayAdapter.add("Mat");
        arrayAdapter.add("Ivan");
        arrayAdapter.add("Terrence");

        final AlertDialog.Builder builderInner = new AlertDialog.Builder(this);
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                winner = strName;
            }
        });
        builderSingle.show();
    }

    private void showWinLose() {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Choose the winner:");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Alvis");
        arrayAdapter.add("Noel");
        arrayAdapter.add("Mat");
        arrayAdapter.add("Ivan");
        arrayAdapter.add("Terrence");

        final AlertDialog.Builder builderInner = new AlertDialog.Builder(this);
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                winner         = strName;
                showLose();
            }
        });
        builderSingle.show();
    }

    private void showLose() {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Choose the Loser:");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Alvis");
        arrayAdapter.add("Noel");
        arrayAdapter.add("Mat");
        arrayAdapter.add("Ivan");
        arrayAdapter.add("Terrence");

        final AlertDialog.Builder builderInner = new AlertDialog.Builder(this);
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                loser          = strName;
            }
        });
        builderSingle.show();
    }

    private void showDraw() {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Choose the winner:");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Alvis");
        arrayAdapter.add("Noel");
        arrayAdapter.add("Mat");
        arrayAdapter.add("Ivan");
        arrayAdapter.add("Terrence");

        final AlertDialog.Builder builderInner = new AlertDialog.Builder(this);
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                String strName = arrayAdapter.getItem(which);
//
//                builderInner.setMessage(strName);
//                builderInner.setTitle("Your Selected Item is");
//                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog,int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builderInner.show();
            }
        });
        builderSingle.show();
    }



}
