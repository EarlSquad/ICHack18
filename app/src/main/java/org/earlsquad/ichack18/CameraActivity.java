package org.earlsquad.ichack18;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.otaliastudios.cameraview.*;

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
        CameraUtils.decodeBitmap(jpeg, new CameraUtils.BitmapCallback() {
          @Override
          public void onBitmapReady(Bitmap bitmap) {
            Log.d("CAMERA", "BITMAPREADY");
            splitBitmap(bitmap, 14);
          }
        });
      }
    });
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
