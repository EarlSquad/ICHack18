package org.earlsquad.ichack18;

import android.graphics.Bitmap;
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
          }
        });
      }
    });
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
