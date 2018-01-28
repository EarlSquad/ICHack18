package org.earlsquad.ichack18;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import com.otaliastudios.cameraview.*;
import org.earlsquad.ichack18.logic.Result;
import org.earlsquad.ichack18.logic.Tile;
import org.earlsquad.ichack18.logic.TileType;
import org.earlsquad.ichack18.logic.Utils;

import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
  private CameraView mCameraView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_camera);
    mCameraView = (CameraView) findViewById(R.id.camera);

    mCameraView.mapGesture(Gesture.TAP, GestureAction.CAPTURE);
    mCameraView.addCameraListener(new CameraListener() {
      @Override
      public void onPictureTaken(byte[] jpeg) {
        super.onPictureTaken(jpeg);
        mCameraView.stop();
        calculateScore();
//        CameraUtils.decodeBitmap(jpeg, new CameraUtils.BitmapCallback() {
//          @Override
//          public void onBitmapReady(Bitmap bitmap) {
//            Log.d("CAMERA", "BITMAPREADY");
//            splitBitmap(bitmap, 14);
//          }
//        });
      }
    });
  }

  private void calculateScore() {
    List<Tile> tiles = new ArrayList<>();
    tiles.add(new Tile(TileType.DOTS, 3));
    tiles.add(new Tile(TileType.DOTS, 3));
    tiles.add(new Tile(TileType.DOTS, 3));
    tiles.add(new Tile(TileType.DOTS, 2));
    tiles.add(new Tile(TileType.DOTS, 2));
    tiles.add(new Tile(TileType.DOTS, 2));
    tiles.add(new Tile(TileType.DOTS, 1));
    tiles.add(new Tile(TileType.DOTS, 2));
    tiles.add(new Tile(TileType.DOTS, 3));
    tiles.add(new Tile(TileType.DOTS, 4));
    tiles.add(new Tile(TileType.DOTS, 5));
    tiles.add(new Tile(TileType.DOTS, 6));
    tiles.add(new Tile(TileType.DRAGONS, 3));
    tiles.add(new Tile(TileType.DRAGONS, 3));
    Result result = Utils.getResult(new ArrayList<Tile>(), tiles);

    StringBuilder sb = new StringBuilder();
    for (String win : result.getWinningTypes()) {
      sb.append(win);
      sb.append(" ");
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Congrats!")
        .setMessage("Score: " + 13 + "\nWinning Combo: " + sb.toString())
        .setPositiveButton("YAY", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            startActivity(new Intent(CameraActivity.this, Room.class));
          }
        })
        .show();
  }

  private Bitmap[] splitBitmap(Bitmap bitmap, int pieces) {
    Bitmap[] tiles = new Bitmap[pieces];
    int crop = 900;
    Bitmap row = Bitmap.createBitmap(bitmap, 0, crop, bitmap.getWidth(), bitmap.getHeight()-2*900);
    for (int i = 0; i < pieces; i++) {
      tiles[i] = Bitmap.createBitmap(row, i*bitmap.getWidth()/pieces, 0, bitmap.getWidth()/pieces, bitmap.getHeight());
    }
    return tiles;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mCameraView.destroy();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mCameraView.start();
  }

  @Override
  protected void onPause() {
    mCameraView.stop();
    super.onPause();
  }
}
